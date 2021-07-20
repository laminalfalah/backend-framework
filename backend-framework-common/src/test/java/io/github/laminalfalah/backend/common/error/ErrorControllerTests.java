package io.github.laminalfalah.backend.common.error;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.error
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

import io.github.laminalfalah.backend.common.exception.DataNotFoundException;
import io.github.laminalfalah.backend.common.exception.UnauthorizedException;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.server.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author laminalfalah on 18/07/21
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ErrorControllerTests.Application.ExampleController.class)
class ErrorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("Testing HttpMessageNotReadableException")
    void httpMessageNotReadableException() throws Exception {
        mockMvc.perform(get("/example/http-message-not-readable-exception").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    @DisplayName("Testing Unauthorized")
    void unauthorizedException() throws Exception {
        mockMvc.perform(get("/example/unauthorized").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(3)
    @DisplayName("Testing MethodNotAllowed")
    void methodNotAllowedException() throws Exception {
        mockMvc.perform(get("/example/method-not-allowed").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @Order(4)
    @DisplayName("Testing NotAcceptableStatus")
    void notAcceptableStatusException() throws Exception {
        mockMvc.perform(get("/example/not-acceptable-status").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @Order(5)
    @DisplayName("Testing UnsupportedMediaTypeStatus")
    void unsupportedMediaTypeStatusException() throws Exception {
        mockMvc.perform(get("/example/unsupported-media-type-status").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @Order(6)
    @DisplayName("Testing HttpMessageNotWritable")
    void httpMessageNotWritableException() throws Exception {
        mockMvc.perform(get("/example/http-message-not-writable").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(7)
    @DisplayName("Testing HttpMessageNotWritable")
    void serverErrorException() throws Exception {
        mockMvc.perform(get("/example/http-message-not-writable").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(8)
    @DisplayName("Testing Exception")
    void exception() throws Exception {
        mockMvc.perform(get("/example/exception").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(9)
    @DisplayName("Testing Throwable")
    void throwable() throws Exception {
        mockMvc.perform(get("/example/throwable").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(10)
    @DisplayName("Testing RuntimeException")
    void runtimeException() throws Exception {
        mockMvc.perform(get("/example/runtime-exception").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(11)
    @DisplayName("Testing ResponseStatus")
    void responseStatusException() throws Exception {
        mockMvc.perform(get("/example/response-status").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(12)
    @DisplayName("Testing ServerWebInput")
    void serverWebInputException() throws Exception {
        mockMvc.perform(get("/example/server-web-input").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(13)
    @DisplayName("Testing WebExchangeBind")
    void webExchangeBindException() throws Exception {
        mockMvc.perform(post("/example/web-exchange-bind").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(14)
    @DisplayName("Testing ConstraintViolation")
    void constraintViolationException() throws Exception {
        mockMvc.perform(post("/example/constraint-violation").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(15)
    @DisplayName("Testing InvalidProperty")
    void invalidPropertyException() throws Exception {
        mockMvc.perform(get("/example/invalid-property").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(16)
    @DisplayName("Testing TypeMismatch")
    void typeMismatchException() throws Exception {
        mockMvc.perform(get("/example/type-mismatch").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(17)
    @DisplayName("Testing MethodArgumentNotValid")
    void methodArgumentNotValidException() throws Exception {
        mockMvc.perform(post("/example/method-argument-not-valid").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(18)
    @DisplayName("Testing AsyncRequestTimeout")
    void asyncRequestTimeoutException() throws Exception {
        mockMvc.perform(get("/example/async-request-timeout").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(19)
    @DisplayName("Testing DataNotFound")
    void dataNotFoundException() throws Exception {
        mockMvc.perform(get("/example/data-not-found").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(20)
    @DisplayName("Testing DataIntegrityViolation")
    void dataIntegrityViolationException() throws Exception {
        mockMvc.perform(get("/example/data-integrity-violation").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(21)
    @DisplayName("Testing DataAccessResourceFailure")
    void dataAccessResourceFailureException() throws Exception {
        mockMvc.perform(get("/example/data-access-resource").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @SpringBootApplication
    static class Application {

        @Data
        @Builder
        public static class ExampleRequest {
            @NotNull
            @NotEmpty
            @NotBlank
            private String name;
        }

        @Slf4j
        @ControllerAdvice
        @RestControllerAdvice
        public static class ErrorControllerImpl implements ErrorController {

            @Setter
            @Getter
            private MessageSource messageSource;

            @Override
            public Logger getLogger() {
                return log;
            }
        }

        @RestController
        @RequestMapping(path = "/example")
        public static class ExampleController {

            @GetMapping("/http-message-not-readable-exception")
            public String httpMessageNotReadableException() {
                throw new HttpMessageNotReadableException("Ups", new HttpInputMessage() {

                    @Override
                    public HttpHeaders getHeaders() {
                        return null;
                    }

                    @Override
                    public InputStream getBody() {
                        return null;
                    }
                });
            }

            @GetMapping("/unauthorized")
            public String unAuthorized() {
                throw new UnauthorizedException();
            }

            @GetMapping("/method-not-allowed")
            public String methodNotAllowed() {
                throw new MethodNotAllowedException("POST", Collections.singletonList(HttpMethod.GET));
            }

            @GetMapping("/not-acceptable-status")
            public String notAcceptable() {
                throw new NotAcceptableStatusException("Ups");
            }

            @GetMapping("/unsupported-media-type-status")
            public String unsupportedMediaTypeStatus() {
                throw new UnsupportedMediaTypeStatusException("Ups");
            }

            @GetMapping("/http-message-not-writable")
            public String httpMessageNotWritable() {
                throw new HttpMessageNotWritableException("Ups");
            }

            @GetMapping("/server-error")
            public String serverError() {
                throw new ServerErrorException("Ups", new Throwable());
            }

            @GetMapping("/exception")
            public String exception() throws Exception {
                throw new Exception("Ups");
            }

            @GetMapping("/throwable")
            public String throwable() throws Throwable {
                throw new Throwable("Ups");
            }

            @GetMapping("/runtime-exception")
            public String runtimeException() {
                throw new RuntimeException("Ups");
            }

            @GetMapping("/response-status")
            public String responseStatus() {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ups");
            }

            @GetMapping("/server-web-input")
            public String serverWebInput() {
                throw new ServerWebInputException("Ups");
            }

            @PostMapping("/web-exchange-bind")
            public String webExchangeBind(@Validated @RequestBody ExampleRequest request) {
                return "OK";
            }

            @PostMapping("/constraint-violation")
            public String constraintViolation(@Validated @RequestBody ExampleRequest request) {
                return "OK";
            }

            @GetMapping("/invalid-property")
            public String invalidProperty() {
                throw new InvalidPropertyException(ExampleRequest.class, "Ups", "Ups");
            }

            @GetMapping("/type-mismatch")
            public String typeMismatch() {
                throw new TypeMismatchException("Ups", null);
            }

            @PostMapping("/method-argument-not-valid")
            public String methodArgumentNotValid(@Validated @RequestBody ExampleRequest request) {
                return "OK";
            }

            @GetMapping("/async-request-timeout")
            public String asyncRequestTimeout() {
                throw new AsyncRequestTimeoutException();
            }

            @GetMapping("/data-not-found")
            public String dataNotFound() {
                throw new DataNotFoundException();
            }

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