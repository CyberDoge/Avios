package io.ssau.team.Avios.config;

import io.ssau.team.Avios.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class AuthenticationToken extends AbstractAuthenticationToken {
    private final String token;
    private final User user;

    AuthenticationToken(String token, User user) {
        super(Collections.singleton(new SimpleGrantedAuthority("USER")));

        this.token = token;
        this.user = user;

        setAuthenticated(true);
    }

    AuthenticationToken(String token) {
        super(null);

        this.token = token;
        this.user = null;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return getToken();
    }

    @Override
    public Object getPrincipal() {
        return getUser();
    }

    String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}