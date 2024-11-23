package com.hometopia.coreservice.mapper;

import com.hometopia.commons.mapper.ReferenceMapper;
import com.hometopia.coreservice.dto.response.DistrictResponse;
import com.hometopia.coreservice.entity.District;
import com.hometopia.coreservice.entity.DistrictLan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ReferenceMapper.class)
public interface DistrictMapper {
    @Mapping(source = "id.districtId", target = "code")
    DistrictResponse toDistrictResponse(DistrictLan district);

    District toDistrict(Integer districtId);
}
