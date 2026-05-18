package com.TaxiBookingSystem.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditDataManager {

    private static final String MAINTENANCE_FILE = "maintenance_logs.txt";
    private static final String INCIDENT_FILE = "incident_reports.txt";

    @Value("${taxi.audit.data-dir:data}")
    private String dataDir;

    private Path maintenancePath;
    private Path incidentPath;

    @PostConstruct
    void init() throws IOException {
        Path base = Paths.get(dataDir);
        Files.createDirectories(base);
        maintenancePath = base.resolve(MAINTENANCE_FILE);
        incidentPath = base.resolve(INCIDENT_FILE);
        seedIfMissing(maintenancePath, MAINTENANCE_FILE);
        seedIfMissing(incidentPath, INCIDENT_FILE);
    }

    private void seedIfMissing(Path target, String classpathName) throws IOException {
        if (Files.exists(target) && Files.size(target) > 0) {
            return;
        }
        ClassPathResource resource = new ClassPathResource(classpathName);
        if (resource.exists()) {
            try (InputStream in = resource.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } else {
            Files.createFile(target);
        }
    }

    public void addMaintenanceReport(MaintenanceReport report) throws IOException {
        appendToFile(maintenancePath, report.toFileString());
    }

    public void addSafetyIncident(SafetyIncident incident) throws IOException {
        appendToFile(incidentPath, incident.toFileString());
    }

    public List<MaintenanceReport> getOverdueMaintenance() throws IOException {
        return readAllMaintenanceReports().stream()
                .filter(MaintenanceReport::isOverdue)
                .collect(Collectors.toList());
    }

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

    public void deleteRecordsForVehicle(String vehicleId) throws IOException {
        List<MaintenanceReport> reports = readAllMaintenanceReports();
        reports.removeIf(report -> report.getVehicleId().equals(vehicleId));
        rewriteMaintenanceFile(reports);

        List<SafetyIncident> incidents = readAllSafetyIncidents();
        incidents.removeIf(incident -> incident.getVehicleId().equals(vehicleId));
        rewriteIncidentFile(incidents);
    }

    private boolean shouldSkipLine(String line) {
        if (line == null) {
            return true;
        }
        String trimmed = line.trim();
        return trimmed.isEmpty() || trimmed.startsWith("#");
    }

    private void appendToFile(Path file, String content) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.toFile(), true))) {
            bw.write(content);
            bw.newLine();
        }
    }

    private List<MaintenanceReport> readAllMaintenanceReports() throws IOException {
        List<MaintenanceReport> reports = new ArrayList<>();
        if (!Files.exists(maintenancePath)) {
            return reports;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(maintenancePath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (shouldSkipLine(line)) {
                    continue;
                }
                MaintenanceReport report = MaintenanceReport.fromFileString(line.trim());
                if (report != null) {
                    reports.add(report);
                }
            }
        }
        return reports;
    }

    private List<SafetyIncident> readAllSafetyIncidents() throws IOException {
        List<SafetyIncident> incidents = new ArrayList<>();
        if (!Files.exists(incidentPath)) {
            return incidents;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(incidentPath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (shouldSkipLine(line)) {
                    continue;
                }
                SafetyIncident incident = SafetyIncident.fromFileString(line.trim());
                if (incident != null) {
                    incidents.add(incident);
                }
            }
        }
        return incidents;
    }

    private void rewriteMaintenanceFile(List<MaintenanceReport> reports) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(maintenancePath.toFile(), false))) {
            for (MaintenanceReport report : reports) {
                bw.write(report.toFileString());
                bw.newLine();
            }
        }
    }

    private void rewriteIncidentFile(List<SafetyIncident> incidents) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(incidentPath.toFile(), false))) {
            for (SafetyIncident incident : incidents) {
                bw.write(incident.toFileString());
                bw.newLine();
            }
        }
    }
}
