package com.ecommerce.project.service;

import com.ecommerce.project.document.SupportTicket;
import com.ecommerce.project.repository.SupportTicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SupportTicketServiceTest {

    @Mock
    private SupportTicketRepository supportTicketRepository; // Mocked SupportTicketRepository

    @InjectMocks
    private SupportTicketService supportTicketService; // Service to be tested

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
    }

    @Test
    public void testCreateTicket() {
        SupportTicket ticket = new SupportTicket();
        ticket.setIssue("Defective item");
        when(supportTicketRepository.save(ticket)).thenReturn(ticket); // Mock save behavior

        SupportTicket createdTicket = supportTicketService.createTicket(ticket);
        assertNotNull(createdTicket); // Ensure the created ticket is not null
        assertEquals("Defective item", createdTicket.getIssue()); // Check the ticket issue
    }

    @Test
    public void testGetAllTickets() {
        SupportTicket ticket1 = new SupportTicket();
        ticket1.setIssue("Issue 1");
        SupportTicket ticket2 = new SupportTicket();
        ticket2.setIssue("Issue 2");
        when(supportTicketRepository.findAll()).thenReturn(Arrays.asList(ticket1, ticket2)); // Mock findAll behavior

        List<SupportTicket> tickets = supportTicketService.getAllTickets();
        assertEquals(2, tickets.size()); // Verify the number of tickets returned
    }
}

