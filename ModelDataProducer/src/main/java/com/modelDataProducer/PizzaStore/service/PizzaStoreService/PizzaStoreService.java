package com.modelDataProducer.PizzaStore.service.PizzaStoreService;

import java.util.List;

import com.modelDataProducer.PizzaStore.model.MenuItem;
import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.ResponseModel.BaseResponse;

public interface PizzaStoreService {
    public List<MenuItem> getMenu();
    public BaseResponse creatOrder(Order order);
}
