package com.modelDataProducer.PizzaStore.model.ResponseModel;

public class StoreOrderDataResponse extends BaseResponse {
    public StoreOrderDataResponse() {
        super();
    }

    public StoreOrderDataResponse(boolean isSuccess) {
        super(isSuccess);
    }

    public StoreOrderDataResponse(boolean isSuccess, String message) {
        super(isSuccess, message);
    }
}
