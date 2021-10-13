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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.laminalfalah.backend.common.annotation.Authentication;
import io.github.laminalfalah.backend.mvc.interceptor.CustomInterceptor;
import io.github.laminalfalah.backend.mvc.payload.LoginRequest;
import io.github.laminalfalah.backend.mvc.service.AuthService;
import java.nio.charset.Charset;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laminalfalah on 12/10/21
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AuthControllerTests.Application.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private String requestLoginFailed;

    private String requestLoginSuccess;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        LoginRequest loginRequestFailed = LoginRequest.builder()
            .username("laminalfalah")
            .password("654321")
            .build();

        requestLoginFailed = mapper.writeValueAsString(loginRequestFailed);

        LoginRequest loginRequestSuccess = LoginRequest.builder()
            .username("laminalfalah")
            .password("123456")
            .build();

        requestLoginSuccess = mapper.writeValueAsString(loginRequestSuccess);
    }

    @Test
    @Order(1)
    @DisplayName("Testing Login Failed")
    void testLoginFailed() throws Exception {
        mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestLoginFailed)
                .characterEncoding(Charset.defaultCharset())
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(2)
    @DisplayName("Testing Login Success")
    void testLoginSuccess() throws Exception {
        mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestLoginSuccess)
                .characterEncoding(Charset.defaultCharset())
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    @DisplayName("Testing WhoAmI Failed Not Valid Token")
    void testWhoAmIFailedNotValidToken() throws Exception {
        mockMvc.perform(get("/auth/who-am-i")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "12345678")
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(4)
    @DisplayName("Testing WhoAmI Failed Is Blank Token")
    void testWhoAmIFailedIsBlankToken() throws Exception {
        mockMvc.perform(get("/auth/who-am-i")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "")
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(5)
    @DisplayName("Testing WhoAmI Success")
    void testWhoAmISuccess() throws Exception {
        mockMvc.perform(get("/auth/who-am-i")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "1234567890"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
    }

    @SpringBootApplication
    @Import(CustomInterceptor.class)
    static class Application {

        @RestController
        @RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
        @AllArgsConstructor
        @Import(AuthService.class)
        static class AuthController {

            private final AuthService service;

            @PostMapping
            public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
                return ResponseEntity.ok(service.doLogin(request));
            }

            @Authentication
            @GetMapping("/who-am-i")
            public ResponseEntity<String> whoAmI() {
                return ResponseEntity.ok(service.doWhoAmI());
            }
        }
    }
}
