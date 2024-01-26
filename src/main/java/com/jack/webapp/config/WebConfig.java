package com.jack.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class WebConfig implements WebMvcConfigurer {
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
    // Allow localhost:3000 to access the resources
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
    // Allow all methods (GET, POST, PUT, DELETE, OPTIONS, HEAD)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    // Allow the following headers
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    // Allow credentials
        configuration.setAllowCredentials(true);
    // Expose headers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // Allow all paths
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
