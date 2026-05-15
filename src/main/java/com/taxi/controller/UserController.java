package com.taxi.controller;

import com.taxi.model.Driver;
import com.taxi.model.Passenger;
import com.taxi.model.User;
import com.taxi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // ── SHOW LOGIN PAGE ──────────────────────────
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // ── SHOW REGISTER PAGE ───────────────────────
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // ── SHOW HOME PAGE ───────────────────────────
    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/login";
    }

    // ── HANDLE REGISTER ──────────────────────────
    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String role,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String phone,
            // Passenger fields
            @RequestParam(required = false) String homeAddress,
            // Driver fields
            @RequestParam(required = false) String licenseNumber,
            @RequestParam(required = false) String vehiclePlate,
            @RequestParam(required = false) String vehicleModel,
            @RequestParam(required = false) String vehicleColor,
            @RequestParam(required = false) Integer vehicleYear,
            Model model) {

        try {
            if (role.equals("PASSENGER")) {
                userService.registerPassenger(
                        fullName, email, password,
                        phone, homeAddress
                );
            } else if (role.equals("DRIVER")) {
                userService.registerDriver(
                        fullName, email, password, phone,
                        licenseNumber, vehiclePlate,
                        vehicleModel, vehicleColor, vehicleYear
                );
            }
            // Registration successful
            model.addAttribute("success",
                    "Account created! Please login.");
            return "login";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    // ── HANDLE LOGIN ─────────────────────────────
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        try {
            // This is polymorphic login routing
            User user = userService.login(email, password);

            // Save user in session
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("role", user.getRole());
            session.setAttribute("userName", user.getFullName());

            // Route to correct dashboard based on role
            if (user instanceof Driver) {
                return "redirect:/driver/profile";
            } else {
                return "redirect:/passenger/profile";
            }

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    // ── SHOW PASSENGER PROFILE ───────────────────
    @GetMapping("/passenger/profile")
    public String showPassengerProfile(HttpSession session,
                                       Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        try {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);
            return "passenger-profile";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    // ── SHOW DRIVER PROFILE ──────────────────────
    @GetMapping("/driver/profile")
    public String showDriverProfile(HttpSession session,
                                    Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        try {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);
            return "driver-profile";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    // ── UPDATE PROFILE ───────────────────────────
    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam String fullName,
            @RequestParam String phone,
            HttpSession session,
            Model model) {

        String userId = (String) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        try {
            userService.updateProfile(userId, fullName, phone);
            model.addAttribute("success", "Profile updated!");

            if ("DRIVER".equals(role)) {
                return "redirect:/driver/profile";
            } else {
                return "redirect:/passenger/profile";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/login";
        }
    }

    // ── DELETE ACCOUNT ───────────────────────────
    @PostMapping("/account/delete")
    public String deleteAccount(HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        try {
            userService.deleteAccount(userId);
            session.invalidate();
            return "redirect:/login";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    // ── LOGOUT ───────────────────────────────────
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
