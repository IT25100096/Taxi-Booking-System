
package com.TaxiBookingSystem.demo;

import java.time.LocalDate;

public class SafetyIncident extends Report {
    private String issueDescription;
    private Severity severity;

    public SafetyIncident(String reportId, String vehicleId, LocalDate dateReported, String issueDescription,
                          Severity severity) {
        super(reportId, vehicleId, dateReported);
        this.issueDescription = issueDescription;
        this.severity = severity;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public Severity getSeverity() {
        return severity;
    }

    @Override
    public String getReportDetails() {
        return "Safety Incident: " + issueDescription + " [Severity: " + severity.name() + "]";
    }

    @Override
    public String toFileString() {

        return reportId + "," + vehicleId + "," + dateReported.toString() + "," + issueDescription + ","
                + severity.name();
    }

    public static SafetyIncident fromFileString(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length == 5) {
                return new SafetyIncident(parts[0].trim(), parts[1].trim(), LocalDate.parse(parts[2].trim()),
                        parts[3].trim(), Severity.valueOf(parts[4].trim()));
            }
        } catch (Exception ignored) {
            // Skip malformed lines
        }
        return null;
    }
}
