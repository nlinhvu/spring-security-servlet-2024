package vn.cloud.servletsecuritydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@SpringBootApplication
public class ServletSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletSecurityDemoApplication.class, args);
	}

	@Bean
	@Order(SecurityProperties.BASIC_AUTH_ORDER - 1)
	SecurityFilterChain apiKeySecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatchers((matchers) -> matchers.requestMatchers("/api-key/**"));
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
		http.addFilterBefore(new ApiKeyFilter(), AuthorizationFilter.class);
		return http.build();
	}

	@Bean
	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	}
}
