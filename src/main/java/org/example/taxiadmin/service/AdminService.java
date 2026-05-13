package org.example.taxiadmin.service;

import org.example.taxiadmin.model.AdminLog;
import org.example.taxiadmin.model.Driver;
import org.example.taxiadmin.model.Vehicle;
import org.example.taxiadmin.repository.AdminLogRepository;
import org.example.taxiadmin.repository.DriverRepository;
import org.example.taxiadmin.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AdminLogRepository adminLogRepository;

    // Driver CRUD
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public void addDriver(Driver driver) {
        driverRepository.save(driver);
        adminLogRepository.save(new AdminLog("Added driver: " + driver.getName()));
    }

    public Driver getDriverById(int id) {
        return driverRepository.findById(id).orElse(null);
    }

    public void updateDriver(Driver driver) {
        driverRepository.save(driver);
        adminLogRepository.save(new AdminLog("Updated driver: " + driver.getName()));
    }

    public void deleteDriver(int id) {
        Driver driver = getDriverById(id);
        if (driver != null) {
            adminLogRepository.save(new AdminLog("Deleted driver: " + driver.getName()));
            driverRepository.deleteById(id);
        }
    }

    // Vehicle CRUD
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        adminLogRepository.save(new AdminLog("Added vehicle: " + vehicle.getPlateNumber()));
    }

    // Logs
    public List<AdminLog> getAllLogs() {
        return adminLogRepository.findAll();
    }

    // Stats for dashboard
    public long countDrivers() { return driverRepository.count(); }
    public long countVehicles() { return vehicleRepository.count(); }
    public long countLogs() { return adminLogRepository.count(); }
}