package com.taxi.booking.controller;

import com.taxi.booking.model.Booking;
import com.taxi.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookingController {

    @Autowired
    BookingRepository repo;

    @GetMapping("/book")
    public String showForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "book";
    }

    @PostMapping("/book")
    public String saveBooking(@ModelAttribute Booking booking) {
        booking.setFare(booking.getRideType()
                .equals("instant") ? 250.0 : 300.0);
        repo.save(booking);
        return "redirect:/history";
    }

    @GetMapping("/history")
    public String showHistory(Model model) {
        model.addAttribute("bookings", repo.findAll());
        return "history";
    }

    @GetMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/history";
    }
}