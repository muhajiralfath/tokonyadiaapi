package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.entity.Store;
import com.enigma.tokonyadia.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping(path = "/store")
    public Store createNewStore(@RequestBody Store store) {
        return storeService.create(store);
    }

    @GetMapping(path = "/store/{id}")
    public Store getAllStore(@PathVariable String id) {
        return storeService.getById(id);
    }

    @GetMapping(path = "/store")
    public List<Store> getAllStore() {
        return storeService.getAll();
    }

    @PutMapping(path = "/store")
    public Store updateStore(@RequestBody Store store) {
        return storeService.update(store);
    }

}
