package io.github.laminalfalah.backend.mvc.controller;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.mvc.controller
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laminalfalah on 12/10/21
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CustomErrorControllerTests.Application.class)
@AutoConfigureMockMvc
class CustomErrorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Testing DataIntegrityViolation")
    void dataIntegrityViolationException() throws Exception {
        mockMvc.perform(get("/example-error/data-integrity-violation")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Testing DataAccessResourceFailure")
    void dataAccessResourceFailureException() throws Exception {
        mockMvc.perform(get("/example-error/data-access-resource")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isInternalServerError());
    }

    @SpringBootApplication
    static class Application {

        @RestController
        @RequestMapping(path = "/example-error")
        public static class ExampleErrorController {

            @GetMapping("/data-integrity-violation")
            public String dataIntegrityViolation() {
                throw new DataIntegrityViolationException("Ups");
            }

            @GetMapping("/data-access-resource")
            public String dataAccessResources() {
                throw new DataAccessResourceFailureException("Ups");
            }

        }
    }
}
