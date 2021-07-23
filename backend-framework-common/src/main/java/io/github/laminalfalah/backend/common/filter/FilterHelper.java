package io.github.laminalfalah.backend.common.filter;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.filter
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
import io.github.laminalfalah.backend.common.payload.request.Filter;
import io.github.laminalfalah.backend.common.properties.PagingProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.MethodParameter;

import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author laminalfalah on 07/07/21
 */

public final class FilterHelper {

    private static String genericPackageName;

    private FilterHelper() {
        throw new UnsupportedOperationException();
    }

    public static String valueOf(Map<String, Object> params, FilterField field, FilterColumn column) {
        return (String) params.get(field.getFilterParameterName(column));
    }

    public static String getGenericPackageName() {
        return genericPackageName;
    }

    public static void setGenericPackageName(MethodParameter parameter) {
        var type = parameter.getGenericParameterType().getTypeName();

        if (type.contains("?")) {
            genericPackageName = null;
        } else {
            var pattern = Pattern.compile(".+?(?=<)", Pattern.MULTILINE);
            var matcher = pattern.matcher(type);

            genericPackageName = matcher.replaceAll("")
                    .replace("<", "")
                    .replace(">", "");
        }
    }

    public static boolean excludeDefaultPaging(String key, PagingProperties pagingProperties) {
        return !key.equalsIgnoreCase(pagingProperties.getQuery().getPageKey()) &&
                !key.equalsIgnoreCase(pagingProperties.getQuery().getSizeKey()) &&
                !key.equalsIgnoreCase(pagingProperties.getQuery().getSortKey());
    }

    protected static String defaultValue(String value, FilterField filterField, Filter<?> filter) {
        return value != null && !StringUtils.isEmpty(value.trim()) ? value : defaultValueGetter(filterField, filter);
    }

    protected static <T> T defaultValue(String value, FilterField filterField, Filter<?> filter, Function<String, T> func) {
        return value == null ? defaultValueGetter(filterField, filter) : func.apply(validateNumberNotNull(value));
    }

    protected static Boolean defaultValueBoolean(String value) {
        if (value != null && !StringUtils.isEmpty(value)) {
            return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1");
        } else {
            return value == null ? null : false;
        }
    }

    protected static String defaultValueDate(String value) {
        return value != null && !StringUtils.isEmpty(value.trim()) ? value : "0000-00-00";
    }

    protected static String defaultValueDateTime(String value) {
        return value != null && !StringUtils.isEmpty(value.trim()) ? value : "0000-00-00 00:00:00";
    }

    protected static String defaultValueTime(String value) {
        return value != null && !StringUtils.isEmpty(value.trim()) ? value : "00:00:00";
    }

    @SuppressWarnings("unchecked")
    private static <T> T defaultValueGetter(FilterField filterField, Filter<?> filter) {
        return filterField.getValue(filter.getParams()) != null ? (T) filterField.getValue(filter.getParams()) : null;
    }

    private static String validateNumberNotNull(String value) {
        return value != null && value.equalsIgnoreCase("null") ? "0" : defaultZeroIfNotNull(value);
    }

    private static String defaultZeroIfNotNull(String value) {
        return !NumberUtils.isCreatable(value) ? "0" : value;
    }

}
