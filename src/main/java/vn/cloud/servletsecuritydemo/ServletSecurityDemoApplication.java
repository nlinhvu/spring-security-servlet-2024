package vn.cloud.servletsecuritydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@SpringBootApplication
public class ServletSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletSecurityDemoApplication.class, args);
	}

	@Bean
	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http
				.sessionManagement(withDefaults())
				.securityContext(withDefaults())
				.headers(withDefaults())
				.cors(withDefaults())
				.csrf(withDefaults())
				.logout(withDefaults())
				.requestCache(withDefaults())
				.servletApi(withDefaults())
				.anonymous(withDefaults())
				.exceptionHandling(withDefaults())
				.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
//				.formLogin(withDefaults())
				.httpBasic(withDefaults())
				.addFilterBefore(new ApiKeyFilter(), UsernamePasswordAuthenticationFilter.class)
				.build();
	}
}
