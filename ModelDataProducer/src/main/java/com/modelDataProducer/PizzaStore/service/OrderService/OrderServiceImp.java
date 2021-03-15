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
        List<MenuItem> orderedMenuItemsInDetail = getOrderedMenuItemsInDetail(order.getOrderedItems());
        List<Material> requiredMaterialsInStock = getRequiredMaterialsInStock(orderedMenuItemsInDetail);
        Map<String, Integer> requiredMaterialsQuantity = getRequiredMaterialsQuantities(order.getOrderedItems(),
                orderedMenuItemsInDetail);
        Map<String, Integer> materialsAfterOrdering = getMaterialsAfterOrdering(requiredMaterialsInStock,
                requiredMaterialsQuantity);

        Map<String, BigDecimal> missingMaterialsCost = getMissingMaterialsCost(materialsAfterOrdering,
                requiredMaterialsInStock);

        BigDecimal storeBudget = new BigDecimal(100);
        storeBudget = storeBudget.add(getOrderIncome(order.getOrderedItems(), orderedMenuItemsInDetail));
        if (missingMaterialsCost.size() > 0) {
            prepareMaterialsAfterOrderingByMissingMaterials(materialsAfterOrdering, missingMaterialsCost);
            storeBudget = storeBudget.subtract(getSubtractExpensesFromBudget(missingMaterialsCost));
        }

        // update materials stock
        // update budget
        // store order data

        return new OrderResult(true, "");
    }

    private List<MenuItem> getOrderedMenuItemsInDetail(List<OrderedMenuItem> orderedMenuItems) {
        List<MenuItem> orderedMenuItemsInDetail = mediatorClient
                .getMenuItemsByIds(getOrderedMenuItemsIds(orderedMenuItems));

        return orderedMenuItemsInDetail == null ? new ArrayList<>() : orderedMenuItemsInDetail;
    }

    private List<String> getOrderedMenuItemsIds(List<OrderedMenuItem> orderedMenuItems) {
        return orderedMenuItems.stream().map(OrderedMenuItem::getId).collect(Collectors.toList());
    }

    private List<Material> getRequiredMaterialsInStock(List<MenuItem> orderedMenuItemsInDetail) {
        ArrayList<String> requiredMaterialIds = getRequiredMaterialsIds(orderedMenuItemsInDetail);
        List<Material> requiredMaterials = mediatorClient.getMaterialsByIds(requiredMaterialIds);
        return requiredMaterials == null ? new ArrayList<>() : requiredMaterials;
    }

    private ArrayList<String> getRequiredMaterialsIds(List<MenuItem> orderedMenuItemsInDetail) {
        ArrayList<String> requiredMaterialIds = new ArrayList<>();

        for (MenuItem menuItem : orderedMenuItemsInDetail) {
            if (menuItem.getType() == MenuItemType.PIZZA) {
                for (MenuItemRequiredMaterial material : menuItem.getRequiredMaterials()) {
                    if (!requiredMaterialIds.contains(material.getId())) {
                        requiredMaterialIds.add(material.getId());
                    }
                }
            } else {
                requiredMaterialIds.add(menuItem.getId());
            }
        }
        return requiredMaterialIds;
    }

    private Map<String, Integer> getRequiredMaterialsQuantities(List<OrderedMenuItem> orderedMenuItems,
            List<MenuItem> orderedMenuItemsInDetail) {
        HashMap<String, Integer> requiredMaterialsQuantity = new HashMap<>();

        for (OrderedMenuItem orderedMenuItem : orderedMenuItems) {
            MenuItem orderedMenuItemInDetail = getMenuItemInListById(orderedMenuItem.getId(), orderedMenuItemsInDetail);

            if (orderedMenuItemInDetail.getType() == MenuItemType.PIZZA) {
                addRequiredPizzaMaterialsQuantityToMap(orderedMenuItemInDetail, orderedMenuItem.getQuantity(),
                        requiredMaterialsQuantity);
            } else {
                requiredMaterialsQuantity.put(orderedMenuItemInDetail.getId(), orderedMenuItem.getQuantity());
            }
        }
        return requiredMaterialsQuantity;
    }

    private MenuItem getMenuItemInListById(String id, List<MenuItem> orderedMenuItemsInDetail) {
        Optional<MenuItem> optionalMenuItem = orderedMenuItemsInDetail.stream().filter(m -> m.getId().equals(id))
                .findFirst();

        return optionalMenuItem.isPresent() ? optionalMenuItem.get() : new MenuItem();
    }

    private void addRequiredPizzaMaterialsQuantityToMap(MenuItem pizzaMenuItem, int orderedPizzaQuantity,
            HashMap<String, Integer> requiredMaterialsQuantity) {
        for (MenuItemRequiredMaterial material : pizzaMenuItem.getRequiredMaterials()) {
            if (!requiredMaterialsQuantity.containsKey(material.getId())) {
                requiredMaterialsQuantity.put(material.getId(), orderedPizzaQuantity);
            } else {
                requiredMaterialsQuantity.put(material.getId(),
                        requiredMaterialsQuantity.get(material.getId()) + orderedPizzaQuantity);
            }
        }
    }

    private Map<String, Integer> getMaterialsAfterOrdering(List<Material> requiredMaterialsInStock,
            Map<String, Integer> requiredMaterialsQuantity) {
        Map<String, Integer> materialsAfterOrdering = new HashMap<>();

        for (Map.Entry<String, Integer> requiredMaterial : requiredMaterialsQuantity.entrySet()) {
            Material materialInStock = getMaterialInListById(requiredMaterial.getKey(), requiredMaterialsInStock);

            materialsAfterOrdering.put(materialInStock.getId(),
                    materialInStock.getQuantityInStock() - requiredMaterial.getValue());
        }
        return materialsAfterOrdering;
    }

    private Material getMaterialInListById(String id, List<Material> materialsInDetail) {
        Optional<Material> optionalMaterial = materialsInDetail.stream().filter(m -> m.getId().equals(id)).findFirst();

        return optionalMaterial.isPresent() ? optionalMaterial.get() : new Material();
    }

    private Map<String, BigDecimal> getMissingMaterialsCost(Map<String, Integer> materialsAfterOrdering,
            List<Material> materialDetails) {
        Map<String, BigDecimal> missingMaterialsCost = new HashMap<>();

        for (Map.Entry<String, Integer> materialAfterOrdering : materialsAfterOrdering.entrySet()) {
            if (materialAfterOrdering.getValue() >= 0)
                continue;

            Material materialDetail = getMaterialInListById(materialAfterOrdering.getKey(), materialDetails);

            missingMaterialsCost.put(materialDetail.getId(),
                    BigDecimal.valueOf(Math.abs(materialAfterOrdering.getValue())).multiply(materialDetail.getPrice()));
        }
        return missingMaterialsCost;
    }

    private void prepareMaterialsAfterOrderingByMissingMaterials(Map<String, Integer> materialsAfterOrdering,
            Map<String, BigDecimal> missingMaterialsCost) {

        for (Map.Entry<String, BigDecimal> missingMaterialWithCost : missingMaterialsCost.entrySet()) {
            materialsAfterOrdering.replace(missingMaterialWithCost.getKey(), 0);
        }
    }

    private BigDecimal getOrderIncome(List<OrderedMenuItem> orderedItems, List<MenuItem> orderedMenuItemsInDetail) {
        BigDecimal income = BigDecimal.ZERO;

        for (OrderedMenuItem orderedItem : orderedItems) {
            MenuItem orderedMenuItemInDetail = getMenuItemInListById(orderedItem.getId(), orderedMenuItemsInDetail);

            income = BigDecimal.valueOf(orderedItem.getQuantity()).multiply(orderedMenuItemInDetail.getPrice())
                    .add(income);
        }

        return income;
    }

    private BigDecimal getSubtractExpensesFromBudget(Map<String, BigDecimal> missingMaterialsCost) {
        BigDecimal subtractExpenses = BigDecimal.ZERO;

        for (BigDecimal subtractExpense : missingMaterialsCost.values()) {
            subtractExpenses = subtractExpense.add(subtractExpense);
        }
        return subtractExpenses;
    }
}
