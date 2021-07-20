package io.github.laminalfalah.backend.swagger.properties;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.swagger.properties
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author laminalfalah on 09/07/21
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("backend.swagger")
public class SwaggerProperties {

    private String title;

    private String description;

    private String termsOfService;

    private String version;

    private Contact contact = new Contact();

    private License license = new License();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Contact {

        private String name;

        private String email;

        private String url;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class License {

        private String name = "Apache License, Version 2.0";

        private String url = "https://www.apache.org/licenses/LICENSE-2.0.txt";
    }

}
