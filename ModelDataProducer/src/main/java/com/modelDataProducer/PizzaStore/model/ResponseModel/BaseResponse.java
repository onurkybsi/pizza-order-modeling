package com.modelDataProducer.PizzaStore.model.ResponseModel;

public class BaseResponse {
    private boolean isSuccess;
    private String message;

    public BaseResponse() {
    }

    public BaseResponse(boolean isSucces) {
    }

    public BaseResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    /**
     * @return boolean return the isSuccess
     */
    public boolean isIsSuccess() {
        return isSuccess;
    }

    /**
     * @param isSuccess the isSuccess to set
     */
    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     * @return String return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
