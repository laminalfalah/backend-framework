package io.github.laminalfalah.backend.mvc.validator;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.mvc.validator
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

import io.github.laminalfalah.backend.mvc.annotation.PasswordMatches;
import io.github.laminalfalah.backend.validation.mvc.AbstractConstraintValidator;

import javax.validation.ConstraintValidatorContext;

import static io.github.laminalfalah.backend.validation.helper.UtilsHelper.getObjectField;

/**
 * @author laminalfalah on 23/07/21
 */

public class PasswordMatchesValidator extends AbstractConstraintValidator<PasswordMatches, Object> {

    @Override
    public Boolean validate(Object value, PasswordMatches annotation, ConstraintValidatorContext context) {
        var password = getObjectField(value, annotation.fieldPassword());
        var confirmPassword = getObjectField(value, annotation.fieldConfirmPassword());

        if (password != null && password.equals(confirmPassword)) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(annotation.message())
                .addPropertyNode(annotation.fieldPassword())
                .addConstraintViolation()
                .getDefaultConstraintMessageTemplate();

        return false;
    }

}
