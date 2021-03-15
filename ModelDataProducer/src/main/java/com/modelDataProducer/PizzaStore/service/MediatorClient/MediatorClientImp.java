package com.modelDataProducer.PizzaStore.service.MediatorClient;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.modelDataProducer.PizzaStore.model.Material;
import com.modelDataProducer.PizzaStore.model.MenuItem;
import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.OrderResult;
import com.modelDataProducer.PizzaStore.model.StoreMetaData;
import com.modelDataProducer.PizzaStore.model.RequestModel.GetMaterialsByIdsRequest;
import com.modelDataProducer.PizzaStore.model.RequestModel.GetMenuItemsByIdsRequest;
import com.modelDataProducer.PizzaStore.model.RequestModel.UpdateStoreBudgetRequest;
import com.modelDataProducer.PizzaStore.model.ResponseModel.UpdateStoreBudgetRespose;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class MediatorClientImp implements MediatorClient {
    @Value("${MEDIATOR_BASE_URL}")
    private String mediatorBaseURL;
    @Value("${GETMENU_ENDPOINT}")
    private String getMenuEndPoint;
    @Value("${GETMATERIALS_ENDPOINT}")
    private String getMaterialsEndPoint;
    @Value("${GET_MENUITEMS_BY_IDS_ENDPOINT}")
    private String getMenuItemsByIdsEndPoint;
    @Value("${GET_MATERIALS_BY_IDS_ENDPOINT}")
    private String getMaterialsByIdsEndPoint;
    @Value("${GET_STORE_BUDGET_ENDPOINT}")
    private String getStoreBudgetEndPoint;
    @Value("${UPDATE_STORE_BUDGET_ENDPOINT}")
    private String updateStoreBudgetEndPoint;

    private HttpClient client;

    private MediatorClientImp() {
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public List<MenuItem> getMenu() {
        ArrayList<MenuItem> menu = new ArrayList<>();

        HttpRequest mediatorGetMenuRequest = createGetMediatorRequest(getMenuEndPoint);

        try {
            HttpResponse<String> response = client.send(mediatorGetMenuRequest, BodyHandlers.ofString());

            menu = convertJsonStringToGivenTypeObject(new TypeToken<ArrayList<MenuItem>>() {
            }.getType(), response.body());
        } catch (Exception e) {
            // TODO: handle exception
        }

        return menu;
    }

    @Override
    public List<Material> getMaterials() {
        ArrayList<Material> materials = new ArrayList<>();

        HttpRequest mediatorGetMaterialsRequest = createGetMediatorRequest(getMaterialsEndPoint);

        try {
            HttpResponse<String> response = client.send(mediatorGetMaterialsRequest, BodyHandlers.ofString());

            materials = convertJsonStringToGivenTypeObject(new TypeToken<ArrayList<Material>>() {
            }.getType(), response.body());
        } catch (Exception e) {
            // TODO: handle exception
        }

        return materials;
    }

    private HttpRequest createGetMediatorRequest(String endpoint) {
        String requestURL = mediatorBaseURL + endpoint;

        return HttpRequest.newBuilder().uri(URI.create(requestURL)).header("Accept", "application/json").build();
    }

    @Override
    public List<MenuItem> getMenuItemsByIds(List<String> ids) {
        ArrayList<MenuItem> menu = new ArrayList<>();

        GetMenuItemsByIdsRequest requestBody = new GetMenuItemsByIdsRequest();
        requestBody.setMenuItemIds(ids);

        String requestURL = createGetMenuItemsByIdsEndpointURL(requestBody);

        HttpRequest mediatorGetMenuItemsByIdsRequest = HttpRequest.newBuilder().uri(URI.create(requestURL)).build();

        try {
            HttpResponse<String> response = client.send(mediatorGetMenuItemsByIdsRequest, BodyHandlers.ofString());

            menu = convertJsonStringToGivenTypeObject(new TypeToken<ArrayList<MenuItem>>() {
            }.getType(), response.body());
        } catch (Exception e) {
            // TODO: handle exception
        }

        return menu;
    }

    private String createGetMenuItemsByIdsEndpointURL(GetMenuItemsByIdsRequest requestBody) {
        String requestURL = mediatorBaseURL + getMenuItemsByIdsEndPoint;
        return UriComponentsBuilder.newInstance().fromHttpUrl(requestURL)
                .queryParam("menuItemIds", requestBody.getMenuItemIds()).build().toString();
    }

    @Override
    public List<Material> getMaterialsByIds(List<String> ids) {
        ArrayList<Material> materials = new ArrayList<>();

        GetMaterialsByIdsRequest requestBody = new GetMaterialsByIdsRequest();
        requestBody.setMaterialsIds(ids);

        String requestURL = createGetMaterialsByIdsEndpointURL(requestBody);

        HttpRequest mediatorGetMaterialsByIdsRequest = HttpRequest.newBuilder().uri(URI.create(requestURL)).build();

        try {
            HttpResponse<String> response = client.send(mediatorGetMaterialsByIdsRequest, BodyHandlers.ofString());

            materials = convertJsonStringToGivenTypeObject(new TypeToken<ArrayList<Material>>() {
            }.getType(), response.body());
        } catch (Exception e) {
            // TODO: handle exception
        }

        return materials;
    }

    private String createGetMaterialsByIdsEndpointURL(GetMaterialsByIdsRequest requestBody) {
        String requestURL = mediatorBaseURL + getMaterialsByIdsEndPoint;
        return UriComponentsBuilder.newInstance().fromHttpUrl(requestURL)
                .queryParam("materialsIds", requestBody.getMaterialsIds()).build().toString();
    }

    @Override
    public StoreMetaData getStoreBudget() {
        StoreMetaData budgetMetaData = new StoreMetaData();

        HttpRequest getStoreBudgetRequest = createGetMediatorRequest(getStoreBudgetEndPoint);

        try {
            HttpResponse<String> response = client.send(getStoreBudgetRequest, BodyHandlers.ofString());

            budgetMetaData = convertJsonStringToGivenTypeObject(new TypeToken<StoreMetaData>() {
            }.getType(), response.body());
        } catch (Exception e) {
            // TODO: handle exception
        }

        return budgetMetaData;
    }

    private <T> T convertJsonStringToGivenTypeObject(Type type, String jsonString) {
        return new Gson().fromJson(jsonString, type);
    }

    @Override
    public UpdateStoreBudgetRespose updateStoreBudget(UpdateStoreBudgetRequest request) {
        UpdateStoreBudgetRespose updateStoreBudgetResponse = new UpdateStoreBudgetRespose();

        HttpRequest updateStoreBudgRequest = createMediatorPostRequest(request, updateStoreBudgetEndPoint);

        try {
            HttpResponse<String> response = client.send(updateStoreBudgRequest, BodyHandlers.ofString());
            updateStoreBudgetResponse = convertJsonStringToGivenTypeObject(new TypeToken<UpdateStoreBudgetRespose>() {
            }.getType(), response.body());
        } catch (Exception e) {
            // TODO: handle exception
        }

        return updateStoreBudgetResponse;
    }

    private <T> HttpRequest createMediatorPostRequest(T requestBody, String endpoint) {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBodyString = "";

        try {
            objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            // TODO: handle exception
        }

        String requestedUrl = mediatorBaseURL + endpoint;

        return HttpRequest.newBuilder().uri(URI.create(requestedUrl))
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyString)).build();
    }

    @Override
    public OrderResult createOrder(Order order) {
        // TODO Auto-generated method stub
        return null;
    }
}
