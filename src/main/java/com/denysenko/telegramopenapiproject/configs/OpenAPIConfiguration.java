package com.denysenko.telegramopenapiproject.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "TelegramOpenAIProject",
                description = "Telegram bot for OpenAI chatGPT interaction",
                contact = @Contact(
                        name = "denisenk00",
                        email = "dmitriy.denisenko.2002@gmail.com"
                )
        ),
        servers = @Server(
                url = "http://localhost:8090/api",
                description = "dev"
        )
)
@SecuritySchemes({
        @SecurityScheme(
            name = "jwt",
            scheme = "bearer",
            type = SecuritySchemeType.HTTP,
            in = SecuritySchemeIn.HEADER,
            bearerFormat = "JWT"
        ),
        @SecurityScheme(
            name = "basicAuth",
            scheme = "basic",
            type = SecuritySchemeType.HTTP,
            in = SecuritySchemeIn.HEADER
        )
})

public class OpenAPIConfiguration {

}
