package io.github.laminalfalah.backend.metric;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.metric
 *
 * This is part of the backend-framework.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static io.github.laminalfalah.backend.metric.Constant.*;

import io.github.laminalfalah.backend.metric.model.MetricsResponse;
import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.distribution.ValueAtPercentile;
import io.micrometer.core.instrument.search.Search;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;

/**
 * @author laminalfalah on 15/10/21
 */

@Slf4j
@WebEndpoint(id = "backend-metrics")
@AllArgsConstructor
public class MetricsEndpoint {

    private final MeterRegistry meterRegistry;

    @ReadOperation
    public MetricsResponse allMetrics() {
        return MetricsResponse.builder()
            .jvm(jvmMemoryMetrics())
            .httpServerRequests(httpRequestsMetrics())
            .cache(cacheMetrics())
            .services(serviceMetrics())
            .databases(databaseMetrics())
            .garbageCollector(garbageCollectorMetrics())
            .processMetrics(processMetrics())
            .build();
    }

    private Map<String, Map<String, Number>> jvmMemoryMetrics() {
        var resultsJvm = new HashMap<String, Map<String, Number>>();

        Search.in(meterRegistry).name(s -> s.contains(JVM_MEMORY_USED)).gauges()
            .forEach(gauge -> {
                var key = gauge.getId().getTag(ID);
                resultsJvm.putIfAbsent(key, new HashMap<>());
                resultsJvm.get(key).put(USED, gauge.value());
            });

        Search.in(meterRegistry).name(s -> s.contains(JVM_MEMORY_MAX)).gauges()
            .forEach(gauge -> {
                var key = gauge.getId().getTag(ID);
                resultsJvm.get(key).put(MAX, gauge.value());
            });

        Search.in(meterRegistry).name(s -> s.contains(JVM_MEMORY_COMMITTED)).gauges()
            .forEach(gauge -> {
                var key = gauge.getId().getTag(ID);
                resultsJvm.get(key).put(COMMITTED, gauge.value());
            });

        return resultsJvm;
    }

    private Map<String, Object> httpRequestsMetrics() {
        var statusCode = new HashSet<String>();

        var resultsHttp = new HashMap<String, Object>();
        var resultsHttpPerCode = new HashMap<String, Map<String, Number>>();

        var resultsHttpAll = new HashMap<String, Number>();

        meterRegistry.find(HTTP_SERVER_REQUESTS).timers()
            .forEach(timer -> statusCode.add(timer.getId().getTag(STATUS)));

        statusCode.forEach(code -> {
            var resultsPerCode = new HashMap<String, Number>();
            var httpTimersStream = meterRegistry.find(HTTP_SERVER_REQUESTS)
                .tag(STATUS, code).timers();

            var count = httpTimersStream.stream()
                .map(Timer::count)
                .reduce(Long::sum)
                .orElse(0L);

            var max = httpTimersStream.stream()
                .map(x -> x.max(TimeUnit.MILLISECONDS))
                .reduce((x, y) -> x > y ? x : y)
                .orElse(0.0);

            var totalTime = httpTimersStream.stream()
                .map(x -> x.totalTime(TimeUnit.MILLISECONDS))
                .reduce(Double::sum)
                .orElse(0.0);

            resultsPerCode.put(COUNT, count);
            resultsPerCode.put(MAX, max);
            resultsPerCode.put(MEAN, count != 0 ? totalTime / count : 0);

            resultsHttpPerCode.put(code, resultsPerCode);
        });

        resultsHttp.put(HTTP_PER_CODE, resultsHttpPerCode);

        var countAllRequests = meterRegistry.find(HTTP_SERVER_REQUESTS)
            .timers()
            .stream()
            .map(Timer::count)
            .reduce(Long::sum)
            .orElse(0L);

        resultsHttpAll.put(COUNT, countAllRequests);

        resultsHttp.put(ALL, resultsHttpAll);

        return resultsHttp;
    }

    private Map<String, Map<String, Number>> cacheMetrics() {
        var resultsCache = new HashMap<String, Map<String, Number>>();

        Search.in(meterRegistry).name(s -> s.contains(CACHE) && !s.contains(HIBERNATE))
            .functionCounters()
            .forEach(counter -> {
                var key = counter.getId().getName();
                var name = counter.getId().getTag(NAME);
                if (name != null) {
                    resultsCache.putIfAbsent(name, new HashMap<>());
                    if (counter.getId().getTag(RESULT) != null) {
                        key += "." + counter.getId().getTag(RESULT);
                    }
                    resultsCache.get(name).put(key, counter.count());
                } else {
                    log.warn(MISSING_NAME_TAG_MESSAGE, key);
                }
            });

        Search.in(meterRegistry).name(s -> s.contains(CACHE)).gauges().forEach(gauge -> {
            var key = gauge.getId().getName();
            var name = gauge.getId().getTag(NAME);
            if (name != null) {
                resultsCache.putIfAbsent(name, new HashMap<>());
                resultsCache.get(name).put(key, gauge.value());
            } else {
                log.warn(MISSING_NAME_TAG_MESSAGE, key);
            }
        });

        return resultsCache;
    }

