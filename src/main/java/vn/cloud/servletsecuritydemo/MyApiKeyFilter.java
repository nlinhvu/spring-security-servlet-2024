package vn.cloud.servletsecuritydemo;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

//@Component
//@Order(-104)
@Slf4j
public class MyApiKeyFilter implements Filter {

    private final String apiKey;

    public MyApiKeyFilter() {
        this.apiKey = Base64.getEncoder().encodeToString("password".getBytes(StandardCharsets.UTF_8));
        log.info("ApiKey: " + this.apiKey);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String apiKey = ((HttpServletRequest) servletRequest).getHeader("x-api-key");
        if (!this.apiKey.equals(apiKey)) {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "API key required!");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
