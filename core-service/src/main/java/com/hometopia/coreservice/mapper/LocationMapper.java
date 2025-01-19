package com.hometopia.coreservice.mapper;

import com.hometopia.commons.mapper.ReferenceMapper;
import com.hometopia.commons.utils.SecurityUtils;
import com.hometopia.coreservice.dto.request.CreateLocationRequest;
import com.hometopia.coreservice.dto.request.UpdateLocationRequest;
import com.hometopia.coreservice.dto.response.CreateLocationResponse;
import com.hometopia.coreservice.dto.response.GetListLocationResponse;
import com.hometopia.coreservice.dto.response.GetOneLocationResponse;
import com.hometopia.coreservice.dto.response.UpdateLocationResponse;
import com.hometopia.coreservice.entity.Location;
import com.hometopia.coreservice.repository.UserRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(uses = ReferenceMapper.class)
public abstract class LocationMapper {

    @Autowired
    protected UserRepository userRepository;

    public abstract Location toLocation(String locationId);

    public abstract Location toLocation(CreateLocationRequest request);

    public abstract CreateLocationResponse toCreateLocationResponse(Location location);

    public abstract Location updateLocation(@MappingTarget Location location, UpdateLocationRequest request);

    public abstract UpdateLocationResponse toUpdateLocationResponse(Location location);

    public abstract GetOneLocationResponse toGetOneLocationResponse(Location location);

    public abstract GetListLocationResponse toGetListLocationResponse(Location location);

    @AfterMapping
    public void attachUser(@MappingTarget Location location, CreateLocationRequest request) {
        location.setUser(userRepository.getReferenceById(SecurityUtils.getCurrentUserId()));
    }
}
