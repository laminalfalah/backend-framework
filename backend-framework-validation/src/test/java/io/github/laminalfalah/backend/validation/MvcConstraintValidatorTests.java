package io.github.laminalfalah.backend.validation;

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

import io.github.laminalfalah.backend.validation.mvc.AbstractConstraintValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.*;
import java.lang.annotation.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author laminalfalah on 23/07/21
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MvcConstraintValidatorTests.Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MvcConstraintValidatorTests {

    @Autowired
    private Validator validator;

    @Test
    @Order(1)
    @DisplayName("Testing Validator Valid")
    void testValid() {
        ExampleRequest request = ExampleRequest.builder().name("Foo").build();

        Set<ConstraintViolation<ExampleRequest>> constraintViolations = validator.validate(request);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @Order(2)
    @DisplayName("Testing Validator Invalid")
    void testInvalid() {
        ExampleRequest request = ExampleRequest.builder().name("Bar").build();

        Set<ConstraintViolation<ExampleRequest>> constraintViolations = validator.validate(request);
        assertFalse(constraintViolations.isEmpty());
    }

    @SpringBootApplication
    static class Application {}

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class ExampleRequest {

        @MustValid(message = "Must Valid")
        private String name;
    }

    @Documented
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = {
            MustValidator.class
    })
    @interface MustValid {
        String message();

        Class<?>[] payload() default {};

        Class<? extends Payload>[] groups() default {};
    }

    static class MustValidator extends AbstractConstraintValidator<MustValid, String> {

        @Override
        public Boolean validate(String value, MustValid annotation, ConstraintValidatorContext context) {
            return "Foo".equals(value);
        }

    }

}
