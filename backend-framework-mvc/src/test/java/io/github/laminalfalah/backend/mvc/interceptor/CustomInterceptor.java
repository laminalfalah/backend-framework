package io.github.laminalfalah.backend.mvc.interceptor;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.mvc.interceptor
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
import org.springframework.stereotype.Component;

/**
 * @author laminalfalah on 12/10/21
 */

@Component
public class CustomInterceptor implements AuthenticationInterceptor {

    @Override
    public boolean isPreHandle(Authentication authentication, String token) {
        if (token.equals("1234567890")) {
            return true;
        } else {
            throw new UnauthorizedException();
        }
    }

}
