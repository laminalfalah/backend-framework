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
import io.github.laminalfalah.backend.common.helper.AbstractFieldAnnotationHelper;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author laminalfalah on 07/07/21
 */

public class FilterField extends AbstractFieldAnnotationHelper<FilterColumn> {

    @Getter
    private final String dateTimeFormatter;

    @Getter
    private final String dateFormatter;

    @Getter
    private final String timeFormatter;

    @Getter
    private final List<Operation> operations;

    public static FilterField.Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Field field;

        private FilterColumn filterColumn;

        public Builder field(Field field) {
            this.field = field;
            return this;
        }

        public Builder filterColumn(FilterColumn filterColumn) {
            this.filterColumn = filterColumn;
            return this;
        }

        public FilterField build() {
            return new FilterField(field, filterColumn);
        }
    }

    public FilterField(Field field, FilterColumn annotation) {
        super(field, annotation);
        this.dateFormatter = getFilterDateFormat(annotation);
        this.dateTimeFormatter = getFilterDateTimeFormat(annotation);
        this.timeFormatter = getFilterTimeFormat(annotation);
        this.operations = getOperations(annotation);
    }

    protected String getFilterParameterName(FilterColumn column) {
        if (column == null) {
            return getFieldName();
        } else if (!StringUtils.isEmpty(column.parameterName().trim())) {
            return column.parameterName();
        } else {
            return StringUtils.isEmpty(column.value()) ? getFieldName() : column.value();
        }
    }

    private String getFilterDateFormat(FilterColumn column) {
        return column == null || StringUtils.isEmpty(column.dateFormat().trim()) ? "yyyy-MM-dd" : column.dateFormat();
    }

    private String getFilterDateTimeFormat(FilterColumn column) {
        return column == null || StringUtils.isEmpty(column.dateTimeFormat().trim()) ? "yyyy-MM-dd HH:mm:ss" : column.dateTimeFormat();
    }

    private String getFilterTimeFormat(FilterColumn column) {
        return column == null || StringUtils.isEmpty(column.timeFormat().trim()) ? "HH:mm:ss" : column.timeFormat();
    }

    private List<Operation> getOperations(FilterColumn column) {
        return column == null ? Collections.singletonList(Operation.EQUALS) : Arrays.asList(column.operations());
    }

}
