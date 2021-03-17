package com.modelDataProducer.PizzaStore.service.OrderService;

import java.util.List;

import com.modelDataProducer.PizzaStore.model.MenuItem;
import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.ResponseModel.BaseResponse;

public interface PizzaStoreService {
    List<MenuItem> getMenu();
    BaseResponse creatOrder(Order order);
}
