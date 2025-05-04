package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration for the WebSocket chat application.
 *
 * <p>This configuration:
 * <ul>
 *   <li>Enables HTTP Basic authentication for all endpoints</li>
 *   <li>Disables CSRF protection (required for WebSocket support)</li>
 *   <li>Provides an in-memory user store with two users: "alice" and "bob"</li>
 * </ul>
 *
 * <p>Used to secure both REST and WebSocket endpoints with simple, form-compatible authentication.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username("alice")
                        .password("password")
                        .build()
                ,
                User.withDefaultPasswordEncoder()
                        .username("bob")
                        .password("password")
                        .build()
        );
    }
}
