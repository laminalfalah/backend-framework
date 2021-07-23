package io.github.laminalfalah.backend.mvc.validation;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.validation
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

import io.github.laminalfalah.backend.mvc.annotation.PasswordValidation;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author laminalfalah on 06/07/21
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PasswordValidationMvcTests {

    @Autowired
    private Validator validator;

    @Test
    @Order(1)
    @DisplayName("Testing Validation Password Valid")
    void testPasswordValid() {
        PasswordRequest request = PasswordHelper.passwordRequest("F@la2695");

        Set<ConstraintViolation<PasswordRequest>> constraintViolations = validator.validate(request);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @Order(2)
    @DisplayName("Testing Validation Password Invalid")
    void testPasswordInvalid() {
        PasswordRequest request = PasswordHelper.passwordRequest("fala2695");

        Set<ConstraintViolation<PasswordRequest>> constraintViolations = validator.validate(request);
        assertFalse(constraintViolations.isEmpty());
    }

    static class PasswordHelper {

        static PasswordRequest passwordRequest(String value) {
            return PasswordRequest.builder().password(value).build();
        }
    }

    @Data
    @Builder
    static class PasswordRequest {

        @PasswordValidation
        private String password;

    }

    @SpringBootApplication
    static class Application {}

}
