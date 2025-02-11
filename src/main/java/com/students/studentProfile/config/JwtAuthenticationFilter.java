package com.students.studentProfile.config;

import com.students.studentProfile.model.User;
import com.students.studentProfile.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UserService userService) {
        super("/students");
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }


    @Override
    public org.springframework.security.core.Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            User user = validateTokenWithUserInfoEndpoint(token);
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            } else {
                throw new AuthenticationServiceException("Invalid token");
            }
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);  // Continue with the next filter in the chain
    }

}
