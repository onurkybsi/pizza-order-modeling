package com.modelDataProducer.PizzaStore.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.modelDataProducer.PizzaStore.model.Material;
import com.modelDataProducer.PizzaStore.model.MenuItem;
import com.modelDataProducer.PizzaStore.model.MenuItemRequiredMaterial;
import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.OrderResult;
import com.modelDataProducer.PizzaStore.service.MediatorClient.MediatorClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImp implements OrderService {
    @Autowired
    private MediatorClient mediatorClient;

    public OrderServiceImp(MediatorClient mediatorClient) {
        this.mediatorClient = mediatorClient;
    }

    @Override
    public OrderResult creatOrder(Order order) {
        List<MenuItem> orderedMenuItemsInDetail = getOrderedMenuItemsInDetail(
                getOrderedMenuItemsIds(order.getOrderedItems()));
        List<Material> requiredMaterials = getRequiredMaterialsInDetail(orderedMenuItemsInDetail);

        return new OrderResult();
    }

    private List<String> getOrderedMenuItemsIds(List<MenuItem> orderedMenuItems) {
        return orderedMenuItems.stream().map(MenuItem::getId).collect(Collectors.toList());
    }

    private List<MenuItem> getOrderedMenuItemsInDetail(List<String> orderedMenuItemsIds) {
        List<MenuItem> orderedMenuItemsInDetail = mediatorClient.getMenuItemsByIds(orderedMenuItemsIds);

        return orderedMenuItemsInDetail == null ? new ArrayList<>() : orderedMenuItemsInDetail;
    }

    private List<Material> getRequiredMaterialsInDetail(List<MenuItem> orderedMenuItemsInDetail) {
        ArrayList<String> requiredMaterialIds = new ArrayList<>();

        for (MenuItem menuItem : orderedMenuItemsInDetail) {
            for (MenuItemRequiredMaterial material : menuItem.getRequiredMaterials()) {
                if (!requiredMaterialIds.contains(material.getId())) {
                    requiredMaterialIds.add(material.getId());
                }
            }
        }

        List<Material> requiredMaterials = mediatorClient.getMaterialsByIds(requiredMaterialIds);

        return requiredMaterials == null ? new ArrayList<>() : requiredMaterials;
    }
}
