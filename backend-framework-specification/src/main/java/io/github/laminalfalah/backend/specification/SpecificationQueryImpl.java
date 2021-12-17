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

import java.util.Iterator;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author laminalfalah on 19/10/21
 */

public class SpecificationQueryImpl implements SpecificationQuery {

    @Override
    public <T> Predicate lessThan(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate lessThanOrEquals(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate greaterThan(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate greaterThanOrEquals(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate equals(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate equalsLower(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate equalsUpper(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate like(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate likeLower(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate likeUpper(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate notLike(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate notLikeLower(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate notLikeUpper(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate startsWith(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate startsWithLower(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate startsWithUpper(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate endsWith(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate endsWithLower(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate endsWithUpper(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate in(Root<?> root, CriteriaBuilder builder, String field, Iterator<T> values) {
        return null;
    }

    @Override
    public <T> Predicate notIn(Root<?> root, CriteriaBuilder builder, String field, Iterator<T> values) {
        return null;
    }

    @Override
    public <T> Predicate exists(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public <T> Predicate notExists(Root<?> root, CriteriaBuilder builder, String field, T value) {
        return null;
    }

    @Override
    public Predicate isNull(Root<?> root, CriteriaBuilder builder, String field) {
        return builder.isNull(root.get(field));
    }

    @Override
    public Predicate isNotNull(Root<?> root, CriteriaBuilder builder, String field) {
        return builder.isNotNull(root.get(field));
    }

    @Override
    public Predicate and(List<Predicate> predicates) {
        return null;
    }

    @Override
    public Predicate andStart() {
        return null;
    }

    @Override
    public Predicate andEnd() {
        return null;
    }

    @Override
    public Predicate or(List<Predicate> predicates) {
        return null;
    }

    @Override
    public Predicate orStart() {
        return null;
    }

    @Override
    public Predicate orEnd() {
        return null;
    }
}