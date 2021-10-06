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

import io.github.laminalfalah.backend.common.exception.MethodHandleException;
import lombok.Getter;
import lombok.SneakyThrows;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author laminalfalah on 06/07/21
 */

public abstract class AbstractFieldAnnotationHelper<A extends Annotation> {

    @Getter
    private final A annotation;

    @Getter
    private final MethodHandle getter;

    @Getter
    private final MethodHandle setter;

    @Getter
    private final Field field;

    @SneakyThrows
    protected AbstractFieldAnnotationHelper(Field field, A annotation) {
        this.field = field;
        this.annotation = annotation;
        this.getter = ReflectHelper.findGetter(field);
        this.setter = ReflectHelper.findSetter(field);
    }

    @SneakyThrows
    public void setValue(Object obj, Object value) {
        this.setter.invoke(obj, value);
    }

    @SneakyThrows
    public Object getValue(Object obj) {
        return this.getter.invoke(obj);
    }

    public Class<?> getFieldType() {
        return this.field.getType();
    }

    public String getFieldName() {
        return this.field.getName();
    }

    protected static class ReflectHelper {

        private ReflectHelper() {
            throw new UnsupportedOperationException();
        }

        @SneakyThrows
        private static Method getMethod(Field field, MethodName name) {
            Method method;
            try {
                if (name.name().equalsIgnoreCase("GET")) {
                    method = new PropertyDescriptor(field.getName(), field.getDeclaringClass()).getReadMethod();
                } else {
                    method = new PropertyDescriptor(field.getName(), field.getDeclaringClass()).getWriteMethod();
                }
            } catch (IntrospectionException e) {
                String methodName = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                method = field.getDeclaringClass().getMethod(name + methodName);
            }
            return method;
        }

        protected static MethodHandle findGetter(Field field) {
            try {
                var method = getMethod(field, MethodName.GET);

                MethodHandle getter = method == null ? null : MethodHandles.lookup().unreflect(method);

                if (getter == null) {
                    throw new MethodHandleException(
                            String.format("No getter specified with field '%s' for class '%s'",
                                    field.getName(),
                                    field.getDeclaringClass()
                            )
                    );
                } else {
                    return getter;
                }
            } catch (IllegalAccessException e) {
                throw new MethodHandleException(
                        String.format("Unable to access getter specified with field '%s' for class '%s'",
                                field.getName(),
                                field.getDeclaringClass()
                        ), e);
            }
        }

        protected static MethodHandle findSetter(Field field) {
            try {
                var method = getMethod(field, MethodName.SET);

                MethodHandle setter = method == null ? null : MethodHandles.lookup().unreflect(method);

                if (setter == null) {
                    throw new MethodHandleException(
                            String.format("No setter specified with field '%s' for class '%s'",
                                    field.getName(),
                                    field.getDeclaringClass()
                            )
                    );
                } else {
                    return setter;
                }
            } catch (IllegalAccessException e) {
                throw new MethodHandleException(
                        String.format("Unable to access setter specified with field '%s' for class '%s'",
                                field.getName(),
                                field.getDeclaringClass()
                        ), e);
            }
        }
    }

    private enum MethodName {
        GET, SET
    }

}
