package io.github.laminalfalah.backend.reactive.filter;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.reactive.filter
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

import io.github.laminalfalah.backend.common.filter.ContractFilterMapper;
import io.github.laminalfalah.backend.common.payload.request.Filter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author laminalfalah on 07/07/21
 */

@Slf4j
@AllArgsConstructor
public class FilterRequestArgumentResolver implements HandlerMethodArgumentResolver {

    private final ContractFilterMapper<ServerHttpRequest> mapper;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return Filter.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter methodParameter, BindingContext bindingContext, ServerWebExchange serverWebExchange) {
        return Mono.fromCallable(() -> mapper.fromServerHttpRequest(methodParameter, log, serverWebExchange.getRequest()));
    }

}
