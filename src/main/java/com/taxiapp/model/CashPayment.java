package com.taxiapp.model;

public class CashPayment extends Payment {

    private static final double BASE = 100.0;
    private static final double RATE = 50.0;

    public CashPayment(String paymentId, String tripId,
                       String passengerId, double distanceKm) {
        super(paymentId, tripId, passengerId, distanceKm);
        setPaymentType("CASH");
    }

    @Override
    public double calculateFare() {
        return BASE + (getDistanceKm() * RATE);
    }
}