package com.example.cookbook;

import com.cookbook.viewmodel.Ingredient;
import com.cookbook.viewmodel.Recipe;
import org.junit.Assert;
import org.junit.Test;

public class RecipeTest {

    @Test
    public void testRecipe() {
        Recipe recipe = new Recipe("Recipe");
        Ingredient cheese = new Ingredient("Cheese");
        Assert.assertFalse(recipe.addIngredient(cheese, 1f));
        Assert.assertTrue(recipe.addIngredient(cheese, 2f));
        Assert.assertEquals("Cheese has quantity 2", recipe.getIngredientQuantity(cheese), 2f, 0f);
    }
}
