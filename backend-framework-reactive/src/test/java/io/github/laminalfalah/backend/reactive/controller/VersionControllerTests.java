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

import io.github.laminalfalah.backend.version.properties.VersionProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author laminalfalah on 20/07/21
 */

@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import(VersionProperties.class)
class VersionControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Testing Version")
    void testingVersion() {
        webTestClient.get().uri("/version")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(result -> {
                    String response = new String(Objects.requireNonNull(result.getResponseBody()));
                    assertTrue(response.contains("maven.groupId="));
                    assertTrue(response.contains("maven.artifactId="));
                    assertTrue(response.contains("maven.pom.version="));
                    assertTrue(response.contains("maven.build.time="));
                });
    }

    @SpringBootApplication
    static class Application {}

}