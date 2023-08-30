package com.newsforyou.apigateway;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration apiCorsConfiguration = new CorsConfiguration();
        apiCorsConfiguration.setAllowCredentials(true);
        apiCorsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        apiCorsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        apiCorsConfiguration.setAllowedMethods(Collections.singletonList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", apiCorsConfiguration);
        return source;
    }
}
