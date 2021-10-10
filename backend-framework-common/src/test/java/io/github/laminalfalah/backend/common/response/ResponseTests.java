package io.github.laminalfalah.backend.common.response;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.response
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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.laminalfalah.backend.common.helper.ResponseHelper;
import io.github.laminalfalah.backend.common.payload.request.Direction;
import io.github.laminalfalah.backend.common.payload.request.SortBy;
import io.github.laminalfalah.backend.common.payload.response.Paging;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author laminalfalah on 20/07/21
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ResponseTests.Application.ExampleController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ResponseTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("Testing Paging")
    void testPaging() throws Exception {
        mockMvc.perform(get("/example/paging").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paging.page", Matchers.is(1)))
                .andExpect(jsonPath("$.paging.size", Matchers.is(50)))
                .andExpect(jsonPath("$.paging.sorts[0].column", Matchers.is("name")))
                .andExpect(jsonPath("$.paging.sorts[0].direction", Matchers.is("ASC")));
    }

    @Test
    @Order(2)
    @DisplayName("Testing Paging Parameter")
    void testPagingParameter() throws Exception {
        mockMvc.perform(get("/example/paging")
                .queryParam("name", "Foo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paging.page", Matchers.is(1)))
                .andExpect(jsonPath("$.paging.size", Matchers.is(50)))
                .andExpect(jsonPath("$.paging.sorts[0].column", Matchers.is("name")))
                .andExpect(jsonPath("$.paging.sorts[0].direction", Matchers.is("ASC")))
                .andExpect(jsonPath("$.paging.params.name", Matchers.is("Foo")));
    }

    @Test
    @Order(3)
    @DisplayName("Testing Post Data")
    void testPostData() throws Exception {
        Application.ExampleRequest request = Application.ExampleRequest.builder().name("Foo").build();
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(request);

        mockMvc.perform(post("/example/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", Matchers.is("Foo")));
    }

    @SpringBootApplication
    public static class Application {

        @Data
        @Builder
        public static class ExampleFilter {
            private String name;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ExampleRequest {
            private String name;
        }

        @Data
        @Builder
        public static class ExampleResponse {
            private String name;
        }

        @RestController
        @RequestMapping(path = "/example", produces = MediaType.APPLICATION_JSON_VALUE)
        public static class ExampleController {

            @GetMapping("/paging")
            public ResponseEntity<?> paging(@RequestParam(value = "name", required = false) String name) {
                Paging<?> paging = Paging.builder()
                        .page(1L)
                        .size(50L)
                        .sorts(Collections.singletonList(
                                SortBy.builder()
                                        .column("name")
                                        .direction(Direction.ASC)
                                        .build()
                        ))
                        .totalItems(3L)
                        .totalPages(1L)
                        .params(ExampleFilter.builder().name(name).build())
                        .build();

                return ResponseEntity.ok(ResponseHelper.ok(Arrays.asList("Foo", "Bar", "Buz"), paging));
            }

            @PostMapping("/hello")
            public ResponseEntity<?> hello(@Validated @RequestBody ExampleRequest request) {
                ExampleResponse response = ExampleResponse.builder()
                        .name(request.getName())
                        .build();

                return ResponseEntity.ok(ResponseHelper.ok(response));
            }
        }
    }
}
