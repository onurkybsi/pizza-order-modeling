package com.modelDataProducer.PizzaStore.model.RequestModel;

import java.math.BigDecimal;

public class UpdateStoreBudgetRequest {
    private BigDecimal amount;

    public UpdateStoreBudgetRequest(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return BigDecimal return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
