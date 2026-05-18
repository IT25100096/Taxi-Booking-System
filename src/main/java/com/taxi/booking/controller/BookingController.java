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
    private BookingRepository bookingRepository;

    @GetMapping("/booking-book")
    public String showForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "booking-book";
    }

    @PostMapping("/book")
    public String saveBooking(@ModelAttribute Booking booking) {
        booking.setFare(booking.getRideType().equals("instant") ? 250.0 : 300.0);
        bookingRepository.save(booking);
        return "redirect:/booking-history";
    }

    @GetMapping("/booking-history")
    public String showHistory(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        return "booking-history";
    }

    @GetMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "redirect:/booking-history";
    }
}
