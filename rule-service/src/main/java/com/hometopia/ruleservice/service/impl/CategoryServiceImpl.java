package com.hometopia.ruleservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hometopia.ruleservice.dto.rule.Category;
import com.hometopia.ruleservice.dto.rule.ListCategories;
import com.hometopia.ruleservice.mapper.CategoryMapper;
import com.hometopia.ruleservice.mapper.HouseTypeMapper;
import com.hometopia.ruleservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ObjectMapper objectMapper;
    private final KieContainer categoryRuleContainer;
    private final HouseTypeMapper houseTypeMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public com.hometopia.proto.category.GetListCategoryResponse getListCategories(com.hometopia.proto.category.GetListCategoryRequest request) {
        try {
            ListCategories categories = new ListCategories(houseTypeMapper.toHouseType(request.getHouseType()));
            KieSession kieSession = categoryRuleContainer.newKieSession();
            kieSession.insert(categories);
            kieSession.fireAllRules();
            kieSession.dispose();
            List<Category> categoryList = objectMapper.readValue(categories.getCategories(), new TypeReference<>() {});
            return com.hometopia.proto.category.GetListCategoryResponse.newBuilder()
                    .addAllCategories(categoryList.stream().map(categoryMapper::toCategoryResponse).toList())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
