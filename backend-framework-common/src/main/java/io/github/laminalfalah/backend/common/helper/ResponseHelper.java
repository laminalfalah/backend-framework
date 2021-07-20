package io.github.laminalfalah.backend.common.helper;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.helper
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

import io.github.laminalfalah.backend.common.payload.response.Paging;
import io.github.laminalfalah.backend.common.payload.response.Response;
import io.github.laminalfalah.backend.common.payload.response.ResponseError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @author laminalfalah on 06/07/21
 */

public final class ResponseHelper {

    private ResponseHelper() {
        throw new UnsupportedOperationException();
    }

    public static <T> Response<T> ok() {
        return ResponseHelper.ok(null);
    }

    public static <T> Response<T> ok(T data) {
        return ResponseHelper.ok(data, null);
    }

    public static <T> Response<T> ok(T data, Paging<?> paging) {
        return ResponseHelper.set(HttpStatus.OK, null, data, paging);
    }

    public static <T> Response<T> set(HttpStatus status, String message, T data) {
        return ResponseHelper.set(status, message, data, null);
    }

    private static <T> Response<T> set(HttpStatus status, String message, T data, Paging<?> paging) {
        return Response.<T>builder()
                .timestamp(Instant.now().toEpochMilli())
                .code(status.value())
                .message(message == null || StringUtils.isEmpty(message) ? status.getReasonPhrase() : message)
                .paging(paging)
                .data(data)
                .build();
    }

    public static ResponseError set(HttpStatus status, String message) {
        return ResponseHelper.set(status, message, null);
    }

    public static ResponseError set(HttpStatus status, Map<String, List<String>> errors) {
        return ResponseHelper.set(status, null, errors);
    }

    private static ResponseError set(HttpStatus status, String message, Map<String, List<String>> errors) {
        return ResponseError.builder()
                .timestamp(Instant.now().toEpochMilli())
                .code(status.value())
                .message(message == null || StringUtils.isEmpty(message) ? status.getReasonPhrase() : message)
                .errors(errors)
                .build();
    }

}
