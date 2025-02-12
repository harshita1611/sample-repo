package com.students.studentProfile.filters;


import com.students.studentProfile.service.UserService;
import com.students.studentProfile.utils.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;


import java.io.IOException;


public class JwtValidationFilter extends AbstractAuthenticationProcessingFilter {
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(JwtValidationFilter.class);

    public JwtValidationFilter(RequestMatcher requestMatcher, JwtTokenProvider tokenProvider, UserService userService) {
        super(requestMatcher);
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String jwt = getJwtFromRequest(request);


        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            String username = tokenProvider.getUsernameFromToken(jwt);
            UserDetails userDetails = userService.loadUserByUsername(username);


            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            logger.info("authentication:{}",authentication);

            return authentication;
        }


        throw new AuthenticationException("Invalid JWT token") {};
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }


    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            logger.info(" jwt token {}",token);
            return token;
        }
        return null;
    }
}


