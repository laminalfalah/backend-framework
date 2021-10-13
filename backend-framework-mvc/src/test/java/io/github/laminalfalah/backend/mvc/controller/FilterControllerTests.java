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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.laminalfalah.backend.common.helper.PagingHelper;
import io.github.laminalfalah.backend.common.helper.ResponseHelper;
import io.github.laminalfalah.backend.common.payload.request.Filter;
import io.github.laminalfalah.backend.common.payload.response.Paging;
import io.github.laminalfalah.backend.mvc.payload.ExampleFilter;
import io.github.laminalfalah.backend.mvc.payload.ExampleResponse;
import io.github.laminalfalah.backend.mvc.service.ExampleService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laminalfalah on 20/07/21
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FilterControllerTests.Application.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilterControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("Testing Filter")
    void testingFilter() throws Exception {
        mockMvc.perform(get("/example").content(MediaType.APPLICATION_JSON_VALUE))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.paging.page", Matchers.is(1)))
            .andExpect(jsonPath("$.paging.size", Matchers.is(1000)))
            .andExpect(jsonPath("$.paging.totalItems", Matchers.is(26)))
            .andExpect(jsonPath("$.paging.sorts[0].column", Matchers.is("createdAt")))
            .andExpect(jsonPath("$.paging.sorts[0].direction", Matchers.is("DESC")))
            .andExpect(jsonPath("$.data[0].name", Matchers.is("A")));
    }

    @Test
    @Order(2)
    @DisplayName("Testing Filter Parameter")
    void testingFilterParameter() throws Exception {
        mockMvc.perform(get("/example")
                .param("name", "a")
                .content(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].name", Matchers.is("A")));
    }

    @Test
    @Order(3)
    @DisplayName("Testing Filter Parameter Page, Size")
    void testingFilterParameterPageSize() throws Exception {
        mockMvc.perform(get("/example")
                .param("page", "1")
                .param("size", "50")
                .content(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.paging.page", Matchers.is(1)))
            .andExpect(jsonPath("$.paging.size", Matchers.is(50)));
    }

    @SpringBootApplication
    static class Application {

        @RestController
        @RequestMapping(path = "/example", produces = MediaType.APPLICATION_JSON_VALUE)
        @AllArgsConstructor
        @Import(ExampleService.class)
        static class ExampleController {

            private final ExampleService service;

            @GetMapping
            public ResponseEntity<?> index(Filter<ExampleFilter> filter) {
                List<ExampleResponse> responses = service.index(filter);
                Paging<ExampleFilter> paging = PagingHelper.toPaging(filter, (long) responses.size());

                return ResponseEntity.ok(ResponseHelper.ok(responses, paging));
            }
        }
    }
}
