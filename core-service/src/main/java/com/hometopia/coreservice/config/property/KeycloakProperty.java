package com.hometopia.coreservice.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("keycloak")
public record KeycloakProperty(
        String url,
        Realm realm,
        Client client,
        Admin admin
) {
    public record Realm(
            String master,
            String hometopia
    ) {}

    public record Client(
            String adminCli,
            String hometopia
    ) {}

    public record Admin(
            String username,
            String password
    ) {}
}
