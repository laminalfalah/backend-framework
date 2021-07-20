package io.github.laminalfalah.backend.swagger.factory;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.swagger.factory
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

import io.github.laminalfalah.backend.swagger.helper.SwaggerInfoHelper;
import io.github.laminalfalah.backend.swagger.properties.SwaggerProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author laminalfalah on 09/07/21
 */

public class OpenApiFactory implements FactoryBean<OpenAPI> {

    @Setter
    private SwaggerProperties properties;

    @Setter
    private Components components;

    @Override
    public OpenAPI getObject() {
        var helper = new SwaggerInfoHelper(properties);
        var openAPI = new OpenAPI();
        openAPI.setInfo(helper.info("Services"));
        openAPI.setComponents(components);
        return openAPI;
    }

    @Override
    public Class<?> getObjectType() {
        return OpenAPI.class;
    }

}
