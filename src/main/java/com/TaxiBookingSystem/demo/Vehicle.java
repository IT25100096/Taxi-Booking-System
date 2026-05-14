
package com.TaxiBookingSystem.demo;

public class Vehicle {
    private String vehicleId;
    private String licensePlate;
    private String model;
    private VehicleState state;

    public Vehicle(String vehicleId, String licensePlate, String model) {
        this.vehicleId = vehicleId;
        this.licensePlate = licensePlate;
        this.model = model;
        this.state = VehicleState.AVAILABLE; // Default state
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleState getState() {
        return state;
    }

    // Applying State Pattern Concept: Centralizing state transitions
    public void setState(VehicleState state) {
        this.state = state;
    }

    public void markAsUnderRepair() {
        this.state = VehicleState.UNDER_REPAIR;
    }

    public void markAsRoadReady() {
        this.state = VehicleState.AVAILABLE;
    }

    @Override
    public String toString() {
        return vehicleId + "," + licensePlate + "," + model + "," + state.name();
    }
}
