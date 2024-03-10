package vn.cloud.servletsecuritydemo;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class ApiKeyToken extends AbstractAuthenticationToken {

    private final String apiKey;
    public ApiKeyToken(String apiKey) {
        super(null);
        this.setAuthenticated(true);
        this.apiKey = apiKey;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.apiKey;
    }
}
