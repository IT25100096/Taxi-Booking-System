package com.TaxiBookingSystem.demo;

import java.time.LocalDate;

public abstract class Report {
    protected String reportId;
    protected String vehicleId;
    protected LocalDate dateReported;

    public Report(String reportId, String vehicleId, LocalDate dateReported) {
        this.reportId = reportId;
        this.vehicleId = vehicleId;
        this.dateReported = dateReported;
    }

    public String getReportId() {
        return reportId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public LocalDate getDateReported() {
        return dateReported;
    }

    public abstract String getReportDetails();

    public abstract String toFileString();
}
