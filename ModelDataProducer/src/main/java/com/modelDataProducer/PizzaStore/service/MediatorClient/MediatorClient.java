package com.modelDataProducer.PizzaStore.service.MediatorClient;

import java.util.List;

import com.modelDataProducer.PizzaStore.model.Material;
import com.modelDataProducer.PizzaStore.model.MenuItem;
import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.StoreMetaData;
import com.modelDataProducer.PizzaStore.model.RequestModel.UpdateMaterialsQuantitiesRequest;
import com.modelDataProducer.PizzaStore.model.RequestModel.UpdateStoreBudgetRequest;
import com.modelDataProducer.PizzaStore.model.ResponseModel.StoreOrderDataResponse;
import com.modelDataProducer.PizzaStore.model.ResponseModel.UpdateMaterialsQuantitiesResponse;
import com.modelDataProducer.PizzaStore.model.ResponseModel.UpdateStoreBudgetResponse;

public interface MediatorClient {
    List<MenuItem> getMenu();

    List<MenuItem> getMenuItemsByIds(List<String> ids);

    List<Material> getMaterialsByIds(List<String> ids);

    StoreMetaData getStoreBudget();

    UpdateStoreBudgetResponse updateStoreBudget(UpdateStoreBudgetRequest request);

    UpdateMaterialsQuantitiesResponse updateMaterialsQuantities(UpdateMaterialsQuantitiesRequest request);

    StoreOrderDataResponse storeOrderData(Order order);
}
