package com.taxi.service;

import com.taxi.model.Driver;
import com.taxi.model.Passenger;
import com.taxi.model.User;
import com.taxi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ── REGISTER PASSENGER ──────────────────────
    public Passenger registerPassenger(String fullName, String email,
                                       String password, String phone,
                                       String homeAddress) throws Exception {
        if (userRepository.existsByEmail(email)) {
            throw new Exception("Email already registered!");
        }

        Passenger passenger = new Passenger(
                UUID.randomUUID().toString(),
                fullName, email, password,
                phone, homeAddress
        );

        return (Passenger) userRepository.save(passenger);
    }

    // ── REGISTER DRIVER ─────────────────────────
    public Driver registerDriver(String fullName, String email,
                                 String password, String phone,
                                 String licenseNumber, String vehiclePlate,
                                 String vehicleModel, String vehicleColor,
                                 Integer vehicleYear) throws Exception {
        if (userRepository.existsByEmail(email)) {
            throw new Exception("Email already registered!");
        }

        Driver driver = new Driver(
                UUID.randomUUID().toString(),
                fullName, email, password, phone,
                licenseNumber, vehiclePlate,
                vehicleModel, vehicleColor, vehicleYear
        );

        return (Driver) userRepository.save(driver);
    }

    // ── LOGIN ────────────────────────────────────
    public User login(String email, String password) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new Exception("No account found with this email!");
        }

        User user = userOpt.get();

        if (!user.isActive()) {
            throw new Exception("This account has been deactivated!");
        }

        if (!user.getPasswordHash().equals(password)) {
            throw new Exception("Incorrect password!");
        }

        return user;
    }

    // ── GET USER BY ID ───────────────────────────
    public User getUserById(String userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found!"));
    }

    // ── UPDATE PROFILE ───────────────────────────
    public User updateProfile(String userId, String fullName,
                              String phone) throws Exception {
        User user = getUserById(userId);
        user.setFullName(fullName);
        user.setPhone(phone);
        return userRepository.save(user);
    }

    // ── UPDATE PASSENGER DETAILS ─────────────────
    public Passenger updatePassengerDetails(String userId,
                                            String homeAddress,
                                            String paymentMethod) throws Exception {
        User user = getUserById(userId);

        if (!(user instanceof Passenger passenger)) {
            throw new Exception("User is not a passenger!");
        }

        passenger.setHomeAddress(homeAddress);
        passenger.setPaymentMethod(paymentMethod);
        return (Passenger) userRepository.save(passenger);
    }

    // ── UPDATE DRIVER DETAILS ────────────────────
    public Driver updateDriverDetails(String userId, String vehiclePlate,
                                      String vehicleModel, String vehicleColor,
                                      Integer vehicleYear) throws Exception {
        User user = getUserById(userId);

        if (!(user instanceof Driver driver)) {
            throw new Exception("User is not a driver!");
        }

        driver.setVehiclePlate(vehiclePlate);
        driver.setVehicleModel(vehicleModel);
        driver.setVehicleColor(vehicleColor);
        driver.setVehicleYear(vehicleYear);
        return (Driver) userRepository.save(driver);
    }

    // ── DELETE ACCOUNT ───────────────────────────
    public void deleteAccount(String userId) throws Exception {
        User user = getUserById(userId);
        user.setActive(false);
        userRepository.save(user);
    }
}