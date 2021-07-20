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
import lombok.Setter;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author laminalfalah on 10/07/21
 */

public class ServiceFactoryBean implements FactoryBean<GroupedOpenApi> {

    @Setter
    private SwaggerProperties properties;

    @Setter
    private Components components;

    @Setter
    private String path;

    @Override
    public GroupedOpenApi getObject() {
        var helper = new SwaggerInfoHelper(properties);
        return GroupedOpenApi.builder()
                .group("Services " + properties.getTitle())
                .addOpenApiCustomiser(openApi -> {
                    openApi.setInfo(helper.info("Services"));
                    openApi.setComponents(components);
                })
                .pathsToExclude(path)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return GroupedOpenApi.class;
    }

}
