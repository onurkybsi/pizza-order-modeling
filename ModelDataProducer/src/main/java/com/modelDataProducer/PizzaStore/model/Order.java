package com.modelDataProducer.PizzaStore.model;

import java.util.List;

public class Order {
    private CreditCard creditCardInfo;
    private List<OrderedMenuItem> orderedMenuItems;

    /**
     * @return CreditCard return the creditCardInfo
     */
    public CreditCard getCreditCardInfo() {
        return creditCardInfo;
    }

    /**
     * @param creditCardInfo the creditCardInfo to set
     */
    public void setCreditCardInfo(CreditCard creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }

    /**
     * @return List<OrderedMenuItem> return the orderedMenuItems
     */
    public List<OrderedMenuItem> getOrderedMenuItems() {
        return orderedMenuItems;
    }

    /**
     * @param orderedMenuItems the orderedMenuItems to set
     */
    public void setOrderedMenuItems(List<OrderedMenuItem> orderedMenuItems) {
        this.orderedMenuItems = orderedMenuItems;
    }
}
