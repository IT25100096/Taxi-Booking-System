package org.example.taxiadmin.controller;

import org.example.taxiadmin.model.Driver;
import org.example.taxiadmin.model.Vehicle;
import org.example.taxiadmin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Home page
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // Dashboard
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("driverCount", adminService.countDrivers());
        model.addAttribute("vehicleCount", adminService.countVehicles());
        model.addAttribute("logCount", adminService.countLogs());
        return "dashboard";
    }

    // Fleet
    @GetMapping("/fleet")
    public String fleet(Model model) {
        model.addAttribute("drivers", adminService.getAllDrivers());
        model.addAttribute("vehicles", adminService.getAllVehicles());
        model.addAttribute("newDriver", new Driver());
        model.addAttribute("newVehicle", new Vehicle());
        return "fleet";
    }

    // Add driver
    @PostMapping("/driver/add")
    public String addDriver(@ModelAttribute Driver driver) {
        driver.setStatus("active");
        adminService.addDriver(driver);
        return "redirect:/fleet";
    }

    // Delete driver
    @GetMapping("/driver/delete/{id}")
    public String deleteDriver(@PathVariable int id) {
        adminService.deleteDriver(id);
        return "redirect:/fleet";
    }

    // Edit driver
    @GetMapping("/driver/edit/{id}")
    public String editDriver(@PathVariable int id, Model model) {
        model.addAttribute("driver", adminService.getDriverById(id));
        return "editDriver";
    }

    // Update driver
    @PostMapping("/driver/update")
    public String updateDriver(@ModelAttribute Driver driver) {
        adminService.updateDriver(driver);
        return "redirect:/fleet";
    }

    // Add vehicle
    @PostMapping("/vehicle/add")
    public String addVehicle(@ModelAttribute Vehicle vehicle) {
        adminService.addVehicle(vehicle);
        return "redirect:/fleet";
    }

    // Delete vehicle
    @GetMapping("/vehicle/delete/{id}")
    public String deleteVehicle(@PathVariable int id) {
        adminService.deleteVehicle(id);
        return "redirect:/fleet";
    }

    // Edit vehicle
    @GetMapping("/vehicle/edit/{id}")
    public String editVehicle(@PathVariable int id, Model model) {
        model.addAttribute("vehicle", adminService.getVehicleById(id));
        model.addAttribute("drivers", adminService.getAllDrivers());
        return "editVehicle";
    }

    // Update vehicle
    @PostMapping("/vehicle/update")
    public String updateVehicle(@ModelAttribute Vehicle vehicle) {
        adminService.updateVehicle(vehicle);
        return "redirect:/fleet";
    }

    // Logs
    @GetMapping("/logs")
    public String logs(Model model) {
        model.addAttribute("logs", adminService.getAllLogs());
        return "logs";
    }

    // Member 1 pages
    @GetMapping("/user-login")
    public String userLogin() { return "user-login"; }

    @GetMapping("/user-register")
    public String userRegister() { return "user-register"; }

    @GetMapping("/driver-profile")
    public String driverProfile() { return "driver-profile"; }

    @GetMapping("/passenger-profile")
    public String passengerProfile() { return "passenger-profile"; }

    // Member 2 pages
    @GetMapping("/booking-book")
    public String bookingBook() { return "booking-book"; }

    @GetMapping("/booking-dashboard")
    public String bookingDashboard() { return "booking-dashboard"; }

    @GetMapping("/booking-history")
    public String bookingHistory() { return "booking-history"; }

    // Member 3 pages
    @GetMapping("/maintenance-dashboard")
    public String maintenanceDashboard() { return "maintenance-dashboard"; }

    @GetMapping("/driver-checklist")
    public String driverChecklist() { return "driver-checklist"; }

    @GetMapping("/repair-history")
    public String repairHistory() { return "repair-history"; }

    // Member 4 pages
    @GetMapping("/payment-summary")
    public String paymentSummary() { return "payment-summary"; }

    @GetMapping("/payment-history")
    public String paymentHistory() { return "payment-history"; }

    @GetMapping("/payment-invoice")
    public String paymentInvoice() { return "payment-invoice"; }

    @GetMapping("/review-dashboard")
    public String reviewDashboard() { return "review-dashboard"; }

    @GetMapping("/review-logs")
    public String reviewLogs() { return "review-logs"; }

    @GetMapping("/review-fleet")
    public String reviewFleet() { return "review-fleet"; }
}