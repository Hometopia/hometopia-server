package com.hometopia.commons.request;

public record Predicate<T>(
        Operator operator,
        T value,
        SortDirection sort
) {}
