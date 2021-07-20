package io.github.laminalfalah.backend.mvc.service;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.mvc.service
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
import io.github.laminalfalah.backend.mvc.payload.ExampleFilter;
import io.github.laminalfalah.backend.mvc.payload.ExampleResponse;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author laminalfalah on 20/07/21
 */

@Service
@Validated
public class ExampleService {

    private static final List<ExampleResponse> EXAMPLE_RESPONSES = new ArrayList<>(50);

    private static Long i = 1L;

    static {
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            EXAMPLE_RESPONSES.add(
                    ExampleResponse.builder()
                            .id(i)
                            .name(String.valueOf(alphabet))
                            .status(true)
                            .age(i.intValue() * 2)
                            .build()
            );
            i++;
        }
    }

    public List<ExampleResponse> index(Filter<ExampleFilter> filter) {
        var responses = EXAMPLE_RESPONSES;

        if (filter.getParams().getName() != null) {
            responses = filter(responses, s -> s.getName().toLowerCase().contains(filter.getParams().getName().toLowerCase()));
        }

        if (filter.getParams().getAge() != null) {
            responses = filter(responses, s -> s.getAge().equals(filter.getParams().getAge()));
        }

        if (filter.getParams().getStatus() != null) {
            responses = filter(responses, a -> a.getStatus().equals(filter.getParams().getStatus()));
        }

        return responses;
    }

    static <T> List<T> filter(List<T> data, Predicate<T> predicate) {
        return data.stream().filter(predicate).collect(Collectors.toList());
    }

}
