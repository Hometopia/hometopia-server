package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.exception.ResourceNotFoundException;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.request.CreateUserRequest;
import com.hometopia.coreservice.dto.response.UserResponse;
import com.hometopia.coreservice.entity.User;
import com.hometopia.coreservice.entity.enumeration.CountryCode;
import com.hometopia.coreservice.mapper.UserMapper;
import com.hometopia.coreservice.repository.DistrictLanRepository;
import com.hometopia.coreservice.repository.ProvinceLanRepository;
import com.hometopia.coreservice.repository.UserRepository;
import com.hometopia.coreservice.repository.WardLanRepository;
import com.hometopia.coreservice.service.KeycloakService;
import com.hometopia.coreservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ProvinceLanRepository provinceLanRepository;
    private final DistrictLanRepository districtLanRepository;
    private final WardLanRepository wardLanRepository;
    private final KeycloakService keycloakService;

    @Override
    @Transactional
    public RestResponse<UserResponse> createUser(CreateUserRequest request) {
        String id = keycloakService.createUser(request);
        try {
            User user = userRepository.saveAndFlush(userMapper.toUser(request, id));

            return RestResponse.created(userMapper.toUserResponse(user,
                    provinceLanRepository
                            .findOneByIdProvinceIdAndIdCountryCode(user.getAddress().getProvince().getCode(), CountryCode.VN)
                            .orElseThrow(() -> new ResourceNotFoundException("Province", "code", user.getAddress().getProvince().getCode())),
                    districtLanRepository
                            .findOneByIdDistrictIdAndIdCountryCode(user.getAddress().getDistrict().getCode(), CountryCode.VN)
                            .orElseThrow(() -> new ResourceNotFoundException("District", "code", user.getAddress().getDistrict().getCode())),
                    wardLanRepository
                            .findOneByIdWardIdAndIdCountryCode(user.getAddress().getWard().getCode(), CountryCode.VN)
                            .orElseThrow(() -> new ResourceNotFoundException("Ward", "code", user.getAddress().getWard().getCode())))
            );
        } catch (Exception e) {
            keycloakService.deleteUser(id);
            throw new RuntimeException(e);
        }
    }

    @Override
    public RestResponse<UserResponse> getUser(String id) {

        return userRepository.findById(id)
                .map(user -> userMapper.toUserResponse(user,
                        provinceLanRepository
                                .findOneByIdProvinceIdAndIdCountryCode(user.getAddress().getProvince().getCode(), CountryCode.VN)
                                .orElseThrow(() -> new ResourceNotFoundException("Province", "code", user.getAddress().getProvince().getCode())),
                        districtLanRepository
                                .findOneByIdDistrictIdAndIdCountryCode(user.getAddress().getDistrict().getCode(), CountryCode.VN)
                                .orElseThrow(() -> new ResourceNotFoundException("District", "code", user.getAddress().getDistrict().getCode())),
                        wardLanRepository
                                .findOneByIdWardIdAndIdCountryCode(user.getAddress().getWard().getCode(), CountryCode.VN)
                                .orElseThrow(() -> new ResourceNotFoundException("Ward", "code", user.getAddress().getWard().getCode()))))
                .map(RestResponse::ok)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public void isEmailExist(String email) {
        if(keycloakService.isEmailExist(email)) {
            throw new RuntimeException("Email already exists");
        }
    }
}
