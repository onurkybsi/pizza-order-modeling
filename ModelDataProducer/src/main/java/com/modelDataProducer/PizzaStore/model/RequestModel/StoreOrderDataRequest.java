package com.modelDataProducer.PizzaStore.model.RequestModel;

import com.modelDataProducer.PizzaStore.model.Order;

public class StoreOrderDataRequest {
    private Order orderInfo;

    public StoreOrderDataRequest(Order orderInfo) {
        this.orderInfo = orderInfo;
    }

    /**
     * @return Order return the orderInfo
     */
    public Order getOrderInfo() {
        return orderInfo;
    }

    /**
     * @param orderInfo the orderInfo to set
     */
    public void setOrderInfo(Order orderInfo) {
        this.orderInfo = orderInfo;
    }

}
