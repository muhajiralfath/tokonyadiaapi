package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.model.request.AuthRequest;
import com.enigma.tokonyadia.model.response.CommonResponse;
import com.enigma.tokonyadia.model.response.LoginResponse;
import com.enigma.tokonyadia.model.response.RegisterResponse;
import com.enigma.tokonyadia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request){
        RegisterResponse register = authService.register(request);
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully Register")
                .data(register)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commonResponse);
    }
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        LoginResponse response = authService.login(request);
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Login")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(commonResponse);
    }
}
