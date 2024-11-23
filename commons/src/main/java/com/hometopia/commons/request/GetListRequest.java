package com.hometopia.commons.request;

public record GetListRequest<T>(
        boolean all,
        Integer page,
        Integer size,
        T criteria
) {}
