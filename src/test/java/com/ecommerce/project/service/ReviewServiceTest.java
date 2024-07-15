package com.ecommerce.project.service;

import com.ecommerce.project.document.Review;
import com.ecommerce.project.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository; // Mocked ReviewRepository

    @InjectMocks
    private ReviewService reviewService; // Service to be tested

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
    }

    @Test
    public void testSaveReview() {
        Review review = new Review();
        review.setRating(4.5);
        review.setComment("Great product!");
        when(reviewRepository.save(review)).thenReturn(review); // Mock save behavior

        Review savedReview = reviewService.saveReview(review);
        assertNotNull(savedReview); // Ensure the saved review is not null
        assertEquals(4.5, savedReview.getRating()); // Check the review rating
    }

    @Test
    public void testGetAllReviews() {
        Review review1 = new Review();
        review1.setComment("Excellent!");
        Review review2 = new Review();
        review2.setComment("Not bad.");
        when(reviewRepository.findAll()).thenReturn(Arrays.asList(review1, review2)); // Mock findAll behavior

        List<Review> reviews = reviewService.getAllReviews();
        assertEquals(2, reviews.size()); // Verify the number of reviews returned
    }
}

