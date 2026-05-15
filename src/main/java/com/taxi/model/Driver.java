package com.taxi.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("DRIVER")
public class Driver extends User {

    @Column(name = "licenseNumber")
    private String licenseNumber;

    @Column(name = "vehiclePlate")
    private String vehiclePlate;

    @Column(name = "vehicleModel")
    private String vehicleModel;

    @Column(name = "vehicleColor")
    private String vehicleColor;

    @Column(name = "vehicleYear")
    private Integer vehicleYear;

    @Column(name = "isAvailable")
    private boolean isAvailable = false;

    @Column(name = "rating")
    private double rating = 5.0;

    // Empty constructor
    public Driver() {}

    // Constructor
    public Driver(String userId, String fullName, String email,
                  String passwordHash, String phone,
                  String licenseNumber, String vehiclePlate,
                  String vehicleModel, String vehicleColor,
                  Integer vehicleYear) {
        super(userId, fullName, email, passwordHash, phone);
        this.licenseNumber = licenseNumber;
        this.vehiclePlate = vehiclePlate;
        this.vehicleModel = vehicleModel;
        this.vehicleColor = vehicleColor;
        this.vehicleYear = vehicleYear;
        this.isAvailable = false;
        this.rating = 5.0;
    }

    // Getters and Setters
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getVehiclePlate() { return vehiclePlate; }
    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getVehicleModel() { return vehicleModel; }
    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleColor() { return vehicleColor; }
    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public Integer getVehicleYear() { return vehicleYear; }
    public void setVehicleYear(Integer vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}