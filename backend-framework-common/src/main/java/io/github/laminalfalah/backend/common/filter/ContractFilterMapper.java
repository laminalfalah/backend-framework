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

import io.github.laminalfalah.backend.common.payload.request.Filter;
import org.slf4j.Logger;
import org.springframework.core.MethodParameter;

/**
 * @author laminalfalah on 15/10/21
 */

public interface ContractFilterMapper<T> {

    Filter<Object> fromServerHttpRequest(MethodParameter parameter, Logger logger, T exchange);

}
