package com.ai.SpringAIDemo.Recipe_Generation;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RecipeService {

    private final ChatModel chatModel;

    public RecipeService(@Qualifier(value = "openAiChatModel") ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String createRecipe(String ingredients, String cuisine, String dietaryRestrictions) {
        String template = """
                Generate a recipe with the following details:
                
                Ingredients available: {%s}
                Cuisine type: {%s}
                Dietary restrictions: {%s}
                
                Please return the recipe in this format:
                - Recipe name
                - Servings
                - Prep time & cook time
                - Ingredients with exact quantities
                - Step-by-step instructions
                - Optional tips or variations
                """.formatted(ingredients, cuisine, dietaryRestrictions);
        ChatResponse chatResponse;
        try {
            chatResponse = chatModel.call(new Prompt(template));
        } catch (Exception e) {
            return e.getMessage();
        }
        return Objects.requireNonNull(chatResponse.getResult()).getOutput().getText();
    }
}
