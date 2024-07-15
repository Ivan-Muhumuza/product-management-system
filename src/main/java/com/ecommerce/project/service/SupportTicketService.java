package com.ecommerce.project.service;

import com.ecommerce.project.document.SupportTicket;
import com.ecommerce.project.repository.SupportTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportTicketService {
    private final SupportTicketRepository supportTicketRepository; // Repository for accessing support tickets

    @Autowired
    public SupportTicketService(SupportTicketRepository supportTicketRepository) {
        this.supportTicketRepository = supportTicketRepository; // Constructor injection of the support ticket repository
    }

    // Create a new support ticket in the database
    public SupportTicket createTicket(SupportTicket ticket) {
        return supportTicketRepository.save(ticket); // Return the saved support ticket
    }

    // Retrieve all support tickets
    public List<SupportTicket> getAllTickets() {
        return supportTicketRepository.findAll(); // Return the list of all support tickets
    }
}


