package io.github.laminalfalah.backend.common.exception;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.exception
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

import org.springframework.http.HttpStatus;

/**
 * @author laminalfalah on 08/07/21
 */

public class OAuth2AuthenticationException extends RuntimeException {

    public OAuth2AuthenticationException() {
        super(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    public OAuth2AuthenticationException(String message) {
        super(message);
    }

    public OAuth2AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
