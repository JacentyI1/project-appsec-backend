//package com.jack.webapp.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig extends WebSecurityConfig{
//
//    public WebConfig(AuthenticationProvider authenticationProvider, JwtAuthFilter jwtAuthenticationFilter, LogoutHandler logoutHandler) {
//        super(authenticationProvider, jwtAuthenticationFilter, logoutHandler);
//    }
//
//    @Override
//        public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/**") // Apply to all endpoints in the application
//                    .allowedOrigins("http://localhost:3000") // Allow this origin to make requests
//                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these HTTP methods
//                    .allowedHeaders("*") // Allow all headers
//                    .allowCredentials(true); // Allow credentials like cookies, authorization headers, etc.
//        }
//}
