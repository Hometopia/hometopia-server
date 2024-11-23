package com.hometopia.coreservice.mapper;

import com.hometopia.commons.mapper.ReferenceMapper;
import com.hometopia.coreservice.dto.response.WardResponse;
import com.hometopia.coreservice.entity.Ward;
import com.hometopia.coreservice.entity.WardLan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ReferenceMapper.class)
public interface WardMapper {
    @Mapping(source = "id.wardId", target = "code")
    WardResponse toWardResponse(WardLan ward);

    Ward toWard(Integer wardId);
}
