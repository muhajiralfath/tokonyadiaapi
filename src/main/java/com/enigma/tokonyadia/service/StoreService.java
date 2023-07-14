package com.enigma.tokonyadia.service;

import com.enigma.tokonyadia.entity.Store;

import java.util.List;

public interface StoreService {
    Store create(Store store);
    Store getById(String id);
    List<Store> getAll();
    Store update(Store store);

}
