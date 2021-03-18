package com.modelDataProducer.PizzaStore.controller;

import java.util.List;

import com.modelDataProducer.PizzaStore.model.MenuItem;
import com.modelDataProducer.PizzaStore.model.Order;
import com.modelDataProducer.PizzaStore.model.ResponseModel.BaseResponse;
import com.modelDataProducer.PizzaStore.service.PizzaStoreService.PizzaStoreService;

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
    private PizzaStoreService pizzaStoreService;

    @GetMapping("/getMenu")
    public List<MenuItem> getMenu() {
        return pizzaStoreService.getMenu();
    }

    @PostMapping("/createOrder")
    public BaseResponse creatOrder(@RequestBody Order order) {
        // TO-DO: if isSuccess false return 500
        return pizzaStoreService.creatOrder(order);
    }
}
