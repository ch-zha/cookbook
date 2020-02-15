package com.cookbook.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recipe {

    private String name = null;
    private HashMap<Ingredient, Float> ingredients = null;
    private List<String> steps = null;


    public Recipe(String name) {
        this.name = name.replaceAll("[^A-Za-z0-9 ]","");
        this.ingredients = new HashMap<>();
        this.steps = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public float getIngredientQuantity(Ingredient ingredient) {
        return ingredients.get(ingredient);
    }

    /* Returns true if ingredient was already present (modify), false if ingredient was not (add) */
    public boolean addIngredient(Ingredient ingredient, float quantity) {
        //Change to lowercase to prevent duplicate ingredients with different cases from being added.
        Float old_value = ingredients.put(ingredient, quantity);
        return (old_value != null);
    }

    public void addStep(String step) {
        this.steps.add(step);
    }

    public static Recipe createSampleRecipe() {
        Recipe recipe = new Recipe("Sample");
        recipe.addIngredient(new Ingredient("Banana"), 1f);
        recipe.addIngredient(new Ingredient("Ice Cream"), 2f);
        recipe.addIngredient(new Ingredient("Cherry"), 1f);
        return recipe;
    }

}
