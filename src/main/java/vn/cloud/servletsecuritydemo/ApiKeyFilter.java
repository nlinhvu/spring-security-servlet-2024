package vn.cloud.servletsecuritydemo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class ApiKeyFilter extends OncePerRequestFilter {

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    private final String apiKey;

    public ApiKeyFilter() {
        this.apiKey = Base64.getEncoder().encodeToString("password".getBytes(StandardCharsets.UTF_8));
        log.info("ApiKey: " + this.apiKey);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader("x-api-key");
        if (!this.apiKey.equals(apiKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        ApiKeyToken apiKeyToken = new ApiKeyToken(apiKey);
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(apiKeyToken);
        this.securityContextHolderStrategy.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", apiKeyToken));
        }
        filterChain.doFilter(request, response);
    }
}

