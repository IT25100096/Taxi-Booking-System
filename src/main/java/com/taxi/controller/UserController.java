package com.taxi.controller;

import com.taxi.model.Driver;
import com.taxi.model.Passenger;
import com.taxi.model.User;
import com.taxi.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/start")
    public String showHomePage() {
        return "redirect:/user-login";
    }

    @GetMapping({"/login", "/user-login"})
    public String showLoginPage() {
        return "user-login";
    }

    @GetMapping({"/register", "/user-register"})
    public String showRegisterPage() {
        return "user-register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String role,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String phone,
            @RequestParam(required = false) String homeAddress,
            @RequestParam(required = false) String licenseNumber,
            @RequestParam(required = false) String vehiclePlate,
            @RequestParam(required = false) String vehicleModel,
            @RequestParam(required = false) String vehicleColor,
            @RequestParam(required = false) Integer vehicleYear,
            Model model) {

        try {
            if (role.equals("PASSENGER")) {
                userService.registerPassenger(fullName, email, password, phone, homeAddress);
            } else if (role.equals("DRIVER")) {
                userService.registerDriver(fullName, email, password, phone,
                        licenseNumber, vehiclePlate, vehicleModel, vehicleColor, vehicleYear);
            }
            model.addAttribute("success", "Account created! Please login.");
            return "user-login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user-register";
        }
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        try {
            User user = userService.login(email, password);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("role", user.getRole());
            session.setAttribute("userName", user.getFullName());

            if (user instanceof Driver) {
                return "redirect:/driver/profile";
            } else {
                return "redirect:/passenger/profile";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user-login";
        }
    }

    @GetMapping({"/passenger/profile", "/passenger-profile"})
    public String showPassengerProfile(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/user-login";
        try {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);
            return "passenger-profile";
        } catch (Exception e) {
            return "redirect:/user-login";
        }
    }

    @GetMapping({"/driver/profile", "/driver-profile"})
    public String showDriverProfile(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/user-login";
        try {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);
            return "driver-profile";
        } catch (Exception e) {
            return "redirect:/user-login";
        }
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam String fullName,
            @RequestParam String phone,
            HttpSession session) {

        String userId = (String) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");
        try {
            userService.updateProfile(userId, fullName, phone);
            if ("DRIVER".equals(role)) {
                return "redirect:/driver/profile";
            } else {
                return "redirect:/passenger/profile";
            }
        } catch (Exception e) {
            return "redirect:/user-login";
        }
    }

    @PostMapping("/account/delete")
    public String deleteAccount(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        try {
            userService.deleteAccount(userId);
            session.invalidate();
            return "redirect:/user-login";
        } catch (Exception e) {
            return "redirect:/user-login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user-login";
    }
}
