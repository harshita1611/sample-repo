package com.students.studentProfile.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.iqm.util.api.executor.APIExecutor;
import com.iqm.util.api.executor.enums.HttpRequestMethod;
import com.iqm.util.api.executor.models.APIExecutionRequestVO;
import com.iqm.util.json.JsonDeserializer;


/**
 * Custom filter for token-based authentication.
 * Validates tokens against a user info endpoint and sets the authentication context.
 * This filter intercepts HTTP requests and authenticates the user based on the token.
 *
 * @author Nisha
 */
public class CustomTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final String userInfoUri;
    private final APIExecutor apiExecutor;

    /**
     * Constructor for CustomTokenAuthenticationFilter.
     *
     * @param requiresAuthenticationRequestMatcher RequestMatcher for determining the requests this filter will process.
     * @param userInfoUri                          The URI of the user info endpoint used to validate the token.
     * @param failureHandler                       Custom handler for failed authentication attempts.
     */
    public CustomTokenAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher,
                                           String userInfoUri,
                                           AuthenticationFailureHandler failureHandler) {
        super(requiresAuthenticationRequestMatcher);
        this.userInfoUri = userInfoUri;
        this.apiExecutor = APIExecutor.getInstance();
        setAuthenticationFailureHandler(failureHandler);
    }

    /**
     * Attempts to authenticate the user by validating the token provided in the Authorization header.
     *
     * @param request  The HttpServletRequest object containing the client's request.
     * @param response The HttpServletResponse object containing the filter's response.
     * @return Authentication object if successful, throws an AuthenticationServiceException otherwise.
     * @throws IOException if an input or output exception occurs.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // Remove "Bearer " prefix
            User user = validateTokenWithUserInfoEndpoint(token);
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            } else {
                throw new AuthenticationServiceException("Invalid token");
            }
        }
        throw new AuthenticationServiceException("Missing or invalid Authorization header");
    }

    /**
     * Validates the token by making a request to the user info endpoint.
     *
     * @param token The token to validate.
     * @return A User object with username and roles if validation is successful, or null otherwise.
     */
    private User validateTokenWithUserInfoEndpoint(String token) {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + token);

            // API call to user info endpoint
            APIExecutionRequestVO executionRequestVO = new APIExecutionRequestVO();
            executionRequestVO.setHttpRequestMethod(HttpRequestMethod.GET);
            executionRequestVO.setRequestUrl(userInfoUri);
            executionRequestVO.setHeaders(headers);
            String response = apiExecutor.callAPIRequest(executionRequestVO);
            var userInfo = JsonDeserializer.deserialize(Map.class, response);

            if (userInfo != null) {
                String username = (String) userInfo.get("name");
                List<String> authoritiesList = (List<String>) userInfo.get("authorities");
                List<GrantedAuthority> authorities = authoritiesList.stream()
                        .map(authority -> (GrantedAuthority) () -> authority)
                        .toList();

                return new User(username, "", authorities);  // Empty password as we rely on the token
            }
        } catch (Exception e) {
            // Log exception (optional) and return null in case of failure
            return null;
        }
        return null;
    }

    /**
     * Handles the scenario where authentication is successful.
     * Sets the security context with the authenticated user and continues with the filter chain.
     *
     * @param request    The HttpServletRequest object containing the client's request.
     * @param response   The HttpServletResponse object containing the filter's response.
     * @param chain      The FilterChain used to invoke the next filter.
     * @param authResult The result of the successful authentication.
     * @throws IOException      if an input or output exception occurs.
     * @throws ServletException if a servlet exception occurs.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);  // Continue with the next filter in the chain
    }
}
