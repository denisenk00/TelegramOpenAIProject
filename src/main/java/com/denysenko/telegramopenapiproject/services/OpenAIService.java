package com.denysenko.telegramopenapiproject.services;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
@AllArgsConstructor
public class OpenAIService {

    private final OpenAiService openAiService;

    private static final String GPT_MODEL = "gpt-3.5-turbo";

    public String executeTextRequest(String text){
        var request = ChatCompletionRequest.builder()
                .model(GPT_MODEL)
                .temperature(0.8)
                .n(1)
                .messages(List.of(
                        new ChatMessage("user", text)
                ))
                .build();
        var message = openAiService.createChatCompletion(request).getChoices().get(0).getMessage();
        return message.getContent();
    }

}
