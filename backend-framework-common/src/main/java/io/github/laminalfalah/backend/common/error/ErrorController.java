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
import io.github.laminalfalah.backend.common.helper.ErrorHelper;
import io.github.laminalfalah.backend.common.helper.ResponseHelper;
import io.github.laminalfalah.backend.common.payload.response.ResponseError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

/**
 * @author laminalfalah on 08/07/21
 */

public interface ErrorController extends MessageSourceAware {

    Logger getLogger();

    MessageSource getMessageSource();

    default ErrorHelper getErrorHelper() {
        return new ErrorHelper(getLogger());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    default ResponseEntity<ResponseError> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        getLogger().error(HttpMessageNotReadableException.class.getName(), e);

        return ResponseEntity.badRequest().body(ResponseHelper.set(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    default ResponseEntity<ResponseError> unauthorizedException(UnauthorizedException e) {
        getLogger().error(UnauthorizedException.class.getName(), e);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseHelper.set(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(MethodNotAllowedException.class)
    default ResponseEntity<ResponseError> methodNotAllowedException(MethodNotAllowedException e) {
        getLogger().error(MethodNotAllowedException.class.getName(), e);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ResponseHelper.set(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(NotAcceptableStatusException.class)
    default ResponseEntity<ResponseError> notAcceptableStatusException(NotAcceptableStatusException e) {
        getLogger().error(NotAcceptableStatusException.class.getName(), e);

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseHelper.set(HttpStatus.NOT_ACCEPTABLE, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(UnsupportedMediaTypeStatusException.class)
    default ResponseEntity<ResponseError> unsupportedMediaTypeStatusException(UnsupportedMediaTypeStatusException e) {
        getLogger().error(UnsupportedMediaTypeStatusException.class.getName(), e);

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(ResponseHelper.set(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpMessageNotWritableException.class)
    default ResponseEntity<ResponseError> httpMessageNotWritableException(HttpMessageNotWritableException e) {
        getLogger().error(HttpMessageNotWritableException.class.getName(), e);

        return ResponseEntity.internalServerError().body(ResponseHelper.set(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServerErrorException.class)
    default ResponseEntity<ResponseError> serverErrorException(ServerErrorException e) {
        getLogger().error(ServerErrorException.class.getName(), e);

        return ResponseEntity.internalServerError().body(ResponseHelper.set(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    default ResponseEntity<ResponseError> exception(Exception e) {
        getLogger().error(Exception.class.getName(), e);

        return ResponseEntity.internalServerError().body(ResponseHelper.set(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    default ResponseEntity<ResponseError> throwable(Throwable e) {
        getLogger().error(Throwable.class.getName(), e);

        return ResponseEntity.internalServerError().body(ResponseHelper.set(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    default ResponseEntity<ResponseError> runtimeException(RuntimeException e) {
        getLogger().error(RuntimeException.class.getName(), e);

        return ResponseEntity.internalServerError().body(ResponseHelper.set(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    default ResponseEntity<ResponseError> responseStatusException(ResponseStatusException e) {
        getLogger().error(ResponseStatusException.class.getName(), e);

        Map<String, List<String>> errors = new HashMap<>();
        errors.put("reason", Collections.singletonList(e.getReason()));

        return ResponseEntity.status(e.getStatus()).body(ResponseHelper.set(e.getStatus(), errors));
    }

    @ExceptionHandler(NestedRuntimeException.class)
    default ResponseEntity<ResponseError> nestedRuntimeException(NestedRuntimeException e) {
        getLogger().error(NestedRuntimeException.class.getName(), e);

        return ResponseEntity.internalServerError().body(ResponseHelper.set(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(ServerWebInputException.class)
    default ResponseEntity<ResponseError> serverWebInputException(ServerWebInputException e) {
        getLogger().error(ServerWebInputException.class.getName(), e);

        Map<String, List<String>> errors = new HashMap<>();

        errors.put("reason", Collections.singletonList(e.getReason()));

        return ResponseEntity.status(e.getStatus()).body(ResponseHelper.set(e.getStatus(), errors));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    default ResponseEntity<ResponseError> webExchangeBindException(WebExchangeBindException e) {
        getLogger().error(WebExchangeBindException.class.getName(), e);

        return ResponseEntity.status(e.getStatus()).body(
                ResponseHelper.set(
                        HttpStatus.BAD_REQUEST,
                        getErrorHelper().from(e.getBindingResult(), getMessageSource())
                )
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    default ResponseEntity<ResponseError> constraintViolationException(ConstraintViolationException e) {
        getLogger().error(ConstraintViolationException.class.getName(), e);

        return ResponseEntity.badRequest().body(
                ResponseError.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .errors(getErrorHelper().from(e.getConstraintViolations()))
                        .metadata(
                                Collections.singletonMap(
                                        "errors", getErrorHelper().getMetaData(e.getConstraintViolations())
                                )
                        )
                        .build()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidPropertyException.class)
    default ResponseEntity<ResponseError> invalidPropertyException(InvalidPropertyException e) {
        getLogger().error(InvalidPropertyException.class.getName(), e);

        return ResponseEntity.badRequest().body(
                ResponseHelper.set(
                        HttpStatus.BAD_REQUEST,
                        Collections.singletonMap("reason", Collections.singletonList(e.getPropertyName()))
                )
        );
    }

    @ExceptionHandler(TypeMismatchException.class)
    default ResponseEntity<ResponseError> typeMismatchException(TypeMismatchException e) {
        getLogger().error(TypeMismatchException.class.getName(), e);

        Map<String, List<String>> errors = new HashMap<>();

        errors.put("reason", List.of(e.getErrorCode(), e.getPropertyName() != null ? e.getPropertyName() : ""));

        return ResponseEntity.internalServerError().body(ResponseHelper.set(HttpStatus.INTERNAL_SERVER_ERROR, errors));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    default ResponseEntity<ResponseError> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        getLogger().error(MethodArgumentNotValidException.class.getName(), e);

        return ResponseEntity.badRequest().body(
                ResponseHelper.set(
                        HttpStatus.BAD_REQUEST,
                        getErrorHelper().from(e.getBindingResult(), getMessageSource())
                )
        );
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    default ResponseEntity<ResponseError> asyncRequestTimeoutException(AsyncRequestTimeoutException e) {
        getLogger().error(AsyncRequestTimeoutException.class.getName(), e);

        return ResponseEntity.internalServerError().body(ResponseHelper.set(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    default ResponseEntity<ResponseError> dataNotFoundException(DataNotFoundException e) {
        getLogger().error(DataNotFoundException.class.getName(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseHelper.set(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    default ResponseEntity<ResponseError> dataIntegrityViolationException(DataIntegrityViolationException e) {
        getLogger().error(DataIntegrityViolationException.class.getName(), e.getMessage());

        Map<String, List<String>> errors = new HashMap<>();

        var data = Arrays.stream(Objects.requireNonNull(e.getMessage()).split(";"))
                .filter(s -> s.contains("violates"))
                .filter(s -> !s.contains("Exception"))
                .map(String::trim)
                .map(s -> s.replace("idx_", ""))
                .collect(Collectors.toSet());

        errors.put("reason", new ArrayList<>(data));

        return ResponseEntity.internalServerError().body(
                ResponseHelper.set(HttpStatus.INTERNAL_SERVER_ERROR, errors)
        );
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    default ResponseEntity<ResponseError> dataAccessResourceFailureException(DataAccessResourceFailureException e) {
        getLogger().error(DataAccessResourceFailureException.class.getName(), e);

        Map<String, List<String>> errors = new HashMap<>();

        var data = Arrays.stream(Objects.requireNonNull(e.getMessage()).split(":"))
                .filter(s -> !s.contains("Exception"))
                .map(String::trim)
                .collect(Collectors.toSet());

        errors.put("reason", new ArrayList<>(data));

        return ResponseEntity.internalServerError().body(
                ResponseHelper.set(HttpStatus.INTERNAL_SERVER_ERROR, errors)
        );
    }

    @ExceptionHandler(TypeMismatchDataAccessException.class)
    default ResponseEntity<ResponseError> typeMismatchDataAccessException(TypeMismatchDataAccessException e) {
        getLogger().error(TypeMismatchDataAccessException.class.getName(), e);

        Map<String, List<String>> errors = new HashMap<>();

        var data = Arrays.stream(Objects.requireNonNull(e.getMessage()).split(";"))
                .filter(s -> !s.contains("Exception"))
                .map(String::trim)
                .collect(Collectors.toSet());

        errors.put("reason", new ArrayList<>(data));

        return ResponseEntity.internalServerError().body(
                ResponseHelper.set(HttpStatus.INTERNAL_SERVER_ERROR, errors)
        );
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    default ResponseEntity<ResponseError> objectOptimisticLockingFailure(ObjectOptimisticLockingFailureException e) {
        getLogger().error(ObjectOptimisticLockingFailureException.class.getName(), e);

        return ResponseEntity.internalServerError().body(
            ResponseHelper.set(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
        );
    }
}
