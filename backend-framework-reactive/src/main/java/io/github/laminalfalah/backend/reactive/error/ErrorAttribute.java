package io.github.laminalfalah.backend.reactive.error;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.reactive.error
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

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Collections;
import java.util.Map;

/**
 * @author laminalfalah on 08/07/21
 */

@Component
public class ErrorAttribute extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errors = super.getErrorAttributes(request, options);

        Object timestamp = errors.get("timestamp");
        Object error = errors.get("error");
        Object status = errors.get("status");

        errors.clear();

        errors.put("timestamp", timestamp);
        errors.put("code", status);
        errors.put("message", error);
        errors.put("errors", Collections.emptyList());

        return errors;
    }
}
