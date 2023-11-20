package com.denysenko.telegramopenapiproject.controllers;

import com.denysenko.telegramopenapiproject.exceptions.AuthorizationException;
import com.denysenko.telegramopenapiproject.model.dto.CredentialsDTO;
import com.denysenko.telegramopenapiproject.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CredentialsDTO credentials){
        String token = authenticationService.register(credentials);
        return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body("Admin registered successfully");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody @Valid CredentialsDTO credentials) throws AuthorizationException{
        String token;
        try {
            token = authenticationService.authenticate(credentials);
        }catch (Exception e){
            throw new AuthorizationException("Authorization wasn't successful: " + e.getMessage(), e);
        }
        return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body("Admin authorized successfully");
    }

}
