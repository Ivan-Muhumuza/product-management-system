package com.ecommerce.project.controller;

import com.ecommerce.project.document.SupportTicket;
import com.ecommerce.project.service.SupportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import java.util.stream.Collectors;


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
    public ResponseEntity<EntityModel<SupportTicket>> createTicket(@RequestBody SupportTicket ticket) {
        ticket.setCreatedAt(LocalDateTime.now()); // Set the current timestamp for the ticket
        SupportTicket createdTicket = supportTicketService.createTicket(ticket); // Save the ticket

        // Create HATEOAS links for the created ticket
        EntityModel<SupportTicket> entityModel = EntityModel.of(createdTicket,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SupportTicketController.class)
                        .getTicketById(createdTicket.getTicketId())).withSelfRel(), // Self link
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SupportTicketController.class)
                        .getTickets()).withRel("support-tickets") // Link to all tickets
        );

        return ResponseEntity.created(entityModel.getRequiredLink("self").toUri()).body(entityModel); // Return the created ticket with 201 Created status
    }

    // GET endpoint to retrieve all support tickets
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<SupportTicket>>> getTickets() {
        List<SupportTicket> tickets = supportTicketService.getAllTickets(); // Retrieve all tickets

        // Create a list of EntityModel<SupportTicket> with HATEOAS links
        List<EntityModel<SupportTicket>> ticketResources = tickets.stream()
                .map(ticket -> EntityModel.of(ticket,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SupportTicketController.class)
                                .getTicketById(ticket.getTicketId())).withSelfRel(), // Self link
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SupportTicketController.class)
                                .getTickets()).withRel("support-tickets") // Link to all tickets
                ))
                .collect(Collectors.toList());

        // Create CollectionModel and add a link to the tickets endpoint
        CollectionModel<EntityModel<SupportTicket>> collectionModel = CollectionModel.of(ticketResources,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SupportTicketController.class)
                        .getTickets()).withSelfRel()); // Link to the tickets endpoint

        return ResponseEntity.ok(collectionModel); // Return the collection of tickets
    }

    // GET endpoint to retrieve a specific ticket by ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SupportTicket>> getTicketById(@PathVariable String id) {
        SupportTicket ticket = supportTicketService.getTicketById(id); // Retrieve the ticket
        EntityModel<SupportTicket> entityModel = EntityModel.of(ticket,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SupportTicketController.class)
                        .getTicketById(id)).withSelfRel(), // Self link
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SupportTicketController.class)
                        .getTickets()).withRel("support-tickets") // Link to all tickets
        );

        return ResponseEntity.ok(entityModel); // Return the ticket with HATEOAS links
    }
}

