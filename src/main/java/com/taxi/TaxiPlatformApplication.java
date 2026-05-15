package com.taxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class TaxiPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxiPlatformApplication.class, args);
    }
}

// This opens the browser ONLY after Spring Boot fully starts
@Component
class BrowserLauncher {

    @EventListener(ApplicationReadyEvent.class)
    public void openBrowser() {
        try {
            // Wait a moment to make sure everything is ready
            Thread.sleep(1000);

            String url = "http://localhost:8080/login";

            // Works on Windows
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(new String[]{
                    "cmd", "/c", "start", url
            });

            System.out.println("Browser opened at: " + url);

        } catch (Exception e) {
            System.out.println("Could not open browser: " + e.getMessage());
        }
    }
}
