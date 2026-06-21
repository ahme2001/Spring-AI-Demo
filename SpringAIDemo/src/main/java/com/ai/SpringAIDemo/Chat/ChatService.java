package com.ai.SpringAIDemo.Chat;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.ai.deepseek.api.DeepSeekApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChatService {


    private final ChatModel chatModel;

    public ChatService(@Qualifier(value = "deepSeekChatModel")ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String getResponse(String prompt) {
        String response;
        try {
            response = chatModel.call(prompt);
        } catch (Exception e) {
            return e.getMessage();
        }
        return response;
    }

    public String getResponseByOptions(String prompt, Double temperature, Integer maxTokens) {
        maxTokens = (Objects.isNull(maxTokens) || maxTokens < 0)? 500 : maxTokens;
        maxTokens = maxTokens > 2000 ? maxTokens : 2000;

        temperature = (temperature > 0.0 && temperature < 2.0) ? temperature : (float) 0.7;
        ChatResponse chatResponse;
        try {
            chatResponse = chatModel.call(new Prompt(
                    prompt,
                    DeepSeekChatOptions.builder()
                            .model(DeepSeekApi.ChatModel.DEEPSEEK_V4_FLASH)
                            .temperature(temperature)
                            .maxTokens(maxTokens)
                            .build()
            ));
        } catch (Exception e) {
            return e.getMessage();
        }
        return Objects.requireNonNull(chatResponse.getResult()).getOutput().getText();
    }
}

/* Defining all chat options and its impact "https://api-docs.deepseek.com/api/create-chat-completion"
    * model (default = deepseek-v4-flash) -> specifies which model to use
    * temperature (default = 0.7) -> controls randomness --> 0.0 = deterministic , 2.0 = very random
    * topP -> Another randomness control "Instead of considering all possible next tokens, the model only considers the most probable tokens whose cumulative probability reaches P "
    * maxTokens -> Maximum tokens generated
    * stop -> Custom stop sequences. "Useful when generating structured text." you send list of words similar to end or finish or last or ...."
    * responseFormat -> Controls output format
    * logprobs -> Returns token probabilities "useful for research and debugging"
    * topLogprobs -> Returns alternative token candidates.
    * tools -> Functions available to the model. "model can choose function instead of generating test"
    * toolChoice -> Controls tool usage " Auto or REQUIRD"
    * toolCallbacks -> Java callbacks executed when a tool is called.
    * toolContext -> Extra data passed to tools. "Tool receives context without exposing it to the model prompt."
*/
