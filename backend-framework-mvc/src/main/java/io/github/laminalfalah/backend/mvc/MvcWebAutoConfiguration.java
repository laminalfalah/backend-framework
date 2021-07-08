package io.github.laminalfalah.backend.mvc;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.mvc
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

import io.github.laminalfalah.backend.common.properties.PagingProperties;
import io.github.laminalfalah.backend.mvc.controller.MvcErrorController;
import io.github.laminalfalah.backend.mvc.controller.VersionController;
import io.github.laminalfalah.backend.mvc.filter.FilterRequestArgumentResolver;
import io.github.laminalfalah.backend.version.properties.VersionProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author laminalfalah on 07/07/21
 */

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(WebMvcConfigurer.class)
@AutoConfigureAfter(MvcAutoConfiguration.class)
@AllArgsConstructor
public class MvcWebAutoConfiguration implements WebMvcConfigurer {

    private final PagingProperties properties;

    private final VersionProperties versionProperties;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new FilterRequestArgumentResolver(properties));
    }

    @Bean
    @ConditionalOnMissingBean(VersionController.class)
    public VersionController versionController() {
        return new VersionController(versionProperties);
    }

    @Bean
    @ConditionalOnMissingBean(MvcErrorController.class)
    public MvcErrorController mvcErrorController() {
        return new MvcErrorController();
    }

}
