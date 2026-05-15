package com.taxiapp.controller;

import com.taxiapp.model.CashPayment;
import com.taxiapp.model.OnlinePayment;
import com.taxiapp.model.Payment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private Connection getConn() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/taxidb", "root", "DileepaK@2003");
    }

    @GetMapping("/new")
    public String showForm() {
        return "payment-summary";
    }

    @PostMapping("/create")
    public String createPayment(@RequestParam String tripId,
                                @RequestParam String passengerId,
                                @RequestParam double distanceKm,
                                @RequestParam String paymentType,
                                @RequestParam(required = false) String promoCode)
            throws Exception {
        String paymentId = "PAY" + System.currentTimeMillis();
        Payment payment;

        if ("ONLINE".equals(paymentType)) {
            String txnRef = "TXN" + System.currentTimeMillis();
            payment = new OnlinePayment(paymentId, tripId, passengerId, distanceKm, txnRef);
        } else {
            payment = new CashPayment(paymentId, tripId, passengerId, distanceKm);
        }

        if (promoCode != null && !promoCode.isEmpty()) {
            payment.applyDiscount(promoCode);
        }

        Connection conn = getConn();
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO payments (payment_id, trip_id, passenger_id, distance_km, " +
                        "promo_code, discount_amount, final_amount, payment_type, payment_status, created_at) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,NOW())");
        ps.setString(1, payment.getPaymentId());
        ps.setString(2, payment.getTripId());
        ps.setString(3, payment.getPassengerId());
        ps.setDouble(4, payment.getDistanceKm());
        ps.setString(5, payment.getPromoCode());
        ps.setDouble(6, payment.getDiscountAmount());
        ps.setDouble(7, payment.getFinalAmount());
        ps.setString(8, payment.getPaymentType());
        ps.setString(9, "COMPLETED");
        ps.executeUpdate();
        conn.close();

        return "redirect:/payments/invoice/" + paymentId;
    }

    @GetMapping("/history")
    public String viewHistory(Model model) throws Exception {
        List<Map<String, Object>> payments = new ArrayList<>();
        Connection conn = getConn();
        ResultSet rs = conn.createStatement().executeQuery(
                "SELECT * FROM payments ORDER BY created_at DESC");
        while (rs.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("paymentId",   rs.getString("payment_id"));
            row.put("tripId",      rs.getString("trip_id"));
            row.put("passengerId", rs.getString("passenger_id"));
            row.put("distanceKm",  rs.getDouble("distance_km"));
            row.put("promoCode",   rs.getString("promo_code"));
            row.put("discount",    rs.getDouble("discount_amount"));
            row.put("finalAmount", rs.getDouble("final_amount"));
            row.put("type",        rs.getString("payment_type"));
            row.put("status",      rs.getString("payment_status"));
            row.put("createdAt",   rs.getString("created_at"));
            payments.add(row);
        }
        conn.close();
        model.addAttribute("payments", payments);
        return "payment-history";
    }

    @GetMapping("/invoice/{id}")
    public String viewInvoice(@PathVariable String id, Model model) throws Exception {
        Connection conn = getConn();
        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM payments WHERE payment_id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            model.addAttribute("paymentId",  rs.getString("payment_id"));
            model.addAttribute("tripId",     rs.getString("trip_id"));
            model.addAttribute("passengerId",rs.getString("passenger_id"));
            model.addAttribute("distanceKm", rs.getDouble("distance_km"));
            model.addAttribute("promoCode",  rs.getString("promo_code"));
            model.addAttribute("discount",   rs.getDouble("discount_amount"));
            model.addAttribute("total",      rs.getDouble("final_amount"));
            model.addAttribute("type",       rs.getString("payment_type"));
            model.addAttribute("status",     rs.getString("payment_status"));
            model.addAttribute("createdAt",  rs.getString("created_at"));
        }
        conn.close();
        return "invoice";
    }

    @PostMapping("/update")
    public String updatePayment(@RequestParam String paymentId,
                                @RequestParam String promoCode,
                                @RequestParam double newFare,
                                @RequestParam double discount) throws Exception {
        Connection conn = getConn();
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE payments SET promo_code=?, discount_amount=?, final_amount=? WHERE payment_id=?");
        ps.setString(1, promoCode);
        ps.setDouble(2, discount);
        ps.setDouble(3, newFare);
        ps.setString(4, paymentId);
        ps.executeUpdate();
        conn.close();
        return "redirect:/payments/history";
    }

    @PostMapping("/delete")
    public String deletePayment(@RequestParam String paymentId) throws Exception {
        Connection conn = getConn();
        PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM payments WHERE payment_id = ?");
        ps.setString(1, paymentId);
        ps.executeUpdate();
        conn.close();
        return "redirect:/payments/history";
    }
}