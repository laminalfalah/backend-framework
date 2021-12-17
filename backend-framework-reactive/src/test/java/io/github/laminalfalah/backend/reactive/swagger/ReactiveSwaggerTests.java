package io.github.laminalfalah.backend.reactive.swagger;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.reactive.swagger
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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author laminalfalah on 16/10/21
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class ReactiveSwaggerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Testing Redirect Swagger UI")
    void testRedirectSwaggerUi() {
        webTestClient.get().uri(uri -> uri.path("/docs/swagger-ui/3.52.3/index.html")
                .queryParam("configUrl", "/v3/api-docs/swagger-config")
                .build()
            )
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody();
    }

    @SpringBootApplication
    static class Application { }

}
