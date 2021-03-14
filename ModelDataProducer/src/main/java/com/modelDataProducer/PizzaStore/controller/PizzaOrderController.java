package com.modelDataProducer.PizzaStore.controller;

import java.util.List;

import com.modelDataProducer.PizzaStore.model.Material;
import com.modelDataProducer.PizzaStore.model.MenuItem;
import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.OrderResult;
import com.modelDataProducer.PizzaStore.service.MediatorClient.MediatorClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class PizzaOrderController {
    @Autowired
    private MediatorClient mediatorClient;

    public PizzaOrderController(MediatorClient mediatorClient) {
        this.mediatorClient = mediatorClient;
    }

    @GetMapping("/getMenu")
    public List<MenuItem> getMenu() {
        return mediatorClient.getMenu();
    }

    @GetMapping("/getMaterials")
    public List<Material> getMaterials() {
        return mediatorClient.getMaterials();
    }

    @PostMapping("/createOrder")
    public OrderResult creatOrder(Order order) {
        OrderResult result = new OrderResult();
        result.setIsSuccess(true);
        result.setMessage("TEST");
        return result;
    }
}
