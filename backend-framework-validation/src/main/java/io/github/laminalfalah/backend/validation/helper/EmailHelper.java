package io.github.laminalfalah.backend.validation.helper;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.validation.helper
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

import io.github.laminalfalah.backend.validation.properties.EmailPatternProperties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author laminalfalah on 06/07/21
 */

public final class EmailHelper {

    private EmailHelper() {
        throw new UnsupportedOperationException();
    }

    public static Matcher matcherEmail(EmailPatternProperties properties, String email) {
        var pattern = Pattern.compile(properties.getPattern());
        return pattern.matcher(email);
    }
}
