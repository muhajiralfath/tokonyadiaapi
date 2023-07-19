package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Seller;
import com.enigma.tokonyadia.repository.SellerRepository;
import com.enigma.tokonyadia.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    @Override
    public Seller create(Seller seller) {
        try {
          return   sellerRepository.save(seller);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "email already used");
        }
    }
}
