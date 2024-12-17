package com.hometopia.coreservice.service.impl;

import com.hometopia.coreservice.config.property.KeycloakProperty;
import com.hometopia.coreservice.dto.request.CreateUserRequest;
import com.hometopia.coreservice.service.KeycloakService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    private final KeycloakProperty keycloak;
    private final RealmResource realmResource;

    @Override
    public String createUser(CreateUserRequest request) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(request.username());
        userRepresentation.setFirstName(request.firstName());
        userRepresentation.setLastName(request.lastName());
        userRepresentation.setEmail(request.email());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);

        try (Response response = realmResource.users().create(userRepresentation)) {
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new RuntimeException("Failed to create user: " + response.readEntity(String.class));
            }
            log.info("Create user response from Keycloak: status {}, body {}", response.getStatus(), response.readEntity(String.class));

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(request.password());
            credentialRepresentation.setTemporary(false);

            String id = CreatedResponseUtil.getCreatedId(response);
            UserResource userResource = realmResource.users().get(id);
            userResource.resetPassword(credentialRepresentation);

            return id;
        }
    }

    @Override
    public List<UserRepresentation> getListUsers() {
        try {
            return realmResource.users().list();
        } catch (Exception e) {
            log.error("Error when getting users from keycloak: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserRepresentation getUserById(String id) {
        try {
            return realmResource.users().get(id).toRepresentation();
        } catch (Exception e) {
            log.error("Error when getting user by id from keycloak: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RoleRepresentation> getListRealmRoles() {
        try {
            return realmResource.roles().list();
        } catch (Exception e) {
            log.error("Error when getting realm roles from keycloak: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RoleRepresentation> getListRealmRolesByUserId(String id) {
        try {
            return realmResource.users().get(id).roles().realmLevel().listEffective();
        } catch (Exception e) {
            log.error("Error when getting user's realm roles from keycloak: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RoleRepresentation> getListClientRolesByUserId(String id) {
        try {
            return realmResource.users().get(id).roles().clientLevel(realmResource.clients()
                    .findByClientId(keycloak.client().hometopia()).get(0).getId()).listEffective();
        } catch (Exception e) {
            log.error("Error when getting user's client roles from keycloak: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(String id) {
        try {
            realmResource.users().delete(id).close();
        } catch (Exception e) {
            log.error("Error when delete user by id: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isEmailExist(String email) {
        try {
            return !realmResource.users().searchByEmail(email, true).isEmpty();
        } catch (Exception e) {
            log.error("Error when delete user by id: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
