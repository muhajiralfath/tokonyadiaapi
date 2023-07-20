package com.enigma.tokonyadia.repository;

import com.enigma.tokonyadia.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, String> {

}
