package org.example.taxiadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "org.example.taxiadmin",
        "com.taxi",
        "com.taxi.booking",
        "com.taxiapp",
        "com.TaxiBookingSystem.demo"
})
@EntityScan(basePackages = {
        "org.example.taxiadmin.model",
        "com.taxi.model",
        "com.taxi.booking.model"
})
@EnableJpaRepositories(basePackages = {
        "org.example.taxiadmin.repository",
        "com.taxi.repository",
        "com.taxi.booking.repository"
})
public class TaxiAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxiAdminApplication.class, args);
    }

}
