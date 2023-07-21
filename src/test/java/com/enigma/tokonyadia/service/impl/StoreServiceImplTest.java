package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Store;
import com.enigma.tokonyadia.model.request.UpdateStoreRequest;
import com.enigma.tokonyadia.model.response.StoreResponse;
import com.enigma.tokonyadia.repository.StoreRepository;
import com.enigma.tokonyadia.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StoreServiceImplTest {

    @Mock
    private StoreRepository storeRepository;

    private StoreService storeService;

    @BeforeEach
    void setUp() {
        storeService = new StoreServiceImpl(storeRepository);
    }

    @Test
    void shouldReturnStoreWhenGetById() {
        String id = "1";
        Store toko = Store.builder()
                .id("1")
                .noSiup("12345")
                .name("Toko")
                .mobilePhone("09876543")
                .build();

        when(storeRepository.findById(id)).thenReturn(Optional.of(toko));

        Store actual = storeService.getById(id);

        assertEquals("Toko", actual.getName());
    }

    @Test
    void shouldReturnListStoreWhenGetAll() {
        Store toko = Store.builder()
                .id("1")
                .noSiup("12345")
                .name("Toko")
                .mobilePhone("09876543")
                .build();
        Store alfath = Store.builder()
                .id("2")
                .noSiup("987564")
                .name("Toko Alfath")
                .mobilePhone("098851512")
                .build();
        List<Store> stores = new ArrayList<>();
        stores.add(toko);
        stores.add(alfath);

        when(storeRepository.findAll()).thenReturn(stores);

        List<Store> actual = storeService.getAll();

        assertEquals(2, actual.size());
    }

    @Test
    void shouldReturnStoreWhenUpdate() {
        UpdateStoreRequest updateToko = UpdateStoreRequest.builder()
                .storeId("1")
                .noSiup("12345")
                .storeName("Toko")
                .mobilePhone("09876543")
                .build();

        Store toko = Store.builder()
                .id(updateToko.getStoreId())
                .noSiup(updateToko.getNoSiup())
                .name(updateToko.getStoreName())
                .mobilePhone(updateToko.getMobilePhone())
                .build();

        when(storeRepository.findById(updateToko.getStoreId())).thenReturn(Optional.of(toko));
        when(storeRepository.save(toko)).thenReturn(toko);

        StoreResponse actual = storeService.update(updateToko);

        assertEquals("Toko", actual.getName());
    }
}