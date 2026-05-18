package org.example.taxiadmin.service;

import org.example.taxiadmin.model.review.Review;
import org.example.taxiadmin.model.review.PassengerReview;
import org.example.taxiadmin.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // --- 1. READ ALL ---
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // --- 2. READ ONE (For Edit Button) ---
    public Review getReviewById(Integer id) {
        return reviewRepository.findById(id).orElse(null);
    }

    // --- 3. CREATE (New Passenger Log) ---
    public void addPassengerReview(int rating, String comment, String driverId, String driverName, String username) {
        PassengerReview pr = new PassengerReview(rating, comment, driverId, driverName, username);
        reviewRepository.save(pr);
    }

    // --- 4. UPDATE / SAVE ---
    // When the Controller sends an existing Review object with an ID,
    // this method updates the row instead of adding a new one.
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    // --- 5. DELETE ---
    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }

    // --- 6. MATH: DRIVER-SPECIFIC AVERAGE ---
    // FIX: Optimized to ensure the Average Rating only calculates for the visible driver
    public double getAverageRatingForDriver(String driverId) {
        if (driverId == null || driverId.equals("D-N/A")) {
            return 0.0;
        }

        List<Review> allReviews = reviewRepository.findAll();

        return allReviews.stream()
                .filter(r -> driverId.equals(r.getDriverId()))
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }
}