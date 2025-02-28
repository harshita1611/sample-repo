package com.students.studentProfile.controller;

import com.students.studentProfile.model.User;
import com.students.studentProfile.service.UserService;
import com.students.studentProfile.utils.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user and stores it in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user input")
    })
    public String createNewUser(@Valid @RequestBody User newUser) {
        logger.info("inside create new user");
        return userService.createNewUser(newUser);
    }

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Authenticates user and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login", content = @Content(schema = @Schema(example = "{ \"token\": \"your-jwt-token\" }"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
    })
    public Map<String, String> login(@RequestBody User loginRequest) {
        logger.info("inside login");
        Map<String, String> response = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = tokenProvider.createToken(userDetails.getUsername(), userDetails.getAuthorities());
            response.put("token", token);
        } catch (AuthenticationException e) {
            response.put("error", "Invalid username or password");
        }
        return response;
    }
}
