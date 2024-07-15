package com.ecommerce.project.exception;

// Custom exception to indicate that a requested resource was not found
public class ResourceNotFoundException extends RuntimeException {

    // Constructor that accepts a message to describe the exception
    public ResourceNotFoundException(String message) {
        super(message); // Pass the message to the superclass (RuntimeException)
    }
}

