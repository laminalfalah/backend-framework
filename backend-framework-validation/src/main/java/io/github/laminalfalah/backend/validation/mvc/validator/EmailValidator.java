package io.github.laminalfalah.backend.validation.mvc.validator;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.validation.mvc.validator
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

import io.github.laminalfalah.backend.validation.annotation.mvc.EmailValidation;
import io.github.laminalfalah.backend.validation.mvc.AbstractConstraintValidator;
import io.github.laminalfalah.backend.validation.properties.EmailPatternProperties;

import javax.validation.ConstraintValidatorContext;

import static io.github.laminalfalah.backend.validation.helper.EmailHelper.matcherEmail;

/**
 * @author laminalfalah on 06/07/21
 */

public class EmailValidator extends AbstractConstraintValidator<EmailValidation, String> {

    private final EmailPatternProperties properties;

    public EmailValidator(EmailPatternProperties properties) {
        this.properties = properties;
    }

    @Override
    public Boolean validate(String value, EmailValidation annotation, ConstraintValidatorContext context) {
        var matcher = matcherEmail(properties, value);

        if (matcher.matches()) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(annotation.message())
                .addConstraintViolation()
                .getDefaultConstraintMessageTemplate();

        return false;
    }
}
