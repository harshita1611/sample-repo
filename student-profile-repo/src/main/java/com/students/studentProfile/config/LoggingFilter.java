package com.students.studentProfile.config;

import com.students.studentProfile.service.StudentService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {
    private final Logger logger= LoggerFactory.getLogger(LoggingFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Optional: Initialization logic
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
//        System.out.println("Incoming request: " + request.getRemoteAddr());
        logger.info("logging incoming request: {}",request.getRemoteAddr());
        chain.doFilter(request, response); // Continue request processing
    }

    @Override
    public void destroy() {
        // Optional: Cleanup logic
    }
}
