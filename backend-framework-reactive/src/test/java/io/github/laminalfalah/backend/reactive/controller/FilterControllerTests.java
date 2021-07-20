package io.github.laminalfalah.backend.reactive.controller;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.reactive.controller
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

import io.github.laminalfalah.backend.common.helper.PagingHelper;
import io.github.laminalfalah.backend.common.helper.ResponseHelper;
import io.github.laminalfalah.backend.common.payload.request.Filter;
import io.github.laminalfalah.backend.common.payload.response.Paging;
import io.github.laminalfalah.backend.reactive.payload.ExampleFilter;
import io.github.laminalfalah.backend.reactive.payload.ExampleResponse;
import io.github.laminalfalah.backend.reactive.service.ExampleService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author laminalfalah on 20/07/21
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FilterControllerTests.Application.class)
@AutoConfigureWebTestClient
class FilterControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Testing Filter")
    void testingFilter() {
        webTestClient.get().uri("/example")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.paging.page").isEqualTo(1)
                .jsonPath("$.paging.size").isEqualTo(1000)
                .jsonPath("$.paging.totalItems").isEqualTo(26)
                .jsonPath("$.data.[0].name").isEqualTo("A");
    }

    @Test
    @DisplayName("Testing Filter Parameter")
    void testingFilterParameter() {
        webTestClient.get().uri(uri -> uri.path("/example").queryParam("name", "a").build())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.data[0].name").isEqualTo("A");
    }

    @Test
    @DisplayName("Testing Filter Parameter Page, Size")
    void testingFilterParameterPageSize() {
        webTestClient.get().uri(uri -> uri.path("/example").queryParam("page", 1).queryParam("size", 50).build())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.paging.page").isEqualTo(1)
                .jsonPath("$.paging.size").isEqualTo(50);
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
