package com.modelDataProducer.PizzaStore.service.MediatorClient;

import java.util.List;

import com.modelDataProducer.PizzaStore.model.Material;
import com.modelDataProducer.PizzaStore.model.MenuItem;
import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.StoreMetaData;
import com.modelDataProducer.PizzaStore.model.RequestModel.UpdateMaterialsQuantitiesRequest;
import com.modelDataProducer.PizzaStore.model.RequestModel.UpdateStoreBudgetRequest;
import com.modelDataProducer.PizzaStore.model.ResponseModel.BaseResponse;
import com.modelDataProducer.PizzaStore.model.ResponseModel.UpdateMaterialsQuantitiesResponse;
import com.modelDataProducer.PizzaStore.model.ResponseModel.UpdateStoreBudgetResponse;

public interface MediatorClient {
    public List<MenuItem> getMenu();

    public List<Material> getMaterials();

    public List<MenuItem> getMenuItemsByIds(List<String> ids);

    public List<Material> getMaterialsByIds(List<String> ids);

    public StoreMetaData getStoreBudget();

    public UpdateStoreBudgetResponse updateStoreBudget(UpdateStoreBudgetRequest request);

    public UpdateMaterialsQuantitiesResponse updateMaterialsQuantities(UpdateMaterialsQuantitiesRequest request);

    public BaseResponse storeOrderData(Order order);
}
