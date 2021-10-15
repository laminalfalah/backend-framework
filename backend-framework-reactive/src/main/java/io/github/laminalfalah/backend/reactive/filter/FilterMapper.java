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

import static io.github.laminalfalah.backend.common.filter.FilterHelper.excludeDefaultPaging;
import static io.github.laminalfalah.backend.common.filter.FilterHelper.genericClassName;
import static io.github.laminalfalah.backend.common.filter.FilterHelper.valueOf;
import static io.github.laminalfalah.backend.common.filter.FilterRequestHelper.addDefaultFilter;
import static io.github.laminalfalah.backend.common.filter.FilterRequestHelper.getLong;
import static io.github.laminalfalah.backend.common.filter.FilterRequestHelper.getSortByList;
import static io.github.laminalfalah.backend.common.filter.FilterRequestHelper.setValueString;

import io.github.laminalfalah.backend.common.annotation.FilterColumn;
import io.github.laminalfalah.backend.common.annotation.FilterQueryParam;
import io.github.laminalfalah.backend.common.filter.ContractFilterMapper;
import io.github.laminalfalah.backend.common.filter.FilterField;
import io.github.laminalfalah.backend.common.payload.request.Filter;
import io.github.laminalfalah.backend.common.properties.PagingProperties;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author laminalfalah on 07/07/21
 */

@AllArgsConstructor
public class FilterMapper implements ContractFilterMapper<ServerHttpRequest> {

    private final PagingProperties properties;

    private Map<String, Object> properties(ServerHttpRequest request, PagingProperties properties) {
        Map<String, Object> params = new ConcurrentHashMap<>();

        request.getQueryParams().toSingleValueMap().forEach((k, v) -> {
            if (excludeDefaultPaging(k, properties) && v != null) {
                params.put(k ,v);
            }
        });

        return params;
    }

    @Override
    @SneakyThrows
    public Filter<Object> fromServerHttpRequest(MethodParameter parameter, Logger logger, ServerHttpRequest exchange) {
        var clazz = genericClassName(parameter);

        var filter = new Filter<>();

        filter.setParams(clazz.getConstructor().newInstance());

        setFilterParams(filter, exchange);

        defaultFilter(filter, exchange);

        if (properties.getLog() != null && properties.getLog()) {
            logger.info("{}", filter);
        }

        return filter;
    }

    private void defaultFilter(Filter<?> filter, ServerHttpRequest request) {
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
    }

    private void setFilterParams(Filter<?> filter, ServerHttpRequest request) {
        var validFilter = AnnotationUtils.findAnnotation(filter.getParams().getClass(), FilterQueryParam.class);

        if (validFilter != null) {
            for (var field: filter.getParams().getClass().getDeclaredFields()) {
                var column = field.getAnnotation(FilterColumn.class);

                var filterField = FilterField.builder().field(field).filterColumn(column).build();

                var type = filterField.getFieldType();

                setValueString(type, filter, filterField, setValueOf(request, filterField, column));
            }
        }
    }

    private String setValueOf(ServerHttpRequest request, FilterField filterField, FilterColumn column) {
        return valueOf(properties(request, properties), filterField, column);
    }

}
