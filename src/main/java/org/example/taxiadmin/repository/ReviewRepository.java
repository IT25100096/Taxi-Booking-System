package org.example.taxiadmin.repository;

import org.example.taxiadmin.model.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    // Add this to find all logs for a specific driver
    List<Review> findByDriverId(String driverId);
}