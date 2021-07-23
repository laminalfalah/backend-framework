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

import io.github.laminalfalah.backend.mvc.annotation.FieldMatches;
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
@SpringBootTest(classes = FieldMatchesValidatorMvcTests.Application.class)
class FieldMatchesValidatorMvcTests {

    @Autowired
    private Validator validator;

    @Test
    @Order(1)
    @DisplayName("Testing Field Validation Matched")
    void testFieldValidMatched() {
        FieldMatch fieldMatch = FieldMatch.builder()
                .textOne("Hello World")
                .textTwo("Hello World")
                .build();

        Set<ConstraintViolation<FieldMatch>> constraintViolations = validator.validate(fieldMatch);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @Order(2)
    @DisplayName("Testing Field Validation Invalid Matched")
    void testFieldInvalidMatched() {
        FieldMatch fieldMatch = FieldMatch.builder()
                .textOne("Hello World")
                .textTwo("Hello World!")
                .build();

        Set<ConstraintViolation<FieldMatch>> constraintViolations = validator.validate(fieldMatch);
        System.out.println(constraintViolations);
        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("Testing Field Validation Matched List")
    void testFieldValidMatchedList() {
        FieldMatchMultiple fieldMatch = FieldMatchMultiple.builder()
                .textOne("Hello World")
                .textTwo("Hello World")
                .textThree("Hello Java")
                .textFour("Hello Java")
                .build();

        Set<ConstraintViolation<FieldMatchMultiple>> constraintViolations = validator.validate(fieldMatch);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @Order(4)
    @DisplayName("Testing Field Validation Invalid Matched List")
    void testFieldInvalidMatchedList() {
        FieldMatchMultiple fieldMatch = FieldMatchMultiple.builder()
                .textOne("Hello World")
                .textTwo("Hello World!")
                .textThree("Hello Java")
                .textFour("Hello Java!")
                .build();

        Set<ConstraintViolation<FieldMatchMultiple>> constraintViolations = validator.validate(fieldMatch);
        assertFalse(constraintViolations.isEmpty());
    }

    @Data
    @Builder
    @FieldMatches(field = "textOne", fieldMatch = "textTwo")
    static class FieldMatch {

        private String textOne;

        private String textTwo;

    }

    @Data
    @Builder
    @FieldMatches.List({
            @FieldMatches(field = "textOne", fieldMatch = "textTwo"),
            @FieldMatches(field = "textThree", fieldMatch = "textFour")
    })
    static class FieldMatchMultiple {

        private String textOne;

        private String textTwo;

        private String textThree;

        private String textFour;
    }

    @SpringBootApplication
    static class Application {}

}
