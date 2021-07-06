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

import io.github.laminalfalah.backend.validation.properties.PasswordProperties;
import org.passay.*;

import java.util.List;

/**
 * @author laminalfalah on 06/07/21
 */

public final class PasswordHelper {

    private PasswordHelper() {
        throw new UnsupportedOperationException();
    }

    public static PasswordValidator getPasswordValidator(List<Rule> rules) {
        return new PasswordValidator(rules);
    }

    public static PasswordValidator getPasswordValidator(PasswordProperties properties) {
        return new PasswordValidator(rules(properties));
    }

    public static RuleResult getRuleResult(PasswordValidator validator, String value) {
        return validator.validate(new PasswordData(value));
    }

    public static List<String> getMessages(PasswordValidator validator, RuleResult result) {
        return validator.getMessages(result);
    }

    private static List<Rule> rules(PasswordProperties properties) {
        return List.of(
                new LengthRule(properties.getMin(), properties.getMax()),
                new CharacterRule(EnglishCharacterData.UpperCase, properties.getUppercase()),
                new CharacterRule(EnglishCharacterData.LowerCase, properties.getLowercase()),
                new CharacterRule(EnglishCharacterData.Special, properties.getSpecial()),
                new CharacterRule(EnglishCharacterData.Digit, properties.getDigit()),
                new WhitespaceRule(),
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5 ,false),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
                new IllegalSequenceRule(EnglishSequenceData.USQwerty)
        );
    }

}
