package org.example.taxiadmin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passenger;
    private String pickup;
    private String dropoff;
    private String rideType;
    private String status = "pending";
    private double fare;

    public Long getId() { return id; }
    public String getPassenger() { return passenger; }
    public void setPassenger(String passenger) { this.passenger = passenger; }
    public String getPickup() { return pickup; }
    public void setPickup(String pickup) { this.pickup = pickup; }
    public String getDropoff() { return dropoff; }
    public void setDropoff(String dropoff) { this.dropoff = dropoff; }
    public String getRideType() { return rideType; }
    public void setRideType(String rideType) { this.rideType = rideType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }
}
