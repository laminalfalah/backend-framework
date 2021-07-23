package io.github.laminalfalah.backend.common.helper;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.helper
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

import io.github.laminalfalah.backend.common.annotation.MetaData;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.*;

/**
 * @author laminalfalah on 08/07/21
 */

public class ErrorHelper {

    private final Logger logger;

    public ErrorHelper(Logger logger) {
        this.logger = logger;
    }

    public Map<String, List<String>> from(BindingResult result, MessageSource messageSource) {
        return from(result, messageSource, Locale.getDefault());
    }

    protected Map<String, List<String>> from(BindingResult result, MessageSource messageSource, Locale locale) {
        Map<String, List<String>> map = new HashMap<>();

        if (result.hasFieldErrors()) {
            result.getFieldErrors().forEach(fieldError -> {
                var field = fieldError.getField();

                var errorMessage = messageSource.getMessage(Objects.requireNonNull(fieldError.getCode()),
                        fieldError.getArguments(), fieldError.getDefaultMessage(), locale);

                if (!map.containsKey(field)) {
                    map.put(field, new ArrayList<>());
                }

                map.get(field).add(errorMessage);
            });
        }

        return map;
    }

    public Map<String, List<String>> from(Set<ConstraintViolation<?>> constraintViolations) {
        Map<String, List<String>> map = new HashMap<>();

        constraintViolations.forEach(violation -> {
            for (String attr : getAttributes(violation)) {
                if (!map.containsKey(attr)) {
                    map.put(attr, new ArrayList<>());
                }
                map.get(attr).add(violation.getMessage());
            }
        });

        return map;
    }

    static String[] getAttributes(ConstraintViolation<?> constraintViolation) {
        String[] values = (String[]) constraintViolation.getConstraintDescriptor().getAttributes().get("path");
        return values == null || values.length == 0 ? getAttributesFromPath(constraintViolation) : values;
    }

    static String[] getAttributesFromPath(ConstraintViolation<?> constraintViolation) {
        var path = constraintViolation.getPropertyPath();

        var builder = new StringBuilder();
        path.forEach(node -> {
            if (node.getName() != null) {
                if (builder.length() > 0) {
                    builder.append(".");
                }

                builder.append(node.getName());
            }
        });

        return new String[]{builder.toString()};
    }

    public Map<String, Map<String, String>> getMetaData(Set<ConstraintViolation<?>> constraintViolations) {
        Map<String, Map<String, String>> metadata = new HashMap<>();
        constraintViolations.forEach(violation -> {
            try {
                Class<?> beanClass = violation.getLeafBean().getClass();

                var field = "";
                for (Path.Node node : violation.getPropertyPath()) {
                    field = node.getName();
                }

                var declaredField = beanClass.getDeclaredField(field);
                var metaDataList = declaredField.getAnnotation(MetaData.List.class);

                if (metaDataList != null) {
                    Map<String, String> values = new HashMap<>();

                    for (MetaData metaData : metaDataList.value()) {
                        values.put(metaData.key(), metaData.value());
                    }

                    for (String attribute : getAttributes(violation)) {
                        metadata.put(attribute, values);
                    }
                }

            } catch (RuntimeException | NoSuchFieldException e) {
                logger.warn(e.getMessage(), e);
            }
        });

        return metadata;
    }

}
