package org.example.taxiadmin.service;

import org.example.taxiadmin.model.AdminLog;
import org.example.taxiadmin.model.FleetDriver;
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

    public List<FleetDriver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public void addDriver(FleetDriver driver) {
        driverRepository.save(driver);
        adminLogRepository.save(new AdminLog("Added driver: " + driver.getName()));
    }

    public FleetDriver getDriverById(int id) {
        return driverRepository.findById(id).orElse(null);
    }

    public void updateDriver(FleetDriver driver) {
        driverRepository.save(driver);
        adminLogRepository.save(new AdminLog("Updated driver: " + driver.getName()));
    }

    public void deleteDriver(int id) {
        FleetDriver driver = getDriverById(id);
        if (driver != null) {
            List<Vehicle> vehicles = vehicleRepository.findAll();
            for (Vehicle v : vehicles) {
                if (v.getDriverId() == id) {
                    vehicleRepository.deleteById(v.getId());
                }
            }
            adminLogRepository.save(new AdminLog("Deleted driver: " + driver.getName()));
            driverRepository.deleteById(id);
        }
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        adminLogRepository.save(new AdminLog("Added vehicle: " + vehicle.getPlateNumber()));
    }

    public Vehicle getVehicleById(int id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public void updateVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        adminLogRepository.save(new AdminLog("Updated vehicle: " + vehicle.getPlateNumber()));
    }

    public void deleteVehicle(int id) {
        Vehicle vehicle = getVehicleById(id);
        if (vehicle != null) {
            adminLogRepository.save(new AdminLog("Deleted vehicle: " + vehicle.getPlateNumber()));
            vehicleRepository.deleteById(id);
        }
    }

    public List<AdminLog> getAllLogs() {
        return adminLogRepository.findAll();
    }

    public long countDrivers() { return driverRepository.count(); }
    public long countVehicles() { return vehicleRepository.count(); }
    public long countLogs() { return adminLogRepository.count(); }
}
