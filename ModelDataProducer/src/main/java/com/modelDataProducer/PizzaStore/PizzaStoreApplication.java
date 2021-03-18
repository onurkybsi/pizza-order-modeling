package com.modelDataProducer.PizzaStore;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.jmx.export.metadata.InvalidMetadataException;

@SpringBootApplication
public class PizzaStoreApplication {
	@Value("${MEDIATOR_BASE_URL}")
	private static String mediatorBaseURL;
	@Value("${MEDIATOR_HEALTH_CHECK_ENDPOINT}")
	private static String mediatorHealthCheckEndPoint;

	public static void main(String[] args) {
		boolean mediatorIsHealthy = checkMediatorHealth();
		if (!mediatorIsHealthy)
			throw new InvalidMetadataException("Couldn't connected to Mediator!");
		SpringApplication.run(PizzaStoreApplication.class, args);
	}

	public static boolean checkMediatorHealth() {
		boolean mediatorHealthy = false;

		// Hard code must be changed !
		String requestURL = "http://mediator:5000/api/health/checkHealth";

		HttpRequest healthCheckRequest = HttpRequest.newBuilder().uri(URI.create(requestURL)).build();
		try {
			HttpResponse<String> response = HttpClient.newHttpClient().send(healthCheckRequest,
					BodyHandlers.ofString());
			mediatorHealthy = response.statusCode() == HttpStatus.OK.value();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return mediatorHealthy;
	}
}
