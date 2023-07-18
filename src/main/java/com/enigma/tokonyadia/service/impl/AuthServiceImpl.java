package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.entity.Role;
import com.enigma.tokonyadia.entity.UserCredential;
import com.enigma.tokonyadia.entity.constant.ERole;
import com.enigma.tokonyadia.model.request.AuthRequest;
import com.enigma.tokonyadia.model.response.LoginResponse;
import com.enigma.tokonyadia.model.response.RegisterResponse;
import com.enigma.tokonyadia.repository.UserCredentialRepository;
import com.enigma.tokonyadia.security.BCrypUtils;
import com.enigma.tokonyadia.security.JwtUtils;
import com.enigma.tokonyadia.service.AuthService;
import com.enigma.tokonyadia.service.CustomerService;
import com.enigma.tokonyadia.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final AuthenticationManager authenticationManager;
    private final BCrypUtils bCrypUtils;
    private final RoleService roleService;
    private final CustomerService customerService;
    private final JwtUtils jwtUtils;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse register(AuthRequest request) {
        try {
            Role role = roleService.getOrSave(ERole.ROLE_CUSTOMER);
            UserCredential credential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(bCrypUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(credential);

            Customer customer = Customer.builder()
                    .email(credential.getEmail())
                    .userCredential(credential)
                    .build();
            customerService.create(customer);
            return RegisterResponse.builder().email(credential.getEmail()).build();
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        return null;
    }


}
