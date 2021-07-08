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

import java.io.Serializable;

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

}
