package io.github.laminalfalah.backend.common.helper;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.common.helper
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
import io.github.laminalfalah.backend.common.payload.response.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author laminalfalah on 06/07/21
 */

public final class PagingHelper {

    private PagingHelper() {
        throw new UnsupportedOperationException();
    }

    public static <T extends Serializable> Paging<T> toPaging(Filter<T> filter, Long totalSize) {
        Long totalPages = totalSize / filter.getSize();

        if (totalSize % filter.getSize() != 0) {
            totalPages++;
        }

        return toPaging(filter, totalPages, totalSize);
    }

    public static <T extends Serializable> Paging<T> toPaging(Filter<T> filter, Long totalPages, Long totalSize) {
        return Paging.<T>builder()
                .page(filter.getPage())
                .size(filter.getSize())
                .sorts(filter.getSorts())
                .params(filter.getParams())
                .totalItems(totalSize)
                .totalPages(totalPages)
                .build();
    }

    public static <T extends Serializable> Pageable pageableOf(Filter<T> filter) {
        List<Sort.Order> orders = new ArrayList<>();

        filter.getSorts().forEach(v ->
                orders.add(new Sort.Order(
                        v.getDirection().name().equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC,
                        v.getColumn()
                ))
        );

        return PageRequest.of(filter.getPage().intValue() - 1, filter.getSize().intValue(), Sort.by(orders));
    }

    public static <T extends Serializable> Paging<T> toPaging(Filter<T> filter, Page<?> page) {
        return Paging.<T>builder()
                .page((long) page.getNumber() + 1)
                .size((long) page.getSize())
                .totalPages((long) page.getTotalPages())
                .totalItems(page.getTotalElements())
                .sorts(filter.getSorts())
                .params(filter.getParams())
                .build();
    }

}
