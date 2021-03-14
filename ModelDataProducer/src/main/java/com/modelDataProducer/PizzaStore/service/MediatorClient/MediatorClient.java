package com.modelDataProducer.PizzaStore.service.MediatorClient;

import java.util.List;

import com.modelDataProducer.PizzaStore.model.Material;
import com.modelDataProducer.PizzaStore.model.MenuItem;
import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.OrderResult;

public interface MediatorClient {
    List<MenuItem> getMenu();

    List<Material> getMaterials();

    OrderResult createOrder(Order order);

    List<MenuItem> getMenuItemsByIds(List<String> ids);

    List<Material> getMaterialsByIds(List<String> ids);
}
