package com.hometopia.commons.utils;

import com.hometopia.commons.request.Operator;
import com.hometopia.commons.request.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T, D> {

    public static <D> Sort toSort(@NonNull D criteria) throws IllegalAccessException {
        Sort sort = null;
        Class<?> clazz = criteria.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Predicate<?> predicate = (Predicate<?>) field.get(criteria);
            Sort sortByField = switch (predicate.sort()) {
                case ASC -> Sort.by(field.getName()).ascending();
                case DESC -> Sort.by(field.getName()).descending();
            };
            sort = sort == null ? sortByField : sort.and(sortByField);
        }

        return sort == null ? Sort.unsorted() : sort;
    }

    public Specification<T> toSpecification(@NonNull D criteria) throws IllegalAccessException {
        Class<?> clazz = criteria.getClass();
        List<Specification<T>> specs = new ArrayList<>(clazz.getDeclaredFields().length);

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Predicate<?> predicate = (Predicate<?>) field.get(criteria);
            specs.add((Specification<T>) (root, query, cb) -> {
                if (predicate.operator() == Operator.EQUAL) {
                    return cb.equal(root.get(field.getName()), predicate.value());
                } else if (predicate.operator() == Operator.NOT_EQUAL) {
                    return cb.notEqual(root.get(field.getName()), predicate.value());
                } else if (predicate.operator() == Operator.IN) {
                    return root.get(field.getName()).in(predicate.value());
                } else if (predicate.operator() == Operator.GREATER_THAN) {
                    if (predicate.value().getClass().isAssignableFrom(String.class)) {
                        return cb.greaterThan(root.get(field.getName()), (String) predicate.value());
                    } else if (predicate.value().getClass().isAssignableFrom(Integer.class)) {
                        return cb.greaterThanOrEqualTo(root.get(field.getName()), (Integer) predicate.value());
                    } else {
                        throw new RuntimeException("Unsupported value: " + predicate.value() + ", type: " + predicate.value().getClass());
                    }
                }
                throw new RuntimeException("Unsupported operator: " + predicate.operator());
            });
        }

        return Specification.allOf(specs);
    }
}
