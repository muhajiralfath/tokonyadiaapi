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
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.<Store>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created")
                        .data(store)
                        .build());
    }

    @GetMapping(path = "/store/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable String id) {
        Store store = storeService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<Store>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get store by id")
                        .data(store)
                        .build());
    }

    @GetMapping(path = "/store")
    public ResponseEntity<?> getAllStore() {
        List<Store> stores = storeService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<List<Store>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all store")
                        .data(stores)
                        .build());
    }

    @PutMapping(path = "/store")
    public ResponseEntity<?> updateStore(@RequestBody Store request) {
        Store store = storeService.update(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<Store>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update store")
                        .data(store)
                        .build());
    }

}
