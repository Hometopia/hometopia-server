package com.hometopia.coreservice.mapper;

import com.hometopia.commons.mapper.ReferenceMapper;
import com.hometopia.coreservice.dto.response.ProvinceResponse;
import com.hometopia.coreservice.entity.Province;
import com.hometopia.coreservice.entity.ProvinceLan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ReferenceMapper.class)
public interface ProvinceMapper {
    @Mapping(target = "code", source = "id.provinceId")
    ProvinceResponse toProvinceResponse(ProvinceLan province);

    Province toProvince(Integer provinceId);

//    ProvinceResponse toProvinceResponse(ProvinceLan province);
}
