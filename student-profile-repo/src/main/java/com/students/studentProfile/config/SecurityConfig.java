package com.students.studentProfile.config;

import com.students.studentProfile.filters.JwtAuthenticationFilter;
import com.students.studentProfile.filters.JwtValidationFilter;
import com.students.studentProfile.service.UserService;
import com.students.studentProfile.utils.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    public SecurityConfig(@Lazy UserService userService, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Inside security filter chain");

        // Public URLs (Swagger + Auth)
        RequestMatcher publicUrls = request -> {
            String path = request.getServletPath();
            return path.startsWith("/auth/") ||
                    path.startsWith("/swagger-ui") ||
                    path.startsWith("/swagger-ui.html") ||
                    path.startsWith("/v3/api-docs") ||
                    path.startsWith("/api-docs");
        };
        RequestMatcher protectedUrls = new NegatedRequestMatcher(publicUrls);

        // JWT Authentication Filters
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter("/auth/login", authenticationManager(http), tokenProvider);
        JwtValidationFilter validationFilter = new JwtValidationFilter(protectedUrls, tokenProvider, userService);
        validationFilter.setAuthenticationManager(authenticationManager(http));

        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api-docs/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validationFilter, UsernamePasswordAuthenticationFilter.class)

                // Fix: CORS policy for frontend connection
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:5173")); // Ensure correct frontend origin
                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true); // Fix for Swagger UI
                    return config;
                }));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }
}
