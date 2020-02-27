package com.cookbook.viewmodel;

import com.cookbook.viewmodel.RecipeListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MenuDay {

    private String day = null;
    private List<RecipeListItem> menu_items = null;

    public MenuDay(String day) {
        this.day = day;
        this.menu_items = new ArrayList<>();
    }

    public String getDayName() {
        return this.day;
    }

    public List<RecipeListItem> getMeals() {
        return this.menu_items;
    }

    public void addMeal(RecipeListItem recipe) {
        this.menu_items.add(recipe);
    }

    public void addSampleMeals() {
        List<RecipeListItem> sample = RecipeListItem.createSampleRecipeList();
        Random rand = new Random();
        int num_meals = rand.nextInt(3);
        num_meals++;
        for (int i = 0; i < num_meals; i++) {
            int index = rand.nextInt(sample.size());
            this.menu_items.add(sample.get(index));
        }
    }
}
