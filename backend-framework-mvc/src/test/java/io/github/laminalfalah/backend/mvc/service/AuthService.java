package io.github.laminalfalah.backend.mvc.service;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.mvc.service
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

import io.github.laminalfalah.backend.common.exception.UnauthorizedException;
import io.github.laminalfalah.backend.mvc.payload.LoginRequest;
import io.github.laminalfalah.backend.mvc.payload.LoginResponse;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author laminalfalah on 12/10/21
 */

@Validated
@Service
public class AuthService {

    public LoginResponse doLogin(LoginRequest request) {
        if (request.getUsername().equals("laminalfalah") && request.getPassword().equals("123456")) {
            return LoginResponse.builder()
                .token("1234567890")
                .build();
        } else {
            throw new UnauthorizedException();
        }
    }

    public String doWhoAmI() {
        return "Laminal Falah";
    }
}
