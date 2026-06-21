package com.ai.SpringAIDemo.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/ask-ai")
    public String getResponse(@RequestParam String prompt) {
        return chatService.getResponse(prompt);
    }

    @GetMapping("/ask-ai/options")
    public String getResponseByOptions(@RequestParam String prompt,
                                       @RequestParam(required = false, defaultValue = "0.7") Double temperature,
                                       @RequestParam(required = false, defaultValue = "500") Integer maxTokens) {
        return chatService.getResponseByOptions(prompt, temperature, maxTokens);
    }
}
