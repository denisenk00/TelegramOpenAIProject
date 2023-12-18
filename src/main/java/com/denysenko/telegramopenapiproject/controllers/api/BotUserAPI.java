package com.denysenko.telegramopenapiproject.controllers.api;

import com.denysenko.telegramopenapiproject.model.BotUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "BotUser API", description = "API for managing telegram bot users")
@SecurityRequirement(name = "jwt")
public interface BotUserAPI {

    @Operation(
            summary = "Get a page of bot users",
            description = "Retrieve a paginated list of bot users.",
            parameters = {
                    @Parameter(name = "page", description = "Page number (default is 1)", in = ParameterIn.QUERY, example = "1"),
                    @Parameter(name = "size", description = "Page size (default is 30)", in = ParameterIn.QUERY, example = "30")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided", content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "404", description = "Bot users not found", content = @Content(mediaType = "application/problem+json"))
    })
    @GetMapping
    ResponseEntity<Page<BotUser>> getPageOfBotUsers(@RequestParam(name = "page", defaultValue = "1", required = false) int pageNumber,
                                                    @RequestParam(name = "size", defaultValue = "30", required = false) int pageSize);
}
