package com.hometopia.coreservice.entity.embedded;

import java.io.Serializable;

public record HyperLink(
        String id,
        String entity
) implements Serializable {}
