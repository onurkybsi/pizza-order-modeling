package com.modelDataProducer.PizzaStore.service.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.modelDataProducer.PizzaStore.model.Material;
import com.modelDataProducer.PizzaStore.model.MenuItem;
import com.modelDataProducer.PizzaStore.model.MenuItemRequiredMaterial;
import com.modelDataProducer.PizzaStore.model.MenuItemType;
import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.OrderResult;
import com.modelDataProducer.PizzaStore.model.OrderedMenuItem;
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
        List<MenuItem> orderedMenuItemsInDetails = getMenuItemsInDetails(order.getOrderedItems());
        Map<String, Integer> requiredMaterialsQuantity = getRequiredMaterialsWithQuantities(order.getOrderedItems(),
                orderedMenuItemsInDetails);

        List<Material> requiredMaterialsOfOrderedMenuItemsInDetails = getMaterialsOfMenuItemsInDetails(
                orderedMenuItemsInDetails);

        Map<String, Integer> materialsQuantitiesAfterOrdering = getMaterialsQuantitiesAfterOrdering(
                requiredMaterialsOfOrderedMenuItemsInDetails, requiredMaterialsQuantity);

        Map<String, BigDecimal> missingMaterialsCost = getMissingMaterialsCost(materialsQuantitiesAfterOrdering,
                requiredMaterialsOfOrderedMenuItemsInDetails);

        BigDecimal storeBudget = new BigDecimal(100);
        storeBudget = storeBudget.add(getOrderIncome(order.getOrderedItems(), orderedMenuItemsInDetails));
        if (missingMaterialsCost.size() > 0) {
            prepareMaterialsAfterOrderingByMissingMaterials(materialsQuantitiesAfterOrdering, missingMaterialsCost);
            storeBudget = storeBudget.subtract(getExpensesToBeSubtractFromBudget(missingMaterialsCost));
        }

        // update materials stock

        // update budget
        // store order data

        return new OrderResult(true, "");
    }

    private List<MenuItem> getMenuItemsInDetails(List<OrderedMenuItem> orderedMenuItemsToBeInDetails) {
        List<MenuItem> menuItemsInDetail = mediatorClient
                .getMenuItemsByIds(getMenuItemsIds(orderedMenuItemsToBeInDetails));

        return menuItemsInDetail == null ? new ArrayList<>() : menuItemsInDetail;
    }

    private List<String> getMenuItemsIds(List<OrderedMenuItem> orderedMenuItemsToBeGetingIds) {
        return orderedMenuItemsToBeGetingIds.stream().map(OrderedMenuItem::getId).collect(Collectors.toList());
    }

    private List<Material> getMaterialsOfMenuItemsInDetails(List<MenuItem> menuItems) {
        ArrayList<String> materialIds = getMaterialsIdsOfMenuItems(menuItems);
        List<Material> materials = mediatorClient.getMaterialsByIds(materialIds);
        return materials == null ? new ArrayList<>() : materials;
    }

    private ArrayList<String> getMaterialsIdsOfMenuItems(List<MenuItem> menuItems) {
        ArrayList<String> materialsIds = new ArrayList<>();

        for (MenuItem menuItem : menuItems) {
            if (menuItem.getType() == MenuItemType.PIZZA) {
                for (MenuItemRequiredMaterial material : menuItem.getRequiredMaterials()) {
                    if (!materialsIds.contains(material.getId())) {
                        materialsIds.add(material.getId());
                    }
                }
            } else {
                materialsIds.add(menuItem.getId());
            }
        }
        return materialsIds;
    }

    private Map<String, Integer> getRequiredMaterialsWithQuantities(List<OrderedMenuItem> orderedMenuItems,
            List<MenuItem> orderedMenuItemsInDetails) {
        HashMap<String, Integer> requiredMaterialsWithQuantities = new HashMap<>();

        for (OrderedMenuItem orderedMenuItem : orderedMenuItems) {
            MenuItem orderedMenuItemInDetail = getMenuItemFromListById(orderedMenuItem.getId(),
                    orderedMenuItemsInDetails);

            if (orderedMenuItemInDetail.getType() == MenuItemType.PIZZA) {
                addRequiredPizzaMaterialsQuantitiesToMap(orderedMenuItemInDetail, orderedMenuItem.getQuantity(),
                        requiredMaterialsWithQuantities);
            } else {
                requiredMaterialsWithQuantities.put(orderedMenuItemInDetail.getId(), orderedMenuItem.getQuantity());
            }
        }
        return requiredMaterialsWithQuantities;
    }

    private MenuItem getMenuItemFromListById(String id, List<MenuItem> menuItems) {
        Optional<MenuItem> optionalMenuItem = menuItems.stream().filter(m -> m.getId().equals(id)).findFirst();

        return optionalMenuItem.isPresent() ? optionalMenuItem.get() : new MenuItem();
    }

    private void addRequiredPizzaMaterialsQuantitiesToMap(MenuItem pizza, int orderedPizzaQuantity,
            HashMap<String, Integer> requiredMaterialsWithQuantities) {
        for (MenuItemRequiredMaterial material : pizza.getRequiredMaterials()) {
            if (!requiredMaterialsWithQuantities.containsKey(material.getId())) {
                requiredMaterialsWithQuantities.put(material.getId(), orderedPizzaQuantity);
            } else {
                requiredMaterialsWithQuantities.put(material.getId(),
                        requiredMaterialsWithQuantities.get(material.getId()) + orderedPizzaQuantity);
            }
        }
    }

    private Map<String, Integer> getMaterialsQuantitiesAfterOrdering(List<Material> requiredMaterialsInStock,
            Map<String, Integer> requiredMaterialsWithQuantities) {
        Map<String, Integer> materialsAfterOrdering = new HashMap<>();

        for (Map.Entry<String, Integer> requiredMaterial : requiredMaterialsWithQuantities.entrySet()) {
            Material materialInStock = getMaterialFromListById(requiredMaterial.getKey(), requiredMaterialsInStock);

            materialsAfterOrdering.put(materialInStock.getId(),
                    materialInStock.getQuantityInStock() - requiredMaterial.getValue());
        }
        return materialsAfterOrdering;
    }

    private Material getMaterialFromListById(String id, List<Material> materialsInDetail) {
        Optional<Material> optionalMaterial = materialsInDetail.stream().filter(m -> m.getId().equals(id)).findFirst();

        return optionalMaterial.isPresent() ? optionalMaterial.get() : new Material();
    }

    private Map<String, BigDecimal> getMissingMaterialsCost(Map<String, Integer> materialsQuantitiesAfterOrdering,
            List<Material> materialDetails) {
        Map<String, BigDecimal> missingMaterialsCost = new HashMap<>();

        for (Map.Entry<String, Integer> materialQuantityAfterOrdering : materialsQuantitiesAfterOrdering.entrySet()) {
            if (materialQuantityAfterOrdering.getValue() >= 0)
                continue;

            Material materialDetail = getMaterialFromListById(materialQuantityAfterOrdering.getKey(), materialDetails);

            missingMaterialsCost.put(materialDetail.getId(), BigDecimal
                    .valueOf(Math.abs(materialQuantityAfterOrdering.getValue())).multiply(materialDetail.getPrice()));
        }
        return missingMaterialsCost;
    }

    private void prepareMaterialsAfterOrderingByMissingMaterials(Map<String, Integer> materialsQuantitiesAfterOrdering,
            Map<String, BigDecimal> missingMaterialsCost) {

        for (Map.Entry<String, BigDecimal> missingMaterialWithCost : missingMaterialsCost.entrySet()) {
            materialsQuantitiesAfterOrdering.replace(missingMaterialWithCost.getKey(), 0);
        }
    }

    private BigDecimal getOrderIncome(List<OrderedMenuItem> orderedItems, List<MenuItem> orderedMenuItemsInDetails) {
        BigDecimal income = BigDecimal.ZERO;

        for (OrderedMenuItem orderedItem : orderedItems) {
            MenuItem orderedMenuItemInDetail = getMenuItemFromListById(orderedItem.getId(), orderedMenuItemsInDetails);

            income = BigDecimal.valueOf(orderedItem.getQuantity()).multiply(orderedMenuItemInDetail.getPrice())
                    .add(income);
        }
        return income;
    }

    private BigDecimal getExpensesToBeSubtractFromBudget(Map<String, BigDecimal> missingMaterialsCost) {
        BigDecimal expensesToBeSubstracted = BigDecimal.ZERO;

        for (BigDecimal subtractExpense : missingMaterialsCost.values()) {
            expensesToBeSubstracted = subtractExpense.add(subtractExpense);
        }
        return expensesToBeSubstracted;
    }
}
