package ru.kolpakovee.neweasyway;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class NewEasyWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewEasyWayApplication.class, args);
    }
}
