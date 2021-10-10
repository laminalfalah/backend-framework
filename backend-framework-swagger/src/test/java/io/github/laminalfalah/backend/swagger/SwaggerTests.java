package io.github.laminalfalah.backend.swagger;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.swagger
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.Valid;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author laminalfalah on 10/10/21
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SwaggerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("Testing Swagger UI")
    void testSwaggerUi() throws Exception {
        mockMvc.perform(get("/docs/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Order(2)
    @DisplayName("Testing api-docs contains info")
    void testApiDocs() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.info.title", Matchers.is("Services Backend Framework")))
                .andExpect(jsonPath("$.info.version", Matchers.is("1.0.0")))
                .andReturn();
    }

    @Test
    @Order(3)
    @DisplayName("Testing api-docs contains paths")
    void testController() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.paths./home").isNotEmpty())
                .andExpect(jsonPath("$.paths./example").isNotEmpty())
                .andExpect(jsonPath("$.paths./hello").isNotEmpty())
                .andReturn();
    }

    @SpringBootApplication
    public static class Application {

        @RestController
        public static class HomeController {

            @GetMapping("/home")
            public String home() {
                return "Home";
            }

            @GetMapping(
                    value = "/example",
                    produces = MediaType.APPLICATION_JSON_VALUE
            )
            public ExampleResponse example() {
                return ExampleResponse.builder().name("Hello").build();
            }

            @PostMapping(
                    value = "/hello",
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE
            )
            public ExampleResponse hello(@Valid @RequestBody ExampleRequest request) {
                return ExampleResponse.builder().name(request.getName()).build();
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ExampleResponse {
            private String name;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ExampleRequest {
            private String name;
        }
    }
}
