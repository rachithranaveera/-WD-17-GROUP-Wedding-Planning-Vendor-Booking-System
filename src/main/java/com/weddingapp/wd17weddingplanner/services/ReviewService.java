package com.weddingapp.wd17weddingplanner.services;

import com.weddingapp.wd17weddingplanner.repository.ReviewRepository;
import com.weddingapp.wd17weddingplanner.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review saveReview(Review review) {
        review.setReviewDate(LocalDate.now());
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}