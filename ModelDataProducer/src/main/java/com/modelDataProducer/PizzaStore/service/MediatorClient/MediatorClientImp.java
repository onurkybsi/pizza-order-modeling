package com.modelDataProducer.PizzaStore.service.MediatorClient;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.modelDataProducer.PizzaStore.model.Material;
import com.modelDataProducer.PizzaStore.model.MenuItem;
import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.StoreMetaData;
import com.modelDataProducer.PizzaStore.model.RequestModel.GetMaterialsByIdsRequest;
import com.modelDataProducer.PizzaStore.model.RequestModel.GetMenuItemsByIdsRequest;
import com.modelDataProducer.PizzaStore.model.RequestModel.StoreOrderDataRequest;
import com.modelDataProducer.PizzaStore.model.RequestModel.UpdateMaterialsQuantitiesRequest;
import com.modelDataProducer.PizzaStore.model.RequestModel.UpdateStoreBudgetRequest;
import com.modelDataProducer.PizzaStore.model.ResponseModel.StoreOrderDataResponse;
import com.modelDataProducer.PizzaStore.model.ResponseModel.UpdateMaterialsQuantitiesResponse;
import com.modelDataProducer.PizzaStore.model.ResponseModel.UpdateStoreBudgetResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class MediatorClientImp implements MediatorClient{
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
    @Value("${UPDATE_MATERIALS_QUANTITIES_ENDPOINT}")
    private String updateMaterialsQuantitiesEndPoint;
    @Value("${STORE_ORDER_DATA_ENDPOINT}")
    private String storeOrderDataEndPoint;

    HttpClient  httpClient;
    Gson gson;

    public MediatorClientImp() {
        this.httpClient = HttpClientBuilder.create().build();
        this.gson = new Gson();
    }

    @Override
    public List<MenuItem> getMenu() {
        ArrayList<MenuItem> menu = new ArrayList<MenuItem>();

        HttpGet getRequest = new HttpGet(mediatorBaseURL+getMenuEndPoint);

        try {
            String resbody = httpClient.execute(getRequest, new BasicResponseHandler());

            menu = gson.fromJson(resbody, (new TypeToken<ArrayList<MenuItem>>() {
            }.getType()));
        } catch (Exception e) {
            //TODO: handle exception
        }
        return menu == null ? new ArrayList<MenuItem>() : menu;
    }

    @Override
    public List<MenuItem> getMenuItemsByIds(List<String> ids) {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        String requestURL = createGetMenuItemsByIdsEndpointURL(new GetMenuItemsByIdsRequest(ids));
        HttpGet getRequest = new HttpGet(requestURL);

        try {
            String resbody = httpClient.execute(getRequest, new BasicResponseHandler());

            menuItems = gson.fromJson(resbody, (new TypeToken<ArrayList<MenuItem>>() {
            }.getType()));
        } catch (Exception e) {
            //TODO: handle exception
        }
        return menuItems == null ? new ArrayList<MenuItem>() : menuItems;
    }

    private String createGetMenuItemsByIdsEndpointURL(GetMenuItemsByIdsRequest requestBody) {
        String requestURL = mediatorBaseURL + getMenuItemsByIdsEndPoint;
        return UriComponentsBuilder.newInstance().fromHttpUrl(requestURL)
                .queryParam("menuItemIds", requestBody.getMenuItemIds()).build().toString();
    }

    @Override
    public List<Material> getMaterialsByIds(List<String> ids) {
        ArrayList<Material> materials = new ArrayList<>();

        String requestURL = createGetMaterialsByIdsEndpointURL(new GetMaterialsByIdsRequest(ids));
        HttpGet getRequest = new HttpGet(requestURL);

        try {
            String resbody = httpClient.execute(getRequest, new BasicResponseHandler());

            materials = gson.fromJson(resbody, (new TypeToken<ArrayList<Material>>() {
            }.getType()));
        } catch (Exception e) {
            //TODO: handle exception
        }
        return materials == null ? new ArrayList<Material>() : materials;
    }

    private String createGetMaterialsByIdsEndpointURL(GetMaterialsByIdsRequest requestBody) {
        String requestURL = mediatorBaseURL + getMaterialsByIdsEndPoint;
        return UriComponentsBuilder.newInstance().fromHttpUrl(requestURL)
                .queryParam("materialsIds", requestBody.getMaterialsIds()).build().toString();
    }

    @Override
    public StoreMetaData getStoreBudget() {
        StoreMetaData budgetData = new StoreMetaData();

        HttpGet getRequest = new HttpGet(mediatorBaseURL+getStoreBudgetEndPoint);
        
        try {
            String resbody = httpClient.execute(getRequest, new BasicResponseHandler());

            budgetData = gson.fromJson(resbody, (new TypeToken<StoreMetaData>() {
            }.getType()));
        } catch (Exception e) {
            //TODO: handle exception
        }
        return budgetData;
    }

    @Override
    public UpdateStoreBudgetResponse updateStoreBudget(UpdateStoreBudgetRequest request) {
        UpdateStoreBudgetResponse updateStoreBudgetResponse = new UpdateStoreBudgetResponse();

        HttpPost postRequest = new HttpPost(mediatorBaseURL+updateStoreBudgetEndPoint);

        try {
            StringEntity postingString = new StringEntity(gson.toJson(request));
            postRequest.setEntity(postingString);
            postRequest.setHeader("Content-type", "application/json");

            String resbody = httpClient.execute(postRequest, new BasicResponseHandler());
            updateStoreBudgetResponse = gson.fromJson(resbody, (new TypeToken<UpdateStoreBudgetResponse>() {
            }.getType()));
        } catch (Exception e) {
            //TODO: handle exception
        }
        return updateStoreBudgetResponse;
    }

    @Override
    public UpdateMaterialsQuantitiesResponse updateMaterialsQuantities(UpdateMaterialsQuantitiesRequest request) {
        UpdateMaterialsQuantitiesResponse updateMaterialsQuantitiesResponse = new UpdateMaterialsQuantitiesResponse();

        HttpPost postRequest = new HttpPost(mediatorBaseURL+updateMaterialsQuantitiesEndPoint);

        try {
            StringEntity postingString = new StringEntity(gson.toJson(request));
            postRequest.setEntity(postingString);
            postRequest.setHeader("Content-type", "application/json");

            String resbody = httpClient.execute(postRequest, new BasicResponseHandler());
            updateMaterialsQuantitiesResponse = gson.fromJson(resbody, (new TypeToken<UpdateMaterialsQuantitiesResponse>() {
            }.getType()));
        } catch (Exception e) {
            //TODO: handle exception
        }
        return updateMaterialsQuantitiesResponse;
    }

    @Override
    public StoreOrderDataResponse storeOrderData(Order order) {
        StoreOrderDataResponse storeOrderDataResponse = new StoreOrderDataResponse();

        HttpPost postRequest = new HttpPost(mediatorBaseURL+storeOrderDataEndPoint);

        try {
            StringEntity postingString = new StringEntity(gson.toJson(new StoreOrderDataRequest(order)));
            postRequest.setEntity(postingString);
            postRequest.setHeader("Content-type", "application/json");

            String resbody = httpClient.execute(postRequest, new BasicResponseHandler());
            storeOrderDataResponse = gson.fromJson(resbody, (new TypeToken<StoreOrderDataResponse>() {
            }.getType()));
        } catch (Exception e) {
            //TODO: handle exception
        }
        return storeOrderDataResponse;
    }
}
