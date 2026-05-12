package org.example.taxiadmin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int driverId;
    private String model;
    private String plateNumber;
    private String vehicleType;

    public Vehicle() {}

    public Vehicle(int driverId, String model, String plateNumber, String vehicleType) {
        this.driverId = driverId;
        this.model = model;
        this.plateNumber = plateNumber;
        this.vehicleType = vehicleType;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getDriverId() { return driverId; }
    public void setDriverId(int driverId) { this.driverId = driverId; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
}