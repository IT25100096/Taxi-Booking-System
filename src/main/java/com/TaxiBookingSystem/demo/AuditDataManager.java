package com.TaxiBookingSystem.demo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuditDataManager {

    private static final String MAINTENANCE_FILE = "maintenance_logs.txt";
    private static final String INCIDENT_FILE = "incident_reports.txt";

    // Create: Add new Maintenance Report
    public void addMaintenanceReport(MaintenanceReport report) throws IOException {
        appendToFile(MAINTENANCE_FILE, report.toFileString());
    }

    // Create: Add new Safety Incident
    public void addSafetyIncident(SafetyIncident incident) throws IOException {
        appendToFile(INCIDENT_FILE, incident.toFileString());
    }

    // Read: Get all Overdue Vehicles
    public List<MaintenanceReport> getOverdueMaintenance() throws IOException {
        return readAllMaintenanceReports().stream()
                .filter(MaintenanceReport::isOverdue)
                .collect(Collectors.toList());
    }

    // Read: Get repair history for a specific vehicle
    public List<Report> getVehicleHistory(String vehicleId) throws IOException {
        List<Report> history = new ArrayList<>();

        history.addAll(readAllMaintenanceReports().stream()
                .filter(r -> r.getVehicleId().equals(vehicleId))
                .collect(Collectors.toList()));

        history.addAll(readAllSafetyIncidents().stream()
                .filter(r -> r.getVehicleId().equals(vehicleId))
                .collect(Collectors.toList()));

        return history;
    }

    // Update: Update maintenance status
    public void updateMaintenanceStatus(String reportId, boolean isOverdue) throws IOException {
        List<MaintenanceReport> reports = readAllMaintenanceReports();
        boolean updated = false;

        for (MaintenanceReport report : reports) {
            if (report.getReportId().equals(reportId)) {
                report.setOverdue(isOverdue);
                updated = true;
                break;
            }
        }

        if (updated) {
            rewriteMaintenanceFile(reports);
        }
    }

    // Delete: Remove old maintenance records for a retired vehicle
    public void deleteRecordsForVehicle(String vehicleId) throws IOException {
        List<MaintenanceReport> reports = readAllMaintenanceReports();
        reports.removeIf(report -> report.getVehicleId().equals(vehicleId));
        rewriteMaintenanceFile(reports);

        List<SafetyIncident> incidents = readAllSafetyIncidents();
        incidents.removeIf(incident -> incident.getVehicleId().equals(vehicleId));
        rewriteIncidentFile(incidents);
    }

    private void appendToFile(String filename, String content) throws IOException {
        File file = new File(filename);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(content);
            bw.newLine();
        }
    }

    private List<MaintenanceReport> readAllMaintenanceReports() throws IOException {
        List<MaintenanceReport> reports = new ArrayList<>();
        File file = new File(MAINTENANCE_FILE);
        if (!file.exists())
            return reports;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                MaintenanceReport report = MaintenanceReport.fromFileString(line);
                if (report != null)
                    reports.add(report);
            }
        }
        return reports;
    }

    private List<SafetyIncident> readAllSafetyIncidents() throws IOException {
        List<SafetyIncident> incidents = new ArrayList<>();
        File file = new File(INCIDENT_FILE);
        if (!file.exists())
            return incidents;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                SafetyIncident incident = SafetyIncident.fromFileString(line);
                if (incident != null)
                    incidents.add(incident);
            }
        }
        return incidents;
    }

    private void rewriteMaintenanceFile(List<MaintenanceReport> reports) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MAINTENANCE_FILE, false))) {
            for (MaintenanceReport report : reports) {
                bw.write(report.toFileString());
                bw.newLine();
            }
        }
    }

    private void rewriteIncidentFile(List<SafetyIncident> incidents) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INCIDENT_FILE, false))) {
            for (SafetyIncident incident : incidents) {
                bw.write(incident.toFileString());
                bw.newLine();
            }
        }
    }
}
