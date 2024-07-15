package com.ecommerce.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Custom exception to handle cases where a review is not found
@ResponseStatus(HttpStatus.NOT_FOUND) // Respond with 404 Not Found
public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(String id) {
        super("Review not found with ID: " + id); // Custom message
    }
}

