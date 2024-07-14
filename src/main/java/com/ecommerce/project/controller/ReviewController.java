package com.ecommerce.project.controller;

import com.ecommerce.project.document.Review;
import com.ecommerce.project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reviews") // Base URL for all review-related endpoints
public class ReviewController {

    private final ReviewService reviewService; // Service for handling review operations

    // Constructor injection for ReviewService
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // POST endpoint to add a new review
    @PostMapping
    public Review addReview(@RequestBody Review review) {
        review.setTimestamp(LocalDateTime.now()); // Set the current timestamp for the review
        return reviewService.saveReview(review); // Save the review and return it
    }

    // GET endpoint to retrieve all reviews
    @GetMapping
    public List<Review> getReviews() {
        return reviewService.getAllReviews(); // Return the list of all reviews
    }
}

