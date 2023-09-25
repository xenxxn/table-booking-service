package com.xenxxn.tablebooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TableBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TableBookingApplication.class, args);
    }

}
