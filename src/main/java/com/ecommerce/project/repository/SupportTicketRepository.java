package com.ecommerce.project.repository;
import com.ecommerce.project.document.SupportTicket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupportTicketRepository extends MongoRepository<SupportTicket, String> {
}

