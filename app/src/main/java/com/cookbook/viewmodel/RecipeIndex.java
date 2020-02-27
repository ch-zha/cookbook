package com.cookbook.viewmodel;

import java.util.HashMap;

public class RecipeIndex {

    private HashMap<Integer, String> recipeIndex = null;

    public RecipeIndex() {
        this.recipeIndex = new HashMap<>();
        recipeIndex.put(1, "Chocolate Cake");
        recipeIndex.put(2, "Chicken Noodle Soup");
        recipeIndex.put(3, "Pasta Salad");
        recipeIndex.put(4, "Hash Browns");
    }

    public Recipe getRecipe(int id) {
        String recipeName = this.recipeIndex.get(id);
        //TODO implement with a real recipe fetch
        return buildSampleRecipe(recipeName);
    }

    private Recipe buildSampleRecipe(String id) {
        Recipe recipe = new Recipe(id);

        recipe.addIngredient(new Ingredient("Ingredient 1"), 2);
        recipe.addIngredient(new Ingredient("Ingredient 2"), 4);
        recipe.addIngredient(new Ingredient("Ingredient 3"), 1);

        recipe.addStep("Do something here");
        recipe.addStep("Do something else here");
        recipe.addStep("Do yet another thing here");

        return recipe;
    }

}