    private Map<String, Map<String, Map<String, Number>>> serviceMetrics() {
        var crudOperations = Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE");

        var uris = new HashSet<String>();

        var resultsService = new HashMap<String, Map<String, Map<String, Number>>>();

        meterRegistry.find(HTTP_SERVER_REQUESTS).timers()
            .forEach(timer -> uris.add(timer.getId().getTag(URI)));

        uris.forEach(uri -> {
            var resultsPerUri = new HashMap<String, Map<String, Number>>();

            crudOperations.forEach(operation -> {
                var resultsPerUriPerCrudOperation = new HashMap<String, Number>();

                var httpTimersStream = meterRegistry.find(HTTP_SERVER_REQUESTS)
                    .tags(URI, uri, METHOD, operation).timers();

                var count = httpTimersStream.stream()
                    .map(Timer::count)
                    .reduce(Long::sum)
                    .orElse(0L);

                if (count != 0) {
                    var max = httpTimersStream.stream()
                        .map(x -> x.max(TimeUnit.MILLISECONDS))
                        .reduce((x, y) -> x > y ? x : 0)
                        .orElse(0.0);

                    var totalTime = httpTimersStream.stream()
                        .map(x -> x.totalTime(TimeUnit.MILLISECONDS))
                        .reduce(Double::sum)
                        .orElse(0.0);

                    resultsPerUriPerCrudOperation.put(COUNT, count);
                    resultsPerUriPerCrudOperation.put(MAX, max);
                    resultsPerUriPerCrudOperation.put(MEAN, totalTime / count);

                    resultsPerUri.put(operation, resultsPerUriPerCrudOperation);
                }
            });

            resultsService.put(uri, resultsPerUri);
        });

        return resultsService;
    }

    private Map<String, Map<String, Number>> databaseMetrics() {
        var resultsDatabase = new HashMap<String, Map<String, Number>>();

        Search.in(meterRegistry).name(s -> s.contains(HIKARI)).timers().forEach(timer -> {
            var key = timer.getId().getName()
                .substring(timer.getId().getName().lastIndexOf('.') + 1);

            resultsDatabase.putIfAbsent(key, new HashMap<>());
            resultsDatabase.get(key).put(COUNT, timer.count());
            resultsDatabase.get(key).put(MAX, timer.max(TimeUnit.MILLISECONDS));
            resultsDatabase.get(key).put(TOTAL_TIME, timer.totalTime(TimeUnit.MILLISECONDS));
            resultsDatabase.get(key).put(MEAN, timer.mean(TimeUnit.MILLISECONDS));

            ValueAtPercentile[] percentiles = timer.takeSnapshot().percentileValues();
            for (ValueAtPercentile percentile : percentiles) {
                resultsDatabase.get(key)
                    .put(String.valueOf(percentile.percentile()),
                        percentile.value(TimeUnit.MILLISECONDS)
                    );
            }
        });

        Search.in(meterRegistry).name(s -> s.contains(HIKARI)).gauges().forEach(gauge -> {
            var key = gauge.getId().getName()
                .substring(gauge.getId().getName().lastIndexOf('.') + 1);

            resultsDatabase.putIfAbsent(key, new HashMap<>());
            resultsDatabase.get(key).put("value", gauge.value());
        });

        return resultsDatabase;
    }

    private Map<String, Object> garbageCollectorMetrics() {
        Map<String, Object> resultsGarbageCollector = new HashMap<>();

        Search.in(meterRegistry).name(s -> s.contains(JVM_GC_PAUSE)).timers().forEach(timer -> {
            var gcPauseResults = new HashMap<String, Number>();
            var key = timer.getId().getName();

            gcPauseResults.put(COUNT, timer.count());
            gcPauseResults.put(MAX, timer.max(TimeUnit.MILLISECONDS));
            gcPauseResults.put(TOTAL_TIME, timer.totalTime(TimeUnit.MILLISECONDS));
            gcPauseResults.put(MEAN, timer.mean(TimeUnit.MILLISECONDS));

            ValueAtPercentile[] percentiles = timer.takeSnapshot().percentileValues();
            for (ValueAtPercentile percentile : percentiles) {
                gcPauseResults.put(String.valueOf(percentile.percentile()), percentile.value(TimeUnit.MILLISECONDS));
            }

            resultsGarbageCollector.putIfAbsent(key, gcPauseResults);
        });

        Search.in(meterRegistry)
            .name(s -> s.contains(JVM_GC) && !s.contains(JVM_GC_PAUSE))
            .gauges()
            .forEach(gauge -> resultsGarbageCollector.put(gauge.getId().getName(), gauge.value()));

        Search.in(meterRegistry)
            .name(s -> s.contains(JVM_GC) && !s.contains(JVM_GC_PAUSE))
            .counters()
            .forEach(counter -> resultsGarbageCollector.put(counter.getId().getName(), counter.count()));

        var classesLoaded = Search.in(meterRegistry).name(s -> s.contains(JVM_CLASSES_LOADED))
            .gauges()
            .stream()
            .map(Gauge::value)
            .reduce(Double::sum)
            .orElse(0.0);

        resultsGarbageCollector.put(CLASSES_LOADED, classesLoaded);

        var classesUnloaded = Search.in(meterRegistry)
            .name(s -> s.contains(JVM_CLASSES_UNLOADED))
            .functionCounters()
            .stream()
            .map(FunctionCounter::count)
            .reduce(Double::sum)
            .orElse(0.0);

        resultsGarbageCollector.put(CLASSES_UNLOADED, classesUnloaded);

        return resultsGarbageCollector;
    }

    private Map<String, Number> processMetrics() {
        var resultsProcess = new HashMap<String, Number>();

        Search.in(meterRegistry)
            .name(s -> s.contains(CPU) || s.contains(SYSTEM) || s.contains(PROCESS))
            .gauges()
            .forEach(gauge -> resultsProcess.put(gauge.getId().getName(), gauge.value()));

        Search.in(meterRegistry).name(s -> s.contains(PROCESS))
            .timeGauges()
            .forEach(gauge -> resultsProcess.put(gauge.getId().getName(), gauge.value(TimeUnit.MILLISECONDS)));

        return resultsProcess;
    }

}
