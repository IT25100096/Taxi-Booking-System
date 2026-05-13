package com.taxiapp.model;

public abstract class Payment {

    private String paymentId;
    private String tripId;
    private String passengerId;
    private double distanceKm;
    private String promoCode;
    private double discountAmount;
    private double finalAmount;
    private String paymentStatus;
    private String paymentType;

    public Payment(String paymentId, String tripId,
                   String passengerId, double distanceKm) {
        this.paymentId = paymentId;
        this.tripId = tripId;
        this.passengerId = passengerId;
        this.distanceKm = distanceKm;
        this.finalAmount = calculateFare();
        this.paymentStatus = "PENDING";
    }

    public abstract double calculateFare();

    public void applyDiscount(String promoCode) {
        this.promoCode = promoCode;
        double rate = 0;
        if ("TAXI10".equals(promoCode)) rate = 0.10;
        if ("SAVE20".equals(promoCode)) rate = 0.20;
        if ("FIRST50".equals(promoCode)) rate = 0.50;
        this.discountAmount = finalAmount * rate;
        this.finalAmount = finalAmount - discountAmount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getTripId() {
        return tripId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setFinalAmount(double f) {
        this.finalAmount = f;
    }

    public void setDiscountAmount(double d) {
        this.discountAmount = d;
    }

    public void setPromoCode(String p) {
        this.promoCode = p;
    }

    public void setPaymentStatus(String s) {
        this.paymentStatus = s;
    }

    public void setPaymentType(String t) {
        this.paymentType = t;
    }
}