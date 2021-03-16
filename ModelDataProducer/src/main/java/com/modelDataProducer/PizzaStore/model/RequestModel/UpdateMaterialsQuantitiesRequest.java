package com.modelDataProducer.PizzaStore.model.RequestModel;

import java.util.Map;

public class UpdateMaterialsQuantitiesRequest {
    private Map<String, Integer> materialsIdsWithQuantities;

    public UpdateMaterialsQuantitiesRequest(Map<String, Integer> materialsIdsWithQuantities) {
        this.materialsIdsWithQuantities = materialsIdsWithQuantities;
    }

    /**
     * @return Map<String, Integer> return the materialsIdsWithQuantities
     */
    public Map<String, Integer> getMaterialsIdsWithQuantities() {
        return materialsIdsWithQuantities;
    }

    /**
     * @param materialsIdsWithQuantities the materialsIdsWithQuantities to set
     */
    public void setMaterialsIdsWithQuantities(Map<String, Integer> materialsIdsWithQuantities) {
        this.materialsIdsWithQuantities = materialsIdsWithQuantities;
    }

}
