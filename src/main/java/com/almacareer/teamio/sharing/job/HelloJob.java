package com.almacareer.teamio.sharing.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * A simple job that logs a message, to demonstrate the Intellij IDEA 2024.2 @Scheduled method support improvements.
 */
@Component
@Slf4j
public class HelloJob {

    @Scheduled(initialDelay = 1000)
    void run() {
        LOGGER.info("Hello from the job {}, my fellow {}!", getClass().getCanonicalName(), "developer");
        System.out.println("Hello from the job " + getClass().getCanonicalName() + ", my fellow developer!");
    }
}
