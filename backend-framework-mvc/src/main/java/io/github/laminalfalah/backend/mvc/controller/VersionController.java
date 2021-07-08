package io.github.laminalfalah.backend.mvc.controller;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.mvc.controller
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

import io.github.laminalfalah.backend.version.properties.VersionProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laminalfalah on 07/07/21
 */

@RestController
@RequestMapping(path = "/version", produces = MediaType.TEXT_PLAIN_VALUE)
public class VersionController implements InitializingBean {

    private final VersionProperties properties;

    private String maven;

    public VersionController(VersionProperties properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() {
        maven = "maven.groupId=" + properties.getGroupId() + "\n" +
                "maven.artifactId=" + properties.getArtifactId() + "\n" +
                "maven.pom.version=" + properties.getVersion() + "\n" +
                "maven.build.time=" + properties.getBuildTime();
    }

    @GetMapping
    public String version() {
        return maven;
    }

}
