package com.modelDataProducer.PizzaStore.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MenuItem {
    private String name;
    private MenuItemType type;
    private ArrayList<Material> requiredMaterials;
    private int preparationTimeInMinute;
    private BigDecimal price;

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return MenuItemType return the type
     */
    public MenuItemType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MenuItemType type) {
        this.type = type;
    }

    /**
     * @return ArrayList<Material> return the requiredMaterials
     */
    public ArrayList<Material> getRequiredMaterials() {
        return requiredMaterials;
    }

    /**
     * @param requiredMaterials the requiredMaterials to set
     */
    public void setRequiredMaterials(ArrayList<Material> requiredMaterials) {
        this.requiredMaterials = requiredMaterials;
    }

    /**
     * @return int return the preparationTimeInMinute
     */
    public int getPreparationTimeInMinute() {
        return preparationTimeInMinute;
    }

    /**
     * @param preparationTimeInMinute the preparationTimeInMinute to set
     */
    public void setPreparationTimeInMinute(int preparationTimeInMinute) {
        this.preparationTimeInMinute = preparationTimeInMinute;
    }

    /**
     * @return BigDecimal return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
