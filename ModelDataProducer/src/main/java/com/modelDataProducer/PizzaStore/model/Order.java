package com.modelDataProducer.PizzaStore.model;

import java.util.List;

public class Order {
    private CreditCard creditCard;
    private List<MenuItem> orderedItems;

    /**
     * @return CreditCard return the creditCard
     */
    public CreditCard getCreditCard() {
        return creditCard;
    }

    /**
     * @param creditCard the creditCard to set
     */
    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    /**
     * @return List<MenuItem> return the orderedItems
     */
    public List<MenuItem> getOrderedItems() {
        return orderedItems;
    }

    /**
     * @param orderedItems the orderedItems to set
     */
    public void setOrderedItems(List<MenuItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

}
