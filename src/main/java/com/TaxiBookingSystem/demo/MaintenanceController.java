package com.TaxiBookingSystem.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
public class MaintenanceController {

    @Autowired
    private AuditDataManager auditDataManager;

    @GetMapping("/maintenance-dashboard")
    public String maintenanceDashboard(Model model) {
        try {
            List<MaintenanceReport> overdue = auditDataManager.getOverdueMaintenance();
            model.addAttribute("overdueCount", overdue.size());
            model.addAttribute("overdueReports", overdue);
        } catch (IOException e) {
            model.addAttribute("overdueCount", 0);
            model.addAttribute("overdueReports", Collections.emptyList());
        }
        return "maintenance-dashboard";
    }

    @GetMapping("/driver-checklist")
    public String driverChecklist() {
        return "driver-checklist";
    }

    @GetMapping("/repair-history")
    public String repairHistory(
            @RequestParam(defaultValue = "V-001") String vehicleId,
            Model model) {
        try {
            model.addAttribute("vehicleId", vehicleId);
            model.addAttribute("history", auditDataManager.getVehicleHistory(vehicleId));
        } catch (IOException e) {
            model.addAttribute("vehicleId", vehicleId);
            model.addAttribute("history", Collections.emptyList());
        }
        return "repair-history";
    }
}
