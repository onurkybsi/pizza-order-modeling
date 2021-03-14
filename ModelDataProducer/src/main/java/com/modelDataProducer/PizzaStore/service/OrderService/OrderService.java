package com.modelDataProducer.PizzaStore.service.OrderService;

import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.OrderResult;

public interface OrderService {
    OrderResult creatOrder(Order order);
}
