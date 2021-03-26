package com.oaa.elasticapm.webserver.controller;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class DummyEndpointsController {

	private final static Logger logger = LoggerFactory.getLogger(DummyEndpointsController.class);

    @GetMapping("/random/{id}")
	public DeferredResult<ResponseEntity<String>> random(@PathVariable(value="id") String id) {
		return waitAndGetResponse();
	}

	public DeferredResult<ResponseEntity<String>> waitAndGetResponse() {
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		ForkJoinPool.commonPool().submit(() -> {
			long waitTime = randomDelay();
			result.setResult(ResponseEntity.ok("Waited " + waitTime + " seconds"));
		});
		return result;
	}

	private long randomDelay() {
		long waitTime = ThreadLocalRandom.current().nextLong(9) + 1;
		try {
			  Thread.sleep(waitTime * 1000);
		} catch (InterruptedException e) {
			logger.error("Thread interrupted", e);
			Thread.currentThread().interrupt();
		}
		return waitTime;
  	}
}
