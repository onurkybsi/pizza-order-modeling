package com.modelDataProducer.PizzaStore.model;

import org.springframework.expression.spel.ast.Identifier;

public class StoreMetaData {
    private String identifierName;
    private Object value; 

    /**
     * @return String return the identifierName
     */
    public String getIdentifierName() {
        return identifierName;
    }

    /**
     * @param identifierName the identifierName to set
     */
    public void setIdentifierName(String identifierName) {
        this.identifierName = identifierName;
    }

    /**
     * @return Object return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

}
