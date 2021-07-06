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

import org.springframework.beans.BeanWrapperImpl;

import java.util.regex.Pattern;

/**
 * @author laminalfalah on 06/07/21
 */

public final class UtilsHelper {

    private UtilsHelper() {
        throw new UnsupportedOperationException();
    }

    public static boolean isValidIpAddress(String value) {
        var pattern = Pattern.compile("^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$");
        var matcher = pattern.matcher(value);

        if (!matcher.matches()) {
            return false;
        } else {
            for (var i = 1; i <= 4; i++) {
                var octet = Integer.parseInt(matcher.group(i));
                if (octet > 255) {
                    return false;
                }
            }
            return true;
        }
    }

    public static Object getObjectField(Object o, String field) {
        return new BeanWrapperImpl(o).getPropertyValue(field);
    }

}
