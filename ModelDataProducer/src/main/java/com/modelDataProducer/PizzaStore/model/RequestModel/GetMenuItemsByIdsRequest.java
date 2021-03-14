package com.modelDataProducer.PizzaStore.model.RequestModel;

import java.util.List;

public class GetMenuItemsByIdsRequest {
    private List<String> menuItemIds;

    /**
     * @return List<String> return the menuItemIds
     */
    public List<String> getMenuItemIds() {
        return menuItemIds;
    }

    /**
     * @param menuItemIds the menuItemIds to set
     */
    public void setMenuItemIds(List<String> menuItemIds) {
        this.menuItemIds = menuItemIds;
    }
}
