package io.github.laminalfalah.backend.reactive.validator;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.reactive.validator
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

import io.github.laminalfalah.backend.reactive.annotation.PasswordValidationReactive;
import io.github.laminalfalah.backend.validation.properties.PasswordProperties;
import io.github.laminalfalah.backend.validation.reactive.AbstractReactiveConstraintValidator;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintValidatorContext;

import static io.github.laminalfalah.backend.validation.helper.PasswordHelper.*;

/**
 * @author laminalfalah on 06/07/21
 */

public class PasswordValidatorReactive extends AbstractReactiveConstraintValidator<PasswordValidationReactive, String> {

    private final PasswordProperties properties;

    public PasswordValidatorReactive(PasswordProperties properties) {
        this.properties = properties;
    }

    @Override
    public Mono<Boolean> validate(String value, PasswordValidationReactive annotation, ConstraintValidatorContext context) {
        if (value == null) {
            return Mono.fromCallable(() -> false);
        }

        var validator = getPasswordValidator(properties);

        var result = getRuleResult(validator, value);

        if (result.isValid()) {
            return Mono.fromCallable(() -> true);
        }

        var messages = getMessages(validator, result);

        messages.forEach(v -> context.buildConstraintViolationWithTemplate(v)
                .addConstraintViolation()
                .disableDefaultConstraintViolation()
        );

        return Mono.fromCallable(() -> false);
    }

}
