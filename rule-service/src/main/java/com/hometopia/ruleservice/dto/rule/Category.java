package com.hometopia.ruleservice.dto.rule;

import java.util.List;

public record Category(
        String name,
        List<Category> subCategories
) {}
