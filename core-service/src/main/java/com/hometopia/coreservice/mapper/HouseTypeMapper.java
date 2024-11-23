package com.hometopia.coreservice.mapper;

import com.hometopia.commons.enumeration.HouseType;
import org.mapstruct.Mapper;

@Mapper
public interface HouseTypeMapper {

    default com.hometopia.proto.category.HouseType toProtoHouseType(HouseType houseType) {
        return switch (houseType) {
            case HIGH_GRADE -> com.hometopia.proto.category.HouseType.HIGH_GRADE;
            case MID_HIGH_GRADE -> com.hometopia.proto.category.HouseType.MID_HIGH_GRADE;
            case MID_GRADE -> com.hometopia.proto.category.HouseType.MID_GRADE;
            case LOW_GRADE -> com.hometopia.proto.category.HouseType.LOW_GRADE;
        };
    }
}
