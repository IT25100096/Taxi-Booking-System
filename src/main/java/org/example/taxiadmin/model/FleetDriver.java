package org.example.taxiadmin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "drivers")
public class FleetDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone;
    private String licenseNumber;
    private String status;

    public FleetDriver() {}

    public FleetDriver(String name, String phone, String licenseNumber, String status) {
        this.name = name;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
