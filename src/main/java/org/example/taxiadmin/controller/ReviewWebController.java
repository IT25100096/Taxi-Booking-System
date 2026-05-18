package org.example.taxiadmin.controller;

import org.example.taxiadmin.model.review.Review;
import org.example.taxiadmin.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewWebController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String reviews(
            @RequestParam(required = false) String driverId,
            Model model) {

        String targetDriver = (driverId != null && !driverId.isBlank()) ? driverId : "D-101";
        model.addAttribute("allReviews", reviewService.getAllReviews());
        model.addAttribute("targetDriver", targetDriver);
        model.addAttribute("driverName", "Fleet Driver");
        model.addAttribute("currentUser", "Passenger");
        model.addAttribute("avgRating", reviewService.getAverageRatingForDriver(targetDriver));
        return "reviews";
    }

    @PostMapping("/add")
    public String addReview(
            @RequestParam int rating,
            @RequestParam String comment,
            @RequestParam(defaultValue = "D-101") String driverId,
            @RequestParam(defaultValue = "Fleet Driver") String driverName,
            @RequestParam(defaultValue = "Passenger") String username) {

        reviewService.addPassengerReview(rating, comment, driverId, driverName, username);
        return "redirect:/reviews?driverId=" + driverId;
    }

    @PostMapping("/update/{id}")
    public String updateReview(
            @PathVariable Integer id,
            @RequestParam int rating,
            @RequestParam String comment,
            @RequestParam(defaultValue = "D-101") String driverId,
            @RequestParam(defaultValue = "Fleet Driver") String driverName,
            @RequestParam(defaultValue = "Passenger") String username) {

        Review existing = reviewService.getReviewById(id);
        if (existing != null) {
            existing.setRating(rating);
            existing.setComment(comment);
            existing.setDriverId(driverId);
            existing.setDriverName(driverName);
            existing.setUsername(username);
            reviewService.saveReview(existing);
        }
        return "redirect:/reviews?driverId=" + driverId;
    }

    @PostMapping("/delete/{id}")
    public String deleteReview(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "D-101") String driverId) {

        reviewService.deleteReview(id);
        return "redirect:/reviews?driverId=" + driverId;
    }
}
