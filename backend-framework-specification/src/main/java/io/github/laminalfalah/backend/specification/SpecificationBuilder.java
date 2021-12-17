package io.github.laminalfalah.backend.specification;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.specification
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

import io.github.laminalfalah.backend.common.annotation.FilterColumn;
import io.github.laminalfalah.backend.common.exception.SpecificationException;
import io.github.laminalfalah.backend.common.filter.FilterField;
import io.github.laminalfalah.backend.common.filter.Operation;
import io.github.laminalfalah.backend.common.helper.PagingHelper;
import io.github.laminalfalah.backend.common.payload.request.Filter;
import io.github.laminalfalah.backend.common.payload.response.Paging;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author laminalfalah on 18/10/21
 */

@Slf4j
public class SpecificationBuilder<E> {

    @Getter
    private final JpaSpecificationExecutor<E> repository;

    @Getter
    private final Filter<?> filter;

    @Getter
    private final Pageable pageable;

    @Setter
    private String includeField;

    private final SpecificationQuery specificationQuery;

    public SpecificationBuilder(JpaSpecificationExecutor<E> repository, Filter<?> filter) {
        this.repository = repository;
        this.filter = filter;
        this.pageable = PagingHelper.pageableOf(filter);
        this.specificationQuery = new SpecificationQueryImpl();
    }

    public Page<E> build() {
        if (repository == null || filter == null) {
            throw new SpecificationException("Repository or filter is null !");
        }

        return repository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            validateIncludeField(predicates, root, builder);

            for (var field: filter.getParams().getClass().getDeclaredFields()) {
                var filterColumn = field.getAnnotation(FilterColumn.class);

                var filterField = FilterField.builder().field(field).filterColumn(filterColumn).build();

                var operation = filterField.getOperation();
                var fieldName = filterField.getFieldNameSql();

                try {
                    if (field.get(fieldName) != null) {
                        if (operation.equals(Operation.LESS_THAN)) {
                            predicates.add(
                                builder.and(
                                    builder.lessThan(root.get(fieldName), filterField.getValue(filter.getParams()).toString())
                                )
                            );
                        } else if (operation.equals(Operation.LESS_THAN_OR_EQUALS)) {
                            predicates.add(
                                builder.and(
                                    builder.lessThanOrEqualTo(root.get(fieldName), filterField.getValue(filter.getParams()).toString())
                                )
                            );
                        } else if (operation.equals(Operation.EQUALS)) {
                            predicates.add(
                                builder.and(
                                    builder.equal(root.get(fieldName), filterField.getValue(filter.getParams()))
                                )
                            );
                        } else if (operation.equals(Operation.EQUALS_LOWER)) {
                            predicates.add(
                                builder.and(

                                )
                            );
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size() == 0 ? 0 : predicates.size()]));
        }, getPageable());
    }

    private void validateIncludeField(List<Predicate> predicates, Root<?> root, CriteriaBuilder builder) {
        if (includeField != null) {
            predicates.add(specificationQuery.isNotNull(root, builder, includeField));
        }
    }

    public <T> List<T> map(Page<E> page, Function<E, T> mapper) {
        return page.getContent().stream().map(mapper).collect(Collectors.toList());
    }

    public <T> Paging<T> toPaging(Filter<T> filter, Page<E> entity) {
        return PagingHelper.toPaging(filter, entity);
    }

}
