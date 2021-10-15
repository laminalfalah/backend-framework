package io.github.laminalfalah.backend.metric;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.metric
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

import static org.assertj.core.api.Assertions.assertThat;

import io.github.laminalfalah.backend.metric.info.ActiveProfilesInfoContributor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author laminalfalah on 15/10/21
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ActiveProfilesInfoContributorTests {

    @Test
    void activeProfilesShouldBeSetWhenProfilesActivated() {
        ConfigurableEnvironment environment = new MockEnvironment();
        environment.setActiveProfiles("prod");
        environment.setDefaultProfiles("dev", "api-docs");

        ActiveProfilesInfoContributor contributor = new ActiveProfilesInfoContributor(environment);

        Info.Builder builder = new Info.Builder();
        contributor.contribute(builder);
        Info info = builder.build();

        assertThat(info.get("activeProfiles")).asList().contains("prod");
    }

    @Test
    void defaultProfilesShouldBeSetWhenNoProfilesActivated() {
        ConfigurableEnvironment environment = new MockEnvironment();
        environment.setDefaultProfiles("dev", "api-docs");

        ActiveProfilesInfoContributor contributor = new ActiveProfilesInfoContributor(environment);

        Info.Builder builder = new Info.Builder();
        contributor.contribute(builder);
        Info info = builder.build();

        assertThat(info.get("activeProfiles")).asList().contains("dev", "api-docs");
    }
}
