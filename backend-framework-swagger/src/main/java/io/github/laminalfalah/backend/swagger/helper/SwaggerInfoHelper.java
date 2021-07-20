package io.github.laminalfalah.backend.swagger.helper;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.swagger.helper
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

import io.github.laminalfalah.backend.swagger.properties.SwaggerProperties;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * @author laminalfalah on 10/07/21
 */

public class SwaggerInfoHelper {

    private final SwaggerProperties properties;

    public SwaggerInfoHelper(SwaggerProperties properties) {
        this.properties = properties;
    }

    public Info info(String groupName) {
        var info = new Info();
        info.setTitle(groupName + " " + properties.getTitle());
        info.setDescription(properties.getDescription());
        info.setTermsOfService(properties.getTermsOfService());
        info.setVersion(properties.getVersion());
        info.setContact(contact());
        info.setLicense(license());
        return info;
    }

    private Contact contact() {
        var contact = new Contact();
        contact.setName(properties.getContact().getName());
        contact.setEmail(properties.getContact().getEmail());
        contact.setUrl(properties.getContact().getUrl());
        return contact;
    }

    private License license() {
        var license = new License();
        license.setName(properties.getLicense().getName());
        license.setUrl(properties.getLicense().getUrl());
        return license;
    }

}
