package com.heliant.my_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/auth").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/api/documents/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/api/documents/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/documents/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/api/documents/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers("/api/documentFields/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers("/api/submittedDocuments/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers("/api/submittedDocumentFields/**").hasAnyRole("EMPLOYEE", "ADMIN");
                    auth.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

}
