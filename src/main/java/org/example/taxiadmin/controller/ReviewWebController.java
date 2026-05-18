package org.example.taxiadmin.controller;

import org.example.taxiadmin.model.review.Review;
import org.example.taxiadmin.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewWebController {

    @Autowired
    private ReviewService reviewService;

    // read and dashboard view
    @GetMapping
    public String showDashboard(
            @RequestParam(defaultValue = "D-N/A") String driverId,
            @RequestParam(defaultValue = "Unknown Unit") String driverName,
            @RequestParam(defaultValue = "Guest_User") String passengerName,
            Model model) {

        List<Review> allReviews = reviewService.getAllReviews();
        model.addAttribute("allReviews", allReviews);

        //  TYPE LOGIC
        // Pulls the class name (e.g., PassengerReview) from the last log entry
        String recentType = "N/A";
        if (!allReviews.isEmpty()) {
            recentType = allReviews.get(allReviews.size() - 1).getClass().getSimpleName();
        }
        model.addAttribute("recentType", recentType);

        // UI Context for Sidebar/Bottom Bar
        model.addAttribute("targetDriver", driverId);
        model.addAttribute("driverName", driverName);
        model.addAttribute("currentUser", passengerName);

        // --- DYNAMIC AVERAGE FOR "RECENT TRENDS: SATISFACTION" ---
        // This calculates the math only for the current driverId (D-99, etc.)
        double avg = reviewService.getAverageRatingForDriver(driverId);
        model.addAttribute("averageRating", avg);

        return "reviews";
    }

    //   CREATE: ADD NEW LOG
    @PostMapping("/add")
    public String addReviewFromForm(
            @RequestParam int rating,
            @RequestParam String comment,
            @RequestParam String driverId,
            @RequestParam String driverName,
            @RequestParam String username) {

        reviewService.addPassengerReview(rating, comment, driverId, driverName, username);
        // Always redirect with params to keep the UI context locked on the driver
        return "redirect:/reviews?driverId=" + driverId + "&driverName=" + driverName + "&passengerName=" + username;
    }

    // FIX FOR DUPLICATE COMMENTS
    @PostMapping("/update/{id}")
    public String updateReview(
            @PathVariable Integer id,
            @RequestParam int rating,
            @RequestParam String comment,
            @RequestParam String driverId,
            @RequestParam String driverName,
            @RequestParam String username) {

        // We load the existing entity by ID first.
        // This ensures JPA performs an UPDATE instead of an INSERT.
        Review existing = reviewService.getReviewById(id);
        if (existing != null) {
            existing.setRating(rating);
            existing.setComment(comment);
            // The service.saveReview calls repository.save(), which updates the existing row
            reviewService.saveReview(existing);
        }

        return "redirect:/reviews?driverId=" + driverId + "&driverName=" + driverName + "&passengerName=" + username;
    }

    // --- 4. DELETE: REMOVE LOG ---
    @PostMapping("/delete/{id}")
    public String deleteReview(
            @PathVariable Integer id,
            @RequestParam String driverId,
            @RequestParam String driverName,
            @RequestParam String username) {

        reviewService.deleteReview(id);
        return "redirect:/reviews?driverId=" + driverId + "&driverName=" + driverName + "&passengerName=" + username;
    }
}