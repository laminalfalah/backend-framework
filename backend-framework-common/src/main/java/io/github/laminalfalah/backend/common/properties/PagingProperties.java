package io.github.laminalfalah.backend.common.properties;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.properties
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

import io.github.laminalfalah.backend.common.payload.request.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author laminalfalah on 07/07/21
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("backend.paging")
public class PagingProperties {

    private Long defaultPage = 1L;

    private Long defaultSize = 1000L;

    private Direction defaultDirection = Direction.ASC;

    private Long maxSizePerPage = 1000L;

    private Boolean log = true;

    private Query query = new Query();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Query implements Serializable {

        private String pageKey = "page";

        private String sizeKey = "size";

        private String sortKey = "sort";

    }
}
