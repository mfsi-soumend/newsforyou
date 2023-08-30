package com.newsforyou.newsservice.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for the Spring Web MVC framework.
 * Enables Spring Data web support and sets up CORS mappings and resource handlers.
 * 
 * @param allowedOrigins A string containing the allowed origins for CORS requests.
 */
@Configuration
@EnableSpringDataWebSupport
public class AppWebMvcConfig implements WebMvcConfigurer{
	private final String allowedOrigins;

 /**
  * Constructs an instance of the AppWebMvcConfig class with the specified allowed origins.
  *
  * @param allowedOrigins The allowed origins for the application.
  */
	public AppWebMvcConfig(@Value("${allowed.origins}") String allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}


 /**
  * Configures cross-origin resource sharing (CORS) mappings for the application.
  *
  * @param registry The registry to add the CORS mappings to.
  * @return void
  */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedHeaders("*").allowedOrigins(allowedOrigins).allowedMethods("*");
	}


 /**
  * Adds resource handlers to the registry for Swagger UI and webjars.
  *
  * @param registry The registry to add the resource handlers to.
  */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
