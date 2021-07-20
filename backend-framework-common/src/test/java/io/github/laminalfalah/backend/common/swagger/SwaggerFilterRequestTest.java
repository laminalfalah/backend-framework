package io.github.laminalfalah.backend.common.swagger;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.swagger
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

import io.github.laminalfalah.backend.common.annotation.PagingRequestInQuery;
import io.github.laminalfalah.backend.common.payload.request.Filter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author laminalfalah on 20/07/21
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SwaggerFilterRequestTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Testing Swagger Paging")
    void testSwaggerPaging() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.openapi", Matchers.is("3.0.1")))
                .andExpect(jsonPath("$.paths./paging.get.parameters[*].name", Matchers.contains("filter", "page", "size", "sort")))
                .andExpect(jsonPath("$.components.parameters.queryPagingRequestPage.name", Matchers.is("page")))
                .andExpect(jsonPath("$.components.parameters.queryPagingRequestSize.name", Matchers.is("size")))
                .andExpect(jsonPath("$.components.parameters.queryPagingRequestSort.name", Matchers.is("sort")))
                .andReturn();
    }

    @SpringBootApplication
    public static class Application {

        @RestController
        public static class ExampleController {

            @PagingRequestInQuery
            @GetMapping("/paging")
            public ResponseEntity<?> pagingRequest(Filter<?> filter) {
                return ResponseEntity.ok(filter);
            }

        }
    }
}
