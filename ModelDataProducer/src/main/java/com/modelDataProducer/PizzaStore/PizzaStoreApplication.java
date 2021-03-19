package com.modelDataProducer.PizzaStore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PizzaStoreApplication {
	@Value("${MEDIATOR_BASE_URL}")
	private static String mediatorBaseURL;
	@Value("${MEDIATOR_HEALTH_CHECK_ENDPOINT}")
	private static String mediatorHealthCheckEndPoint;

	public static void main(String[] args) {
		SpringApplication.run(PizzaStoreApplication.class, args);
	}
}
