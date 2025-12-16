package com.clickfud.clickfud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // ❌ Disable CSRF (React API use case)
                .csrf(csrf -> csrf.disable())

                // ✅ Enable CORS
                .cors(Customizer.withDefaults())


                // ✅ Authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Public test endpoints
                        .requestMatchers("/api/test/**").permitAll()

                        // Admin APIs (later Firebase auth protect)
                        .requestMatchers("/api/admin/**").permitAll()

                        // Read-only APIs
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()

                        // Everything else
                        .anyRequest().authenticated()
                )

                // ❌ No form login
                .formLogin(form -> form.disable())

                // ❌ No basic auth popup
                .httpBasic(basic -> basic.disable());

        return http.build();
    }


}
