package com.example.cookbook;

import java.util.ArrayList;
import java.util.List;

public class RecipeListItem {

    private String name;

    public RecipeListItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<RecipeListItem> createSampleRecipeList() {

        ArrayList<RecipeListItem> list =  new ArrayList<>();

        list.add(new RecipeListItem("Pumpkin Pie"));
        list.add(new RecipeListItem("Mashed Potatoes"));
        list.add(new RecipeListItem("Roast Beef Sandwich"));
        list.add(new RecipeListItem("Minestrone Soup"));
        list.add(new RecipeListItem("Lamb Hot Pot"));
        list.add(new RecipeListItem("Sashimi Plate"));
        list.add(new RecipeListItem("Poke Bowl"));
        list.add(new RecipeListItem("Beef Chili"));
        list.add(new RecipeListItem("Jasmine Milk Tea"));
        list.add(new RecipeListItem("Apple Pie"));
        list.add(new RecipeListItem("Brownies"));
        list.add(new RecipeListItem("Seafood Soup"));
        list.add(new RecipeListItem("Fried Rice"));

        return list;
    }
}
