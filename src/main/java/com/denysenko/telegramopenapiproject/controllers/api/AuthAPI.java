package com.denysenko.telegramopenapiproject.controllers.api;

import com.denysenko.telegramopenapiproject.model.dto.CredentialsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication API", description = "API for authentication admin users")
public interface AuthAPI {

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Register a new user with the provided credentials.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json"))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content(mediaType = "application/problem+json"))
    })
    ResponseEntity<?> register(@RequestBody @Valid CredentialsDTO credentials);

    @PostMapping("/authenticate")
    @Operation(
            summary = "Authenticate a user",
            description = "Authenticate a user with the provided credentials.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "401", description = "Authentication failed", content = @Content(mediaType = "application/problem+json"))
    })
    ResponseEntity<?> authenticate(@RequestBody @Valid CredentialsDTO credentials);

}
