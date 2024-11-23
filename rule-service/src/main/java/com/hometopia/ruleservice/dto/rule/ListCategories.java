package com.hometopia.ruleservice.dto.rule;

import com.hometopia.commons.enumeration.HouseType;
import lombok.Data;

@Data
public class ListCategories {
    private HouseType houseType;
    private String categories;

    public ListCategories(HouseType houseType) {
        this.houseType = houseType;
    }
}
