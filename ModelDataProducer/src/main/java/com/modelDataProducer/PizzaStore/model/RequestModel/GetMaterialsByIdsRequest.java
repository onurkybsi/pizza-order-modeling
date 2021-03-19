package com.modelDataProducer.PizzaStore.model.RequestModel;

import java.util.List;

public class GetMaterialsByIdsRequest {
 private List<String> materialsIds;   

    public GetMaterialsByIdsRequest() {
    }

    public GetMaterialsByIdsRequest(List<String> materialsIds) {
        this.materialsIds = materialsIds;
    }

    /**
     * @return List<String> return the materialsIds
     */
    public List<String> getMaterialsIds() {
        return materialsIds;
    }

    /**
     * @param materialsIds the materialsIds to set
     */
    public void setMaterialsIds(List<String> materialsIds) {
        this.materialsIds = materialsIds;
    }

}
