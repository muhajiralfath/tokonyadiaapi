package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.entity.Store;
import com.enigma.tokonyadia.model.response.CommonResponse;
import com.enigma.tokonyadia.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping(path = "/store")
    public ResponseEntity<CommonResponse<Store>> createNewStore(@RequestBody Store request) {
        Store store = storeService.create(request);

        CommonResponse<Store> commonResponse = CommonResponse.<Store>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully created")
                .data(store)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @GetMapping(path = "/store/{id}")
    public Store getStoreById(@PathVariable String id) {
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
