package com.taxiapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentAliasController {

    @GetMapping("/payment-summary")
    public String paymentSummary() {
        return "redirect:/payments/new";
    }

    @GetMapping("/payment-history")
    public String paymentHistory() {
        return "redirect:/payments/history";
    }

    @GetMapping("/payment-invoice")
    public String paymentInvoice() {
        return "redirect:/payments/history";
    }
}
