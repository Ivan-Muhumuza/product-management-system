package com.ecommerce.project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ecommerce.project.document.Review;

public interface ReviewRepository extends MongoRepository<Review, String> {
}

