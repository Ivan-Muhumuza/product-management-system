package com.ecommerce.project.controller;

import com.ecommerce.project.document.SupportTicket;
import com.ecommerce.project.service.SupportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/support-tickets") // Base URL for all support ticket-related endpoints
public class SupportTicketController {

    private final SupportTicketService supportTicketService; // Service for handling support ticket operations

    // Constructor injection for SupportTicketService
    @Autowired
    public SupportTicketController(SupportTicketService supportTicketService) {
        this.supportTicketService = supportTicketService;
    }

    // POST endpoint to create a new support ticket
    @PostMapping
    public SupportTicket createTicket(@RequestBody SupportTicket ticket) {
        ticket.setCreatedAt(LocalDateTime.now()); // Set the current timestamp for the ticket
        return supportTicketService.createTicket(ticket); // Save the ticket and return it
    }

    // GET endpoint to retrieve all support tickets
    @GetMapping
    public List<SupportTicket> getTickets() {
        return supportTicketService.getAllTickets(); // Return the list of all support tickets
    }
}

