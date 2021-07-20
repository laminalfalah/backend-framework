package io.github.laminalfalah.backend.common.swagger;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.swagger
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

import io.github.laminalfalah.backend.common.payload.request.Filter;
import io.github.laminalfalah.backend.swagger.api.SwaggerIgnoredParameter;

import java.lang.reflect.Parameter;

/**
 * @author laminalfalah on 09/07/21
 */

public class PagingRequestSwaggerIgnoredParameter implements SwaggerIgnoredParameter {

    @Override
    public boolean isIgnored(Parameter parameter) {
        return Filter.class.isAssignableFrom(parameter.getType());
    }

}