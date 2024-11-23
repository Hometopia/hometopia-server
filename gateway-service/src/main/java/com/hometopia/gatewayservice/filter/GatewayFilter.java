package com.hometopia.gatewayservice.filter;

import com.hometopia.gatewayservice.utils.CustomHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GatewayFilter implements GlobalFilter {

    private final JwtDecoder jwtDecoder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token == null || token.startsWith("Basic")) {
            return chain.filter(exchange);
        }

        Jwt jwt = jwtDecoder.decode(token.substring(7));
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        Set<String> roles = ((List<String>) realmAccess.get("roles")).stream()
                .filter(role -> role.startsWith("ROLE_")).collect(Collectors.toSet());

        return chain.filter(
                exchange.mutate().request(
                                exchange.getRequest().mutate()
                                        .header(CustomHeaders.X_AUTH_USER_ID, jwt.getSubject())
                                        .header(CustomHeaders.X_AUTH_USER_USERNAME, (String) jwt.getClaim("preferred_username"))
                                        .header(CustomHeaders.X_AUTH_USER_AUTHORITIES, roles.toString())
                                        .build())
                        .build());
    }
}
