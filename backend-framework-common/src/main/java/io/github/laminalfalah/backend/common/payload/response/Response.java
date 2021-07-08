package io.github.laminalfalah.backend.common.payload.response;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.payload.response
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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author laminalfalah on 06/07/21
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("paging")
    private Paging<?> paging;

    @JsonProperty("data")
    private T data;

    @JsonProperty("errors")
    private Map<String, List<String>> errors;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

}
