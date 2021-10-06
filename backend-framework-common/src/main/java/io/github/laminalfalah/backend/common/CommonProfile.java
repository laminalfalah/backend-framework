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

/**
 * @author laminalfalah on 22/07/21
 */

public final class CommonProfile {
    public static final String DEVELOPMENT = "dev";
    public static final String PRODUCTION = "prod";
    public static final String TESTING = "testing";
    public static final String STAGING = "staging";
    public static final String SWAGGER = "swagger";
    public static final String ACTUATOR = "actuator";

    private CommonProfile() {
        throw new UnsupportedOperationException();
    }

}
