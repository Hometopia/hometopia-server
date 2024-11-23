package com.hometopia.coreservice.service;

import com.hometopia.coreservice.dto.request.CreateUserRequest;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloakService {
    String createUser(CreateUserRequest request);
    List<UserRepresentation> getListUsers();
    UserRepresentation getUserById(String id);
    List<RoleRepresentation> getListRealmRoles();
    List<RoleRepresentation> getListRealmRolesByUserId(String id);
    List<RoleRepresentation> getListClientRolesByUserId(String id);
    void deleteUser(String id);
}
