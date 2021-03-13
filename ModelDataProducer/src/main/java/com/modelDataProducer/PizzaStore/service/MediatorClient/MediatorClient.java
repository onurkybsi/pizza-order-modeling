package com.modelDataProducer.PizzaStore.service.MediatorClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelDataProducer.PizzaStore.model.MenuItem;

public class MediatorClient implements IMediatorClient {
    // TO-DO: change with env variable
    private static final String MEDIATOR_BASE_URL = "http://localhost:5000";

    private static MediatorClient instance = null;

    public static MediatorClient createMediatorClient() {
        if (instance == null) {
            instance = new MediatorClient(HttpClient.newHttpClient());
        }
        return instance;
    }

    private HttpClient client;

    private MediatorClient(HttpClient client) {
        this.client = client;
    }

    @Override
    public Set<MenuItem> getMenu() {
        HttpRequest mediatorGetMenuRequest = createMediatorGetMenuRequest("/api/pizzastore/getmenu");

        HttpResponse<String> response = client.send(mediatorGetMenuRequest, BodyHandlers.ofString());

        return null;
    }

    private HttpRequest createMediatorGetMenuRequest(String endpoint) {
        String requestURL = MEDIATOR_BASE_URL + endpoint;

        return HttpRequest.newBuilder().uri(URI.create(requestURL)).header("Accept", "application/json").build();
    }
}
