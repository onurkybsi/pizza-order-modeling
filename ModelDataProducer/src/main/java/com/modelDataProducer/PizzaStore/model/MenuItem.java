package com.modelDataProducer.PizzaStore.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MenuItem {
    private String id;
    private String name;
    private MenuItemType type;
    private ArrayList<MenuItemRequiredMaterial> requiredMaterials;
    private int preparationTimeInMinute;
    private BigDecimal price;

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

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
     * @return ArrayList<MenuItemRequiredMaterial> return the requiredMaterials
     */
    public ArrayList<MenuItemRequiredMaterial> getRequiredMaterials() {
        return requiredMaterials;
    }

    /**
     * @param requiredMaterials the requiredMaterials to set
     */
    public void setRequiredMaterials(ArrayList<MenuItemRequiredMaterial> requiredMaterials) {
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
