package org.example.taxiadmin.model.review;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("PASSENGER")
public class PassengerReview extends Review {

    public PassengerReview() {} // Default constructor for JPA

    public PassengerReview(int rating, String comment, String driverId, String driverName, String username) {
        this.setRating(rating);
        this.setComment(comment);
        this.setDriverId(driverId);
        this.setDriverName(driverName);
        this.setUsername(username);
    }

    @Override
    public String getFormattedReview() {
        return "Passenger [" + getUsername() + "] rated Driver [" + getDriverName() + "]: " + getRating() + " stars.";
    }
}