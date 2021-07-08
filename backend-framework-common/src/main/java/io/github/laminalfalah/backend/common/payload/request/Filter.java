package io.github.laminalfalah.backend.common.payload.request;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.payload.request
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author laminalfalah on 06/07/21
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Filter<T extends Serializable> implements Serializable {

    @JsonProperty("page")
    private Long page;

    @JsonProperty("size")
    private Long size;

    @JsonProperty("sort")
    private List<SortBy> sorts;

    @JsonIgnore
    private T params;

    @SneakyThrows
    public Filter(Class<T> params) {
        this.params = params.getConstructor().newInstance();
    }

}