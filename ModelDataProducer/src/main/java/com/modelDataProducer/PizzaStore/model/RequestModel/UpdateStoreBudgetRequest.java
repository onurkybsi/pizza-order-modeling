package com.modelDataProducer.PizzaStore.model.RequestModel;

public class UpdateStoreBudgetRequest {
    private int amount;

    public UpdateStoreBudgetRequest(int amount) {
        this.amount = amount;
    }

    /**
     * @return int return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

}
