package vn.cloud.servletsecuritydemo;

import jakarta.servlet.DispatcherType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ServletSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletSecurityDemoApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<MyApiKeyFilter> myApiKeyFilter() {
		MyApiKeyFilter myApiKeyFilter = new MyApiKeyFilter();
		FilterRegistrationBean<MyApiKeyFilter> filterBean = new FilterRegistrationBean<>(myApiKeyFilter);
		filterBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR);
		filterBean.setUrlPatterns(List.of("/api/*"));
		filterBean.setOrder(-104);
		return filterBean;
	}
}
