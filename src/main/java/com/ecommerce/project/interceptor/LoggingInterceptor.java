package com.ecommerce.project.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    // Logger instance for logging request data
    private static final Logger logger = Logger.getLogger(LoggingInterceptor.class.getName());

    // Method executed before the request is handled
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        // Log the incoming request URI and method
        logger.info("Incoming request data log: URI: " + request.getRequestURI() + ", Method: " + request.getMethod());
        return true; // Continue with the next interceptor or the handler
    }

    // Method executed after the request is handled but before the response is sent
    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) {
        // Log that the request has been handled
        logger.info("Request handled, URI: " + request.getRequestURI() + ", Method: " + request.getMethod());
    }

    // Method executed after the response has been sent
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        // Log if there was an exception during request processing
        if (ex != null) {
            logger.severe("Request resulted in exception: " + ex.getMessage());
        } else {
            // Log successful completion of the request
            logger.info("Request completed successfully, URI: " + request.getRequestURI() + ", Method: " + request.getMethod());
        }
    }
}

