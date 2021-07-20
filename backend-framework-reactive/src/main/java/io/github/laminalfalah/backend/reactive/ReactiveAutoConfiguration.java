package io.github.laminalfalah.backend.reactive;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.reactive
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

import io.github.laminalfalah.backend.common.CommonAutoConfiguration;
import io.github.laminalfalah.backend.reactive.controller.ReactiveErrorController;
import io.github.laminalfalah.backend.reactive.controller.VersionController;
import io.github.laminalfalah.backend.reactive.error.ErrorAttribute;
import io.github.laminalfalah.backend.reactive.error.ErrorWebExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author laminalfalah on 05/07/21
 */

@Configuration
@PropertySource("classpath:reactive.properties")
@AutoConfigureAfter(CommonAutoConfiguration.class)
@ComponentScan(basePackageClasses = {
        ReactiveErrorController.class,
        VersionController.class,
        ErrorAttribute.class,
        ErrorWebExceptionHandler.class
})
public class ReactiveAutoConfiguration {

}
