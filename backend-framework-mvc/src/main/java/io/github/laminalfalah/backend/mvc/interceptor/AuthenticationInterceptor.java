package io.github.laminalfalah.backend.mvc.interceptor;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.interceptor
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

import io.github.laminalfalah.backend.common.annotation.Authentication;
import io.github.laminalfalah.backend.common.exception.UnauthorizedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author laminalfalah on 12/10/21
 */

public interface AuthenticationInterceptor extends HandlerInterceptor {

    String HEADER_AUTHORIZATION = "Authorization";

    @Override
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }

        var handlerMethod = (HandlerMethod) handler;

        var authentication = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Authentication.class);

        if (authentication == null) {
            return true;
        }

        var token = request.getHeader(HEADER_AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            throw new UnauthorizedException();
        }

        return isPreHandle(authentication, token);
    }

    boolean isPreHandle(Authentication authentication, String token);

}
