package io.github.laminalfalah.backend.common;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common
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
import io.github.laminalfalah.backend.common.swagger.PagingRequestSwaggerIgnoredParameter;
import io.github.laminalfalah.backend.swagger.SwaggerAutoConfiguration;
import io.github.laminalfalah.backend.swagger.api.SwaggerIgnoredParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.QueryParameter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author laminalfalah on 09/07/21
 */

@Configuration
@ConditionalOnClass(SwaggerAutoConfiguration.class)
@AutoConfigureAfter(CommonAutoConfiguration.class)
public class CommonSwaggerAutoConfiguration {

    @Bean
    public SwaggerIgnoredParameter pagingRequestSwaggerIgnoredParameter() {
        return new PagingRequestSwaggerIgnoredParameter();
    }

    @Bean
    public Parameter queryPagingRequestPage(PagingProperties properties) {
        return new QueryParameter()
                .name(properties.getQuery().getPageKey())
                .example(properties.getDefaultPage())
                .required(true);
    }

    @Bean
    public Parameter queryPagingRequestSize(PagingProperties properties) {
        return new QueryParameter()
                .name(properties.getQuery().getSizeKey())
                .example(properties.getDefaultSize())
                .required(true);
    }

    @Bean
    public Parameter queryPagingRequestSort(PagingProperties properties) {
        return new QueryParameter()
                .name(properties.getQuery().getSortKey())
                .required(false);
    }

}
