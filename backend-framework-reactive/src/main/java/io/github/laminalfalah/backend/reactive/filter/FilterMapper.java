package io.github.laminalfalah.backend.reactive.filter;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.reactive.filter
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

import io.github.laminalfalah.backend.common.annotation.FilterColumn;
import io.github.laminalfalah.backend.common.filter.FilterField;
import io.github.laminalfalah.backend.common.filter.FilterHelper;
import io.github.laminalfalah.backend.common.payload.request.Filter;
import io.github.laminalfalah.backend.common.properties.PagingProperties;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.laminalfalah.backend.common.filter.FilterHelper.excludeDefaultPaging;
import static io.github.laminalfalah.backend.common.filter.FilterHelper.getGenericPackageName;
import static io.github.laminalfalah.backend.common.filter.FilterRequestHelper.*;

/**
 * @author laminalfalah on 07/07/21
 */

public class FilterMapper {

    private FilterMapper() {
        throw new UnsupportedOperationException();
    }

    private static Map<String, Object> properties(ServerHttpRequest request, PagingProperties properties) {
        Map<String, Object> params = new ConcurrentHashMap<>();

        request.getQueryParams().toSingleValueMap().forEach((k, v) -> {
            if (excludeDefaultPaging(k, properties) && v != null) {
                params.put(k ,v);
            }
        });

        return params;
    }

    @SneakyThrows
    protected static <T extends Serializable> Filter<T> fromServerHttpRequest(Logger log, ServerHttpRequest request, PagingProperties properties) {
        Filter<T> filter = FilterMapper.assign(request, properties);

        filter.setPage(getLong(
                request.getQueryParams().getFirst(properties.getQuery().getPageKey()),
                properties.getDefaultPage()
        ));

        filter.setSize(getLong(
                request.getQueryParams().getFirst(properties.getQuery().getSizeKey()),
                properties.getDefaultSize()
        ));

        if (filter.getSize() > properties.getMaxSizePerPage()) {
            filter.setSize(properties.getMaxSizePerPage());
        }

        filter.setSorts(getSortByList(
                request.getQueryParams().getFirst(properties.getQuery().getSortKey()),
                properties
        ));

        if (properties.getDefaultField() != null ||
            filter.getSorts().stream().anyMatch(v -> !v.getColumn().equalsIgnoreCase(properties.getDefaultField()))
        ) {
            addDefaultFilter(filter, properties.getDefaultField(), properties.getDefaultFieldDirection());
        }

        if (properties.getLog() != null && properties.getLog()) {
            log.info("{}", filter);
        }

        return filter;
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private static <T extends Serializable> Filter<T> assign(ServerHttpRequest request, PagingProperties pagingProperties) {
        var packageName = getGenericPackageName();
        var aClass = packageName == null ? null : (Class<T>) Class.forName(packageName);

        if (aClass == null) return new Filter<>();

        var filter = new Filter<>(aClass);

        var params = properties(request, pagingProperties);

        for (var field: filter.getParams().getClass().getDeclaredFields()) {
            var column = field.getAnnotation(FilterColumn.class);

            var filterField = FilterField.builder().field(field).filterColumn(column).build();

            var type = filterField.getFieldType();

            setValueString(type, filter, filterField, FilterHelper.valueOf(params, filterField, column));
        }

        return filter;
    }

}
