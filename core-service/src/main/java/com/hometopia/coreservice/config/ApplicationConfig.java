package com.hometopia.coreservice.config;

import com.hometopia.commons.mapper.MapperConfig;
import com.hometopia.commons.persistence.AuditingConfig;
import com.hometopia.commons.persistence.QueryDslConfig;
import com.hometopia.commons.security.SecurityConfig;
import com.hometopia.coreservice.config.property.KeycloakProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@Import({SecurityConfig.class, AuditingConfig.class, MapperConfig.class, QueryDslConfig.class})
@Configuration
@EnableScheduling
@EnableFeignClients(basePackages = "com.hometopia.coreservice.client")
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class ApplicationConfig {

    private final KeycloakProperty keycloakProperty;

    @Bean
    public Keycloak keycloak() {
        log.info("Keycloak client configuration: KC_URI {}, KC_REALM_MASTER {}, KC_ADMIN_CLI_CLIENT {}, KC_ADMIN_USERNAME {}," +
                " KC_ADMIN_PASSWORD {}", keycloakProperty.url(), keycloakProperty.realm().master(), keycloakProperty.client().adminCli(),
                keycloakProperty.admin().username(), keycloakProperty.admin().password());
        return KeycloakBuilder.builder()
                .serverUrl(keycloakProperty.url())
                .realm(keycloakProperty.realm().master())
                .clientId(keycloakProperty.client().adminCli())
                .grantType(OAuth2Constants.PASSWORD)
                .username(keycloakProperty.admin().username())
                .password(keycloakProperty.admin().password())
                .build();
    }

    @Bean
    public RealmResource realmResource(Keycloak keycloak) {
        return keycloak.realm(keycloakProperty.realm().hometopia());
    }
}
