package com.hometopia.coreservice.mapper;

import com.hometopia.coreservice.dto.request.CreateUserRequest;
import com.hometopia.coreservice.dto.response.AddressResponse;
import com.hometopia.coreservice.entity.Address;
import com.hometopia.coreservice.entity.DistrictLan;
import com.hometopia.coreservice.entity.ProvinceLan;
import com.hometopia.coreservice.entity.WardLan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ProvinceMapper.class, DistrictMapper.class, WardMapper.class})
public interface AddressMapper {
    @Mapping(source = "request.provinceId", target = "province")
    @Mapping(source = "request.districtId", target = "district")
    @Mapping(source = "request.wardId", target = "ward")
    Address toAddress(CreateUserRequest.Address request);

    @Mapping(source = "province", target = "province")
    @Mapping(source = "district", target = "district")
    @Mapping(source = "ward", target = "ward")
    AddressResponse toAddressResponse(Address address, ProvinceLan province, DistrictLan district, WardLan ward);
}
