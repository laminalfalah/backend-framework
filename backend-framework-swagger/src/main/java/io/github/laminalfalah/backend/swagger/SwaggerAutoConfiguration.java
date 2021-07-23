package io.github.laminalfalah.backend.swagger;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.swagger
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

import io.github.laminalfalah.backend.swagger.api.SwaggerIgnoredParameter;
import io.github.laminalfalah.backend.swagger.api.SwaggerIgnoredParameterAnnotation;
import io.github.laminalfalah.backend.swagger.factory.*;
import io.github.laminalfalah.backend.swagger.properties.SwaggerProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import java.util.ArrayList;

/**
 * @author laminalfalah on 05/07/21
 */

@Configuration
@EnableConfigurationProperties({
        SwaggerProperties.class
})
@PropertySource(
        ignoreResourceNotFound = true,
        value = "classpath:swagger.properties"
)
@Profile("swagger")
public class SwaggerAutoConfiguration {

    @Bean
    public ComponentsFactoryBean components(ApplicationContext context) {
        var parameters = context.getBeansOfType(Parameter.class);
        var componentsFactoryBean = new ComponentsFactoryBean();
        componentsFactoryBean.setParameters(parameters);
        return componentsFactoryBean;
    }

    @Bean
    public OpenApiFactory openApi(@Autowired Components components, @Autowired SwaggerProperties properties) {
        var openApiFactory = new OpenApiFactory();
        openApiFactory.setComponents(components);
        openApiFactory.setProperties(properties);
        return openApiFactory;
    }

    @Bean
    @ConditionalOnClass(name = "io.github.laminalfalah.backend.swagger.properties.SwaggerProperties")
    @ConditionalOnMissingBean(name = "serviceApi")
    public ServiceFactoryBean serviceApi(@Autowired Components components,
                                         @Autowired SwaggerProperties properties,
                                         @Value("${management.endpoints.web.base-path:/actuator}") String path) {
        var serviceFactoryBean = new ServiceFactoryBean();
        serviceFactoryBean.setComponents(components);
        serviceFactoryBean.setProperties(properties);
        serviceFactoryBean.setPath(path + "/**");
        return serviceFactoryBean;
    }

    @Bean
    @ConditionalOnClass(name = "org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties")
    @ConditionalOnMissingBean(name = "actuatorApi")
    public ActuatorFactoryBean actuatorApi(@Autowired Components components,
                                           @Autowired SwaggerProperties properties,
                                           @Value("${management.endpoints.web.base-path:/actuator}") String path) {
        var actuatorFactoryBean = new ActuatorFactoryBean();
        actuatorFactoryBean.setComponents(components);
        actuatorFactoryBean.setProperties(properties);
        actuatorFactoryBean.setPath(path + "/**");

        return actuatorFactoryBean;
    }

    @Bean
    public SwaggerIgnoredParameterAnnotation swaggerIgnoredParameterAnnotation() {
        return new SwaggerIgnoredParameterAnnotation();
    }

    @Bean
    @Primary
    public IgnoredParameterAnnotationFactoryBean ignoredParameterAnnotationFactoryBean(ApplicationContext context) {
        var ignoredParameters = context.getBeansOfType(SwaggerIgnoredParameter.class).values();
        var factoryBean = new IgnoredParameterAnnotationFactoryBean();
        factoryBean.setParameters(new ArrayList<>(ignoredParameters));
        return factoryBean;
    }

}
