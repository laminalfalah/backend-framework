package io.github.laminalfalah.backend.common.version;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.version
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
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author laminalfalah on 22/07/21
 */

public abstract class Versioning implements InitializingBean {

    @Autowired
    private VersionProperties properties;

    private String maven;

    @Override
    public void afterPropertiesSet() {
        maven = "maven.groupId=" + properties.getGroupId() + "\n" +
                "maven.artifactId=" + properties.getArtifactId() + "\n" +
                "maven.pom.version=" + properties.getVersion() + "\n" +
                "maven.build.time=" + properties.getBuildTime();
    }

    protected String versionMaven() {
        return maven;
    }

}
