package com.denysenko.telegramopenapiproject.configs;

import com.theokanning.openai.service.OpenAiService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Log4j
public class OpenAIConfiguration {

    @Value("${openai.key}")
    private String apiKey;
    @Value("${openai.timeout}")
    private long timeout;

    @Bean
    public OpenAiService initGptService(){
        var openAiService = new OpenAiService(apiKey, Duration.ofSeconds(timeout));
        log.info("Connected to OpenAI..");
        return openAiService;
    }


}
