package io.github.laminalfalah.backend.metric.info;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.metric.info
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

import java.util.List;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author laminalfalah on 15/10/21
 */

public class ActiveProfilesInfoContributor implements InfoContributor {

    private static final String ACTIVE_PROFILES = "activeProfiles";
    private final List<String> profiles;

    public ActiveProfilesInfoContributor(ConfigurableEnvironment environment) {
        this.profiles = List.of(environment.getActiveProfiles().length == 0
            ? environment.getDefaultProfiles()
            : environment.getActiveProfiles()
        );
    }

    @Override
    public void contribute(Builder builder) {
        builder.withDetail(ACTIVE_PROFILES, profiles);
    }
}
