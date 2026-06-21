package com.ai.SpringAIDemo.Recipe_Generation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/generate-recipe")
    public String generateRecipe(@RequestParam String ingredients,
                                 @RequestParam(required = false, defaultValue = "any") String cuisine,
                                 @RequestParam(required = false, defaultValue = "any")  String dietaryRestrictions) {
        return recipeService.createRecipe(ingredients,cuisine,dietaryRestrictions);
    }
}
