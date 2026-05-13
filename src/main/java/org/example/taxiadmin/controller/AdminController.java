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

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("driverCount", adminService.countDrivers());
        model.addAttribute("vehicleCount", adminService.countVehicles());
        model.addAttribute("logCount", adminService.countLogs());
        return "dashboard";
    }

    @GetMapping("/fleet")
    public String fleet(Model model) {
        model.addAttribute("drivers", adminService.getAllDrivers());
        model.addAttribute("vehicles", adminService.getAllVehicles());
        model.addAttribute("newDriver", new Driver());
        model.addAttribute("newVehicle", new Vehicle());
        return "fleet";
    }

    @PostMapping("/driver/add")
    public String addDriver(@ModelAttribute Driver driver) {
        driver.setStatus("active");
        adminService.addDriver(driver);
        return "redirect:/fleet";
    }

    @GetMapping("/driver/delete/{id}")
    public String deleteDriver(@PathVariable int id) {
        adminService.deleteDriver(id);
        return "redirect:/fleet";
    }

    @GetMapping("/driver/edit/{id}")
    public String editDriver(@PathVariable int id, Model model) {
        model.addAttribute("driver", adminService.getDriverById(id));
        return "editDriver";
    }

    @PostMapping("/driver/update")
    public String updateDriver(@ModelAttribute Driver driver) {
        adminService.updateDriver(driver);
        return "redirect:/fleet";
    }

    @PostMapping("/vehicle/add")
    public String addVehicle(@ModelAttribute Vehicle vehicle, Model model) {
        adminService.addVehicle(vehicle);
        return "redirect:/fleet";
    }

    @GetMapping("/vehicle/delete/{id}")
    public String deleteVehicle(@PathVariable int id) {
        adminService.deleteVehicle(id);
        return "redirect:/fleet";
    }

    @GetMapping("/vehicle/edit/{id}")
    public String editVehicle(@PathVariable int id, Model model) {
        model.addAttribute("vehicle", adminService.getVehicleById(id));
        model.addAttribute("drivers", adminService.getAllDrivers());
        return "editVehicle";
    }

    @PostMapping("/vehicle/update")
    public String updateVehicle(@ModelAttribute Vehicle vehicle) {
        adminService.updateVehicle(vehicle);
        return "redirect:/fleet";
    }

    @GetMapping("/logs")
    public String logs(Model model) {
        model.addAttribute("logs", adminService.getAllLogs());
        return "logs";
    }
}