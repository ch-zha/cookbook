package com.cookbook.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recipe {

    enum Measurement {
        Whole,
        Gram,
        TBSP
    }

    private HashMap<String, Float> ingredients = null;
    private List<String> steps = null;


    public Recipe() {
        ingredients = new HashMap<>();
        steps = new ArrayList<>();
    }

    public float getIngredientQuantity(String ingredient) {
        return ingredients.get(ingredient.toLowerCase());
    }

    /* Returns true if ingredient was already present (modify), false if ingredient was not (add) */
    public boolean addIngredient(String ingredient, float quantity) {
        //Change to lowercase to prevent duplicate ingredients with different cases from being added.
        Float old_value = ingredients.put(ingredient.toLowerCase(), quantity);
        return (old_value != null);
    }

    public static Recipe createSampleRecipe() {
        Recipe recipe = new Recipe();
        recipe.addIngredient("Banana", 1f);
        recipe.addIngredient("Ice Cream", 2f);
        recipe.addIngredient("Cherry", 1f);
        return recipe;
    }

}
