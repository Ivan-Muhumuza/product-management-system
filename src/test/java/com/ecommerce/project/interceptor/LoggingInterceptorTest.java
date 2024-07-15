package com.ecommerce.project.interceptor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggingInterceptorTest {

    private LoggingInterceptor loggingInterceptor;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Object handler;

    @BeforeEach
    public void setUp() {
        loggingInterceptor = new LoggingInterceptor();
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        handler = new Object(); // Mock handler
    }

    @Test
    public void testPreHandle() {
        // Setup mock request data
        Mockito.when(request.getRequestURI()).thenReturn("/api/test");
        Mockito.when(request.getMethod()).thenReturn("GET");

        // Call preHandle method and assert that it returns true
        boolean result = loggingInterceptor.preHandle(request, response, handler);
        assertTrue(result); // Ensure the interceptor allows the request to proceed
    }

    @Test
    public void testPostHandle() {
        // Setup mock request data
        Mockito.when(request.getRequestURI()).thenReturn("/api/test");
        Mockito.when(request.getMethod()).thenReturn("GET");

        // Call postHandle method
        loggingInterceptor.postHandle(request, response, handler, null); // No ModelAndView needed for this test
        // Verification can be done via logs if required
    }

    @Test
    public void testAfterCompletionWithoutException() {
        // Setup mock request data
        Mockito.when(request.getRequestURI()).thenReturn("/api/test");
        Mockito.when(request.getMethod()).thenReturn("GET");

        // Call afterCompletion method without an exception
        loggingInterceptor.afterCompletion(request, response, handler, null);
        // Verification can be done via logs if required
    }

    @Test
    public void testAfterCompletionWithException() {
        // Setup mock request data
        Mockito.when(request.getRequestURI()).thenReturn("/api/test");
        Mockito.when(request.getMethod()).thenReturn("GET");

        // Call afterCompletion method with an exception
        loggingInterceptor.afterCompletion(request, response, handler, new Exception("Test exception"));
        // Verification can be done via logs if required
    }
}

