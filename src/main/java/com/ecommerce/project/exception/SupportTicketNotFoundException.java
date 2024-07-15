package com.ecommerce.project.exception;

public class SupportTicketNotFoundException extends RuntimeException {
    public SupportTicketNotFoundException(String message) {
        super(message);
    }
}

