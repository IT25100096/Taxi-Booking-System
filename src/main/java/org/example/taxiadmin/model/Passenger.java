package org.example.taxiadmin.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PASSENGER")
public class Passenger extends User {

    @Column(name = "homeAddress")
    private String homeAddress;

    @Column(name = "paymentMethod")
    private String paymentMethod = "CASH";

    // Empty constructor
    public Passenger() {}

    // Constructor
    public Passenger(String userId, String fullName, String email,
                     String passwordHash, String phone,
                     String homeAddress) {
        super(userId, fullName, email, passwordHash, phone);
        this.homeAddress = homeAddress;
        this.paymentMethod = "CASH";
    }

    // Getters and Setters
    public String getHomeAddress() { return homeAddress; }
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}