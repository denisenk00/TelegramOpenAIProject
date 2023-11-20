package com.denysenko.telegramopenapiproject.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AdminMessageDTO(@NotNull Long botUserId, @NotBlank String message) {
}
