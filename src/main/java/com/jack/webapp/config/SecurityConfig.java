package com.jack.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Override
    public void configure(HttpSecurity builder) throws Exception {
        builder
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/register", "/login").permitAll()
                                .requestMatchers("/api/public/**").permitAll()
                                .requestMatchers("/api/**").authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .logout(logout-> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );
    }

    /**Password-hashing function designed by
     * Niels Provos and David Mazières, based on the Blowfish cipher.
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
