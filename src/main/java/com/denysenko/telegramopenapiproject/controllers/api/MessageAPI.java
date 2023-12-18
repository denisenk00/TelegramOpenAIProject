package com.denysenko.telegramopenapiproject.controllers.api;

import com.denysenko.telegramopenapiproject.model.Message;
import com.denysenko.telegramopenapiproject.model.dto.AdminMessageDTO;
import com.denysenko.telegramopenapiproject.security.jwt.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/v1/messages", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Message API", description = "API for managing messages")
@SecurityRequirement(name = "jwt")
public interface MessageAPI {

    @GetMapping("/last")
    @Operation(summary = "Get last N messages by bot user ID",
            description = "Retrieve the last N messages for a given bot user ID",
            parameters = {
                @Parameter(name = "userId", description = "ID of telegram bot user", in = ParameterIn.QUERY, required = true),
                @Parameter(name = "amount", description = "Amount of messages you want to fetch", in = ParameterIn.QUERY)
            },
            responses = {
                    @ApiResponse(description = "Last N messages was fetched successfully", responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(description = "Invalid parameters provided", responseCode = "400",
                            content = @Content(mediaType = "application/problem+json")),
                    @ApiResponse(description = "Not authenticated", responseCode = "401",
                            content = @Content(mediaType = "application/problem+json"))
            }
    )
    ResponseEntity<List<Message>> getLastNMessagesByBotUserId(
            @RequestParam(name = "userId") long botUserId,
            @RequestParam(name = "amount", defaultValue = "10", required = false) int amount);



    @GetMapping
    @Operation(
            summary = "Get a page of messages",
            description = "Retrieve a paginated list of messages for a given bot user ID.",
            parameters = {
                @Parameter(name = "userId", description = "ID of the telegram bot user", in = ParameterIn.QUERY, required = true),
                @Parameter(name = "page", description = "Page number (default is 1)", in = ParameterIn.QUERY, example = "1"),
                @Parameter(name = "size", description = "Page size (default is 30)", in = ParameterIn.QUERY, example = "30")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided",
                    content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(description = "Not authenticated", responseCode = "401",
                    content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "404", description = "Messages not found",
                    content = @Content(mediaType = "application/problem+json"))
    })
    ResponseEntity<Page<Message>> getPageOfMessages(
            @RequestParam(name = "userId") long botUserId,
            @RequestParam(name = "page", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(name = "size", defaultValue = "30", required = false) int pageSize);



    @PostMapping
    @Operation(
            summary = "Send a message",
            description = "Send a message using the provided AdminMessageDTO.",
            parameters = {
                    @Parameter(name = "adminMessageDTO", description = "Admin message data", in = ParameterIn.DEFAULT),
                    @Parameter(hidden = true)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message sent successfully",
                    content = @Content(mediaType = "application/json"), useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "401", description = "Not authenticated", content = @Content(mediaType = "application/problem+json"))
    })
    ResponseEntity sendMessage(@RequestBody @Valid AdminMessageDTO adminMessageDTO,
                                      @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

}
