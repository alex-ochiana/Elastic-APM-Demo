package com.oaa.elasticapm.webserver.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DummyRequestsService {
    
    private static final int DELAY_IN_MILLIS = 1000;
    private static final String BASE_URL = "http://localhost:8080/random/";
    private static final Logger logger = LoggerFactory.getLogger(DummyRequestsService.class);
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(20);

    @PostConstruct
    public void init() {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Executing requests with " + DELAY_IN_MILLIS + " millis delay between.");
        for (int i = 0; i < 20; i++) {
            executorService.scheduleWithFixedDelay(() -> {
                String response = restTemplate.getForEntity(BASE_URL + i, String.class).getBody();
                logger.info("/random/" + i + " - " + response);
            }, 120 * DELAY_IN_MILLIS, DELAY_IN_MILLIS, TimeUnit.MILLISECONDS);
        }
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdownNow();
    }
}
