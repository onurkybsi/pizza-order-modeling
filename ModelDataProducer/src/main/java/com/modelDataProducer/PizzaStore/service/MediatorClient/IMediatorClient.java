package com.modelDataProducer.PizzaStore.service.MediatorClient;

import java.util.Set;

import com.modelDataProducer.PizzaStore.model.MenuItem;

public interface IMediatorClient {
    public Set<MenuItem> getMenu();
}
