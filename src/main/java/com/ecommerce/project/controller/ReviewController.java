package com.ecommerce.project.controller;

import com.ecommerce.project.document.Review;
import com.ecommerce.project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@RestController
@RequestMapping("/reviews") // Base URL for all review-related endpoints
public class ReviewController {

    private final ReviewService reviewService; // Service for handling review operations

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService; // Constructor injection for ReviewService
    }

    // POST endpoint to add a new review
    @PostMapping
    public Review addReview(@RequestBody Review review) {
        review.setTimestamp(LocalDateTime.now()); // Set the current timestamp for the review
        return reviewService.saveReview(review); // Save the review and return it
    }

    // GET endpoint to retrieve all reviews with HATEOAS links
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Review>>> getReviews() {
        List<Review> reviews = reviewService.getAllReviews(); // Retrieve all reviews

        // Create a list of EntityModel<Review> with HATEOAS links
        List<EntityModel<Review>> reviewResources = reviews.stream()
                .map(review -> EntityModel.of(review,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReviewController.class)
                                .getReviewById(review.getId())).withSelfRel(), // Self link
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReviewController.class)
                                .getReviews()).withRel("reviews")) // Link to all reviews
                )
                .collect(Collectors.toList());

        // Create CollectionModel and add a link to the reviews endpoint
        CollectionModel<EntityModel<Review>> collectionModel = CollectionModel.of(reviewResources,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReviewController.class)
                        .getReviews()).withSelfRel()); // Link to the reviews endpoint

        return ResponseEntity.ok(collectionModel); // Return the collection of reviews
    }

    // GET endpoint to retrieve a specific review by ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Review>> getReviewById(@PathVariable String id) {
        Review review = reviewService.getReviewById(id); // Get the review by ID
        EntityModel<Review> reviewResource = EntityModel.of(review,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReviewController.class)
                        .getReviewById(id)).withSelfRel(), // Self link
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReviewController.class)
                        .getReviews()).withRel("reviews")); // Link to all reviews

        return ResponseEntity.ok(reviewResource); // Return the review with HATEOAS links
    }
}
