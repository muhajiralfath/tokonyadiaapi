package com.enigma.tokonyadia.service;

import com.enigma.tokonyadia.model.request.AuthRequest;
import com.enigma.tokonyadia.model.response.LoginResponse;
import com.enigma.tokonyadia.model.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest request);
    LoginResponse login(AuthRequest request);
}
