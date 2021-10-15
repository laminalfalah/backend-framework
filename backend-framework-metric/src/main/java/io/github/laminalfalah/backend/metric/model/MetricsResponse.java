package io.github.laminalfalah.backend.metric.model;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.metric.model
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

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laminalfalah on 16/10/21
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricsResponse {

    private Map<String, Map<String, Number>> jvm;

    private Map<String, Object> httpServerRequests;

    private Map<String, Map<String, Number>> cache;

    private Map<String, Map<String, Map<String, Number>>> services;

    private Map<String, Map<String, Number>> databases;

    private Map<String, Object> garbageCollector;

    private Map<String, Number> processMetrics;

}
