package com.modelDataProducer.PizzaStore.model.ResponseModel;

public class UpdateMaterialsQuantitiesResponse extends BaseResponse {
    public UpdateMaterialsQuantitiesResponse() {
        super();
    }

    public UpdateMaterialsQuantitiesResponse(boolean isSuccess) {
        super(isSuccess);
    }

    public UpdateMaterialsQuantitiesResponse(boolean isSuccess, String message) {
        super(isSuccess, message);
    }
}
