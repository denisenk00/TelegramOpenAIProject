package com.denysenko.telegramopenapiproject.controllers;

import com.denysenko.telegramopenapiproject.controllers.api.AuthAPI;
import com.denysenko.telegramopenapiproject.exceptions.AuthenticationException;
import com.denysenko.telegramopenapiproject.model.dto.CredentialsDTO;
import com.denysenko.telegramopenapiproject.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthAPI {

    private final AuthenticationService authenticationService;

    @Override
    @PostMapping("/register")
    public ResponseEntity<?> register(CredentialsDTO credentials){
        String token = authenticationService.register(credentials);
        return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body("Admin registered successfully");
    }

    @Override
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(CredentialsDTO credentials) {
        String token = authenticationService.authenticate(credentials);
        return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body("Admin authorized successfully");
    }

}
