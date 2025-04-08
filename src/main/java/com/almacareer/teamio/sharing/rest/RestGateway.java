package com.almacareer.teamio.sharing.rest;

import com.almacareer.teamio.sharing.config.ISlowApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Gateway for testing virtual threads in REST services.
 */
@Slf4j
@RestController
@RequestMapping("/api/gateway")
@RequiredArgsConstructor
public class RestGateway {

    private final ISlowApi iSlowApi;
    private final AtomicInteger counter = new AtomicInteger(0);
    private final AtomicInteger maxMemory = new AtomicInteger(0);

    /**
     * A simple API that recalls a slow service.
     *
     * @param seconds The number of seconds to wait.
     * @return A message indicating how long the request took to process.
     */
    @RequestMapping("/slow/{seconds}")
    public String slowApi(@PathVariable int seconds) {
        int current = counter.incrementAndGet();
        int threadCount = ManagementFactory.getThreadMXBean().getThreadCount();
        int mem = maxMemory.updateAndGet(m -> Math.max(m, (int) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())));

        LOGGER.info("Request {} received, {} threads active, {} max used memory", current, threadCount, mem);

        return iSlowApi.slow(seconds);
    }
}
