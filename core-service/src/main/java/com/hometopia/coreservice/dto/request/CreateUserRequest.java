package com.hometopia.coreservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        Address address
) {
   public record Address(
           @NotBlank
           String line,
           @NotNull
           Integer provinceId,
           @NotNull
           Integer districtId,
           @NotNull
           Integer wardId
   ) {}
}
