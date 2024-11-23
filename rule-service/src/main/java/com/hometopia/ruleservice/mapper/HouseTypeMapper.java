package com.hometopia.ruleservice.mapper;

import com.hometopia.commons.enumeration.HouseType;
import org.mapstruct.Mapper;

@Mapper
public interface HouseTypeMapper {

    default HouseType toHouseType(com.hometopia.proto.category.HouseType houseType) {
        return switch (houseType) {
            case HIGH_GRADE -> HouseType.HIGH_GRADE;
            case MID_HIGH_GRADE -> HouseType.MID_HIGH_GRADE;
            case MID_GRADE -> HouseType.MID_GRADE;
            case LOW_GRADE -> HouseType.LOW_GRADE;
            case UNRECOGNIZED -> throw new IllegalArgumentException("Unrecognized HouseType");
        };
    }
}
