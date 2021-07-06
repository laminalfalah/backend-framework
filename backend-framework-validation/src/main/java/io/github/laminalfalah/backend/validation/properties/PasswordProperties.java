package io.github.laminalfalah.backend.validation.properties;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.validation.properties
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author laminalfalah on 06/07/21
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("backend.security.password.pattern")
public class PasswordProperties {

    private Integer min = 8;

    private Integer max = 100;

    private Integer uppercase = 1;

    private Integer lowercase = 1;

    private Integer digit = 1;

    private Integer special = 1;

    private List<String> blacklistWords = new ArrayList<>();

}
