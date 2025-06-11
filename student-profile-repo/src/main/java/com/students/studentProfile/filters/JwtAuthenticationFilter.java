package com.students.studentProfile.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.students.studentProfile.model.User;
import com.students.studentProfile.utils.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import java.io.IOException;
import java.util.Collections;


public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private final JwtTokenProvider tokenProvider;
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(String url, AuthenticationManager authManager, JwtTokenProvider tokenProvider) {
        super(new AntPathRequestMatcher(url));
        logger.info("inside constructor of auth filter");
        setAuthenticationManager(authManager);
        this.tokenProvider = tokenProvider;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        logger.info("inside attempt authentication");
        User creds = new ObjectMapper()
                .readValue(request.getInputStream(), User.class);

        logger.info("credentials using ObjectMapper: {}",creds);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        Collections.emptyList()
                )
        );
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        logger.info("User details after successful authentication: {}",userDetails);
        String token = tokenProvider.createToken(userDetails.getUsername(),
                userDetails.getAuthorities());


        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().write(token);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {


        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + failed.getMessage() + "\"}");
    }
}