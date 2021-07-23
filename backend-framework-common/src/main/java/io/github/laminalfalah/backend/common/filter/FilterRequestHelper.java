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

import io.github.laminalfalah.backend.common.payload.request.Direction;
import io.github.laminalfalah.backend.common.payload.request.Filter;
import io.github.laminalfalah.backend.common.payload.request.SortBy;
import io.github.laminalfalah.backend.common.properties.PagingProperties;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static io.github.laminalfalah.backend.common.filter.FilterHelper.*;

/**
 * @author laminalfalah on 07/07/21
 */

public class FilterRequestHelper {

    public static final String SORT_BY_SEPARATOR = ":";
    public static final String SORT_BY_SPLITTER = ";";
    public static final String EMPTY_STRING = "";

    private FilterRequestHelper() {
        throw new UnsupportedOperationException();
    }

    public static List<SortBy> getSortByList(String value, PagingProperties properties) {
        return StringUtils.isEmpty(value) ? Collections.emptyList() : toSortByList(value, properties);
    }

    public static Long getLong(String value, Long defaultValue) {
        return value == null ? defaultValue : toLong(value, defaultValue);
    }

    public static void setValueString(Class<?> type, Filter<?> filter, FilterField filterField, String value) {
        if (type == Object.class) {
            filterField.setValue(filter.getParams(), value);
        } else if (type == Character.class || type == Character.TYPE) {
            filterField.setValue(filter.getParams(), value.charAt(0));
        } else if (type == String.class) {
            filterField.setValue(filter.getParams(), defaultValue(value, filterField, filter));
        } else if (type == Boolean.class || type == Boolean.TYPE) {
            filterField.setValue(filter.getParams(), defaultValueBoolean(value));
        } else {
            setValueNumber(type, filter, filterField, value);
        }
    }

    private static void setValueNumber(Class<?> type, Filter<?> filter, FilterField filterField, String value) {
        if (type == Byte.class || type == Byte.TYPE) {
            filterField.setValue(filter.getParams(), defaultValue(value, filterField, filter, Byte::parseByte));
        } else if (type == Short.class || type == Short.TYPE) {
            filterField.setValue(filter.getParams(), defaultValue(value, filterField, filter, Short::parseShort));
        } else if (type == Integer.class || type == Integer.TYPE) {
            filterField.setValue(filter.getParams(), defaultValue(value, filterField, filter, Integer::parseInt));
        } else if (type == Long.class || type == Long.TYPE || type == Number.class) {
            filterField.setValue(filter.getParams(), defaultValue(value, filterField, filter, Long::parseLong));
        } else if (type == Double.class || type == Double.TYPE) {
            filterField.setValue(filter.getParams(), defaultValue(value, filterField, filter, Double::parseDouble));
        } else if (type == Float.class || type == Float.TYPE) {
            filterField.setValue(filter.getParams(), defaultValue(value, filterField, filter, Float::parseFloat));
        } else {
            setValueDateTime(type, filter, filterField, value);
        }
    }

    @SneakyThrows
    private static void setValueDateTime(Class<?> type, Filter<?> filter, FilterField filterField, String value) {
        if (type == Date.class) {
            filterField.setValue(filter.getParams(), DateUtils.parseDate(defaultValueDate(value), filterField.getDateFormatter()));
        } else if (type == LocalDate.class) {
            var localDate = DateUtils.parseDate(defaultValueDate(value), filterField.getDateFormatter())
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            filterField.setValue(filter.getParams(), localDate);
        } else if (type == LocalDateTime.class) {
            var localDateTime = DateUtils.parseDate(defaultValueDateTime(value), filterField.getDateTimeFormatter())
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            filterField.setValue(filter.getParams(), localDateTime);
        } else if (type == LocalTime.class) {
            var localTime = DateUtils.parseDate(defaultValueTime(value), filterField.getTimeFormatter())
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            filterField.setValue(filter.getParams(), localTime);
        }
    }

    private static List<SortBy> toSortByList(String request, PagingProperties properties) {
        return Arrays.stream(request.split(SORT_BY_SPLITTER))
                .map(s -> toSortBy(s, properties))
                .filter(Objects::nonNull)
                .filter(sortBy -> Objects.nonNull(sortBy.getColumn()))
                .collect(Collectors.toList());
    }

    private static SortBy toSortBy(String request, PagingProperties properties) {
        String sort = request.trim();

        if (StringUtils.isEmpty(sort.replace(SORT_BY_SEPARATOR, EMPTY_STRING)) || sort.startsWith(SORT_BY_SEPARATOR)) {
            return null;
        }

        String[] sortBy = sort.split(SORT_BY_SEPARATOR);

        return new SortBy(
                getAt(sortBy, 0, null),
                EnumUtils.getEnum(Direction.class,
                        getAt(sortBy, 1, properties.getDefaultDirection().name()).toUpperCase()
                )
        );
    }

    private static String getAt(String[] strings, int index, String defaultValue) {
        return strings.length <= index ? defaultValue : strings[index];
    }

    private static Long toLong(String value, Long defaultValue) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static <T extends Serializable> void addDefaultFilter(Filter<T> filter, String field) {
        addDefaultFilter(filter, field, Direction.ASC);
    }

    public static <T extends Serializable> void addDefaultFilter(Filter<T> filter, String field, Direction direction) {
        List<SortBy> sortByList = new ArrayList<>();
        sortByList.add(new SortBy(field, direction));
        filter.setSorts(sortByList);
    }

}
