package com.taxiapp.model;

public class OnlinePayment extends Payment {

    private static final double BASE = 80.0;
    private static final double RATE = 45.0;
    private String transactionRef;

    public OnlinePayment(String paymentId, String tripId,
                         String passengerId, double distanceKm,
                         String transactionRef) {
        super(paymentId, tripId, passengerId, distanceKm);
        this.transactionRef = transactionRef;
        setPaymentType("ONLINE");
    }

    @Override
    public double calculateFare() {
        return BASE + (getDistanceKm() * RATE);
    }

    public String getTransactionRef() {
        return transactionRef;
    }
}