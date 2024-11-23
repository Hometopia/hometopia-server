package com.hometopia.coreservice.service;

import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.request.CreateUserRequest;
import com.hometopia.coreservice.dto.response.UserResponse;

public interface UserService {
    RestResponse<UserResponse> createUser(CreateUserRequest request);
    RestResponse<UserResponse> getUser(String id);
}
