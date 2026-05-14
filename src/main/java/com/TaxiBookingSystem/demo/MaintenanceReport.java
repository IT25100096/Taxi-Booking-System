package com.TaxiBookingSystem.demo;

import java.time.LocalDate;

public class MaintenanceReport extends Report {
    private String serviceType;
    private boolean isOverdue;

    public MaintenanceReport(String reportId, String vehicleId, LocalDate dateReported, String serviceType,
                             boolean isOverdue) {
        super(reportId, vehicleId, dateReported);
        this.serviceType = serviceType;
        this.isOverdue = isOverdue;
    }

    public String getServiceType() {
        return serviceType;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        this.isOverdue = overdue;
    }

    @Override
    public String getReportDetails() {
        return "Maintenance - " + serviceType + " (Overdue: " + isOverdue + ")";
    }

    @Override
    public String toFileString() {
        return reportId + "," + vehicleId + "," + dateReported.toString() + "," + serviceType + "," + isOverdue;
    }

    public static MaintenanceReport fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 5) {
            return new MaintenanceReport(parts[0], parts[1], LocalDate.parse(parts[2]), parts[3],
                    Boolean.parseBoolean(parts[4]));
        }
        return null;
    }
}
