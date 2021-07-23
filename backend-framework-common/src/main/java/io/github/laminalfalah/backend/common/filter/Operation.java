package io.github.laminalfalah.backend.common.filter;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.filter
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

public enum Operation {
    LESS_THAN, LESS_THAN_OR_EQUALS, EQUALS, EQUALS_LOWER, GREATER_THAN, GREATER_THAN_OR_EQUALS,
    CONTAINS, CONTAINS_LOWER, START_WITH, START_WITH_LOWER, END_WITH, END_WITH_LOWER, IN, IN_NOT,
    NULLABLE, NOT_NULLABLE
}
