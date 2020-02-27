package com.cookbook.event;

import com.cookbook.viewmodel.Recipe;

public class RecipeMessageEvent {

    public Recipe recipe;

    public RecipeMessageEvent(Recipe recipe) {
        this.recipe = recipe;
    }
}
