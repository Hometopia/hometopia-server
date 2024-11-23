package com.hometopia.gatewayservice.filter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtClaimsConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;

    public JwtClaimsConverter() {
        this.jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    }

    @Override
    public Mono<AbstractAuthenticationToken> convert(@NonNull Jwt jwt) {
        Set<GrantedAuthority> authorities = Stream.concat(
                // get scopes
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                // get roles
                extractUserRoles(jwt).stream()
        ).collect(Collectors.toSet());
        return Mono.just(new JwtAuthenticationToken(jwt, authorities));
    }

    private Set<? extends GrantedAuthority> extractUserRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        List<String> realmRoles = (List<String>) realmAccess.get("roles");
        return realmRoles.stream()
                .filter(role -> role.startsWith("ROLE"))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
