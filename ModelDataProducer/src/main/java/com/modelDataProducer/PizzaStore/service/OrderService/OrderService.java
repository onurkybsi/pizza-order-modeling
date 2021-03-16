package com.modelDataProducer.PizzaStore.service.OrderService;

import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.ResponseModel.BaseResponse;

public interface OrderService {
    BaseResponse creatOrder(Order order);
}
