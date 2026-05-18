package org.example.taxiadmin.model.review;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "review_type")
public abstract class Review { // ABSTRACTION: Can't create a generic 'Review'

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ENCAPSULATION: Private fields
    private String driverId;    // The unique code (e.g., D-101)
    private String driverName;  // The name passed
    private String username;    // The name of the registered passenger
    private int rating;
    private String comment;

    // GETTERS AND SETTERS

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    // abstract method
    public abstract String getFormattedReview();
}