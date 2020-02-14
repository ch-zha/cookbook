package com.example.cookbook;

import com.cookbook.model.Recipe;
import org.junit.Assert;
import org.junit.Test;

public class RecipeTest {

    @Test
    public void testRecipe() {
        Recipe recipe = new Recipe();
        Assert.assertFalse(recipe.addIngredient("Cheese", 1f));
        Assert.assertTrue(recipe.addIngredient("Cheese", 2f));
        Assert.assertEquals("Cheese has quantity 2", recipe.getIngredientQuantity("cheese"), 2f, 0f);
    }
}
