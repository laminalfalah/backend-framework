package io.github.laminalfalah.backend.validation.reactive.validator;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.validation.reactive.validator
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

import io.github.laminalfalah.backend.validation.annotation.reactive.IpAddress;
import io.github.laminalfalah.backend.validation.reactive.AbstractReactiveConstraintValidator;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintValidatorContext;

import static io.github.laminalfalah.backend.validation.helper.UtilsHelper.isValidIpAddress;

/**
 * @author laminalfalah on 06/07/21
 */

public class IpAddressValidator extends AbstractReactiveConstraintValidator<IpAddress, String> {

    @Override
    public Mono<Boolean> validate(String value, IpAddress annotation, ConstraintValidatorContext context) {
        boolean isValid = isValidIpAddress(value);

        if (isValid) {
            return Mono.fromCallable(() -> true);
        }

        context.buildConstraintViolationWithTemplate(annotation.message())
                .addConstraintViolation()
                .getDefaultConstraintMessageTemplate();

        return Mono.fromCallable(() -> false);
    }

}
