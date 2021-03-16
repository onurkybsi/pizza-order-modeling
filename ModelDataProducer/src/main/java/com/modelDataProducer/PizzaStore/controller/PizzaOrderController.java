package com.modelDataProducer.PizzaStore.controller;

import java.util.List;

import com.modelDataProducer.PizzaStore.model.MenuItem;
import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.ResponseModel.BaseResponse;
import com.modelDataProducer.PizzaStore.service.MediatorClient.MediatorClient;
import com.modelDataProducer.PizzaStore.service.OrderService.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class PizzaOrderController {
    @Autowired
    private MediatorClient mediatorClient;

    @Autowired
    private OrderService orderService;

    public PizzaOrderController(MediatorClient mediatorClient, OrderService orderService) {
        this.mediatorClient = mediatorClient;
        this.orderService = orderService;
    }

    @GetMapping("/getMenu")
    public List<MenuItem> getMenu() {
        return mediatorClient.getMenu();
    }

    @PostMapping("/createOrder")
    public BaseResponse creatOrder(@RequestBody Order order) {
        return orderService.creatOrder(order);
    }
}
