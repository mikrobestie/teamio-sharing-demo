package com.almacareer.teamio.sharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TeamioSharingApplication {

    public static void main(String[] args) {
        new SpringApplication(TeamioSharingApplication.class).run(args);
    }
}