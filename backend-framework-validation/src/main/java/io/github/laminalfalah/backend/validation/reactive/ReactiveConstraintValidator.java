package io.github.laminalfalah.backend.validation.reactive;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.validation.reactive
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

import reactor.core.publisher.Mono;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * @author laminalfalah on 06/07/21
 */

public interface ReactiveConstraintValidator<A extends Annotation, T> extends ConstraintValidator<A, T> {

    default boolean isValid(T value, ConstraintValidatorContext context) {
        return validate(value, context).block();
    }

    Mono<Boolean> validate(T value, ConstraintValidatorContext context);

}
