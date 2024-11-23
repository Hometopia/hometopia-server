package com.hometopia.coreservice.dto.response;

public record UserResponse(
        String id,
        String username,
        String email,
        String firstName,
        String lastName,
        AddressResponse address
) {}
