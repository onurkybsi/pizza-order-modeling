package com.modelDataProducer.PizzaStore.service.MediatorClient;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.modelDataProducer.PizzaStore.model.Material;
import com.modelDataProducer.PizzaStore.model.MenuItem;
import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.OrderResult;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MediatorClientImp implements MediatorClient {
    @Value("${MEDIATOR_BASE_URL}")
    private String mediatorBaseURL;

    private static final String GETMENU_ENDPOINT = "/pizzastore/getmenu";
    private static final String GETMATERIALS_ENDPOINT = "/pizzastore/getmaterials";

    private HttpClient client;

    private MediatorClientImp() {
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public List<MenuItem> getMenu() {
        ArrayList<MenuItem> menu = new ArrayList<>();

        HttpRequest mediatorGetMenuRequest = createMediatorRequest(GETMENU_ENDPOINT);

        try {
            HttpResponse<String> response = client.send(mediatorGetMenuRequest, BodyHandlers.ofString());

            menu = convertGetMenuResponseBodyToArrayListMenuItem(response.body());
        } catch (Exception e) {
        }

        return menu;
    }

    private ArrayList<MenuItem> convertGetMenuResponseBodyToArrayListMenuItem(String responseBody) {
        Gson gson = new Gson();
        Type menuType = new TypeToken<ArrayList<MenuItem>>() {
        }.getType();

        return gson.fromJson(responseBody, menuType);
    }

    @Override
    public List<Material> getMaterials() {
        ArrayList<Material> materials = new ArrayList<>();

        HttpRequest mediatorGetMaterialsRequest = createMediatorRequest(GETMATERIALS_ENDPOINT);

        try {
            HttpResponse<String> response = client.send(mediatorGetMaterialsRequest, BodyHandlers.ofString());

            materials = convertGetMaterialResponseBodyToArrayListMaterial(response.body());
        } catch (Exception e) {
        }

        return materials;
    }

    private HttpRequest createMediatorRequest(String endpoint) {
        String requestURL = mediatorBaseURL + endpoint;

        return HttpRequest.newBuilder().uri(URI.create(requestURL)).header("Accept", "application/json").build();
    }

    private ArrayList<Material> convertGetMaterialResponseBodyToArrayListMaterial(String responseBody) {
        Gson gson = new Gson();
        Type materialType = new TypeToken<ArrayList<Material>>() {
        }.getType();

        return gson.fromJson(responseBody, materialType);
    }

    @Override
    public OrderResult createOrder(Order order) {
        // TODO Auto-generated method stub
        return null;
    }
}
