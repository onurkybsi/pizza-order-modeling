package com.modelDataProducer.PizzaStore.model;

import java.math.BigDecimal;

public class Material {
    private String name;
    private MaterialType type;
    private BigDecimal price;
    private int quantityInStock;

    public Material(String name, MaterialType type, BigDecimal price, int quantityInStock) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setType(MaterialType type) {
        this.type = type;
    }

    public MaterialType getType() {
        return this.type;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public int getQuantityInStock() {
        return this.quantityInStock;
    }
}
