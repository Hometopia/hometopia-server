package com.hometopia.commons.utils;

import com.hometopia.commons.security.UserDetailsImpl;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getCurrentUserId() {
        Authentication authentication = getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationCredentialsNotFoundException("Unable to retrieve user ID due to anonymous access");
        } else {
            try {
                return ((UserDetailsImpl) authentication.getPrincipal()).getId();
            } catch (Exception e) {
                throw new AuthenticationCredentialsNotFoundException(
                        "Unable to retrieve user ID while parsing text: %s".formatted(authentication.getName()));
            }
        }
    }
}
