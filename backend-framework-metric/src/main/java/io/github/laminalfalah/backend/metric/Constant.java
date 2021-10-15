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

/**
 * @author laminalfalah on 15/10/21
 */

public class Constant {
    public static final String JVM = "jvm";
    public static final String ID = "id";
    public static final String USED = "used";
    public static final String MAX = "max";
    public static final String COMMITTED = "committed";
    public static final String JVM_GC = "jvm.gc";
    public static final String JVM_GC_PAUSE = "jvm.gc.pause";
    public static final String JVM_CLASSES_LOADED = "jvm.classes.loaded";
    public static final String JVM_CLASSES_UNLOADED = "jvm.classes.unloaded";
    public static final String JVM_MEMORY_USED = "jvm.memory.used";
    public static final String JVM_MEMORY_MAX = "jvm.memory.max";
    public static final String JVM_MEMORY_COMMITTED = "jvm.memory.committed";
    public static final String HTTP_SERVER_REQUESTS = "http.server.requests";
    public static final String STATUS = "status";
    public static final String COUNT = "count";
    public static final String MEAN = "mean";
    public static final String HTTP_PER_CODE = "httpPerCode";
    public static final String ALL = "all";
    public static final String CACHE = "cache";
    public static final String HIBERNATE = "hibernate";
    public static final String NAME = "name";
    public static final String RESULT = "result";
    public static final String SERVICES = "services";
    public static final String URI = "uri";
    public static final String METHOD = "method";
    public static final String DATABASES = "databases";
    public static final String HIKARI = "hikari";
    public static final String TOTAL_TIME = "totalTime";
    public static final String GARBAGE_COLLECTOR = "garbageCollector";
    public static final String PROCESS_METRICS = "processMetrics";
    public static final String CPU = "cpu";
    public static final String SYSTEM = "system";
    public static final String PROCESS = "process";
    public static final String CLASSES_LOADED = "classesLoaded";
    public static final String CLASSES_UNLOADED = "classesUnloaded";

    public static final String MISSING_NAME_TAG_MESSAGE = "Missing name tag for metric {}";

    private Constant() { }

}
