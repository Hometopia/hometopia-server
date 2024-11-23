package com.hometopia.coreservice.mapper;

import com.hometopia.coreservice.dto.request.CreateUserRequest;
import com.hometopia.coreservice.dto.response.UserResponse;
import com.hometopia.coreservice.entity.DistrictLan;
import com.hometopia.coreservice.entity.ProvinceLan;
import com.hometopia.coreservice.entity.User;
import com.hometopia.coreservice.entity.WardLan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class UserMapper {

    @Autowired
    protected AddressMapper addressMapper;

    @Mapping(source = "id", target = "id")
    @Mapping(target = "address", expression = "java(addressMapper.toAddress(request.address()))")
    public abstract User toUser(CreateUserRequest request, String id);

    @Mapping(source = "user.id", target = "id")
    @Mapping(target = "address", expression = "java(addressMapper.toAddressResponse(user.getAddress(), province, district, ward))")
    public abstract UserResponse toUserResponse(User user, ProvinceLan province, DistrictLan district, WardLan ward);
}
