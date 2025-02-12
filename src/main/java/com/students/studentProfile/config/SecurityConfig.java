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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final Logger logger =  LoggerFactory.getLogger(SecurityConfig.class);


    private final UserService userService;
    private final JwtTokenProvider tokenProvider;


    public SecurityConfig(@Lazy UserService userService, JwtTokenProvider tokenProvider) {
        logger.info("inside security config constructor");
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Create request matchers
        logger.info("inside security filter chain");
        AntPathRequestMatcher publicUrls = new AntPathRequestMatcher("/auth/**");
        RequestMatcher protectedUrls = new NegatedRequestMatcher(publicUrls);
        logger.info("request matchers created");

        // Create filters
        JwtAuthenticationFilter authenticationFilter =
                new JwtAuthenticationFilter("/auth/login", authenticationManager(http), tokenProvider);
        logger.info("auth filter created");

        JwtValidationFilter validationFilter =
                new JwtValidationFilter(protectedUrls, tokenProvider, userService);
        logger.info("validation filter created");

        validationFilter.setAuthenticationManager(authenticationManager(http));



        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/students/**").hasRole("TEACHER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        logger.info(" inside authentication manager");
        authBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }
}
