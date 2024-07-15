package com.ecommerce.project.service;

import com.ecommerce.project.document.Review;
import com.ecommerce.project.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository; // Repository for accessing reviews

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository; // Constructor injection of the review repository
    }

    // Save a new review to the database
    public Review saveReview(Review review) {
        return reviewRepository.save(review); // Return the saved review
    }

    // Retrieve all reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll(); // Return the list of all reviews
    }
}


