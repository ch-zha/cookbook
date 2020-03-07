package com.cookbook.data;

import androidx.lifecycle.LiveData;

import com.cookbook.data.entity.Ingredient;
import com.cookbook.data.entity.Entry;
import com.cookbook.data.entity.MeasurementUnit;
import com.cookbook.data.entity.Recipe;
import com.cookbook.data.entity.Step;

import java.util.List;

public class Repository {

    private RecipeDao recipeDao;

    public Repository(RecipeDao recipe_dao) {
        this.recipeDao = recipe_dao;
    }

    /**** Recipes Table ****/

    public void repopulate() {
        recipeDao.deleteAll();
        recipeDao.addRecipe(new Recipe("Brownies"));
        recipeDao.addRecipe(new Recipe("Beef Chili"));
        recipeDao.addRecipe(new Recipe("Chicken Pot Pie"));
        recipeDao.addRecipe(new Recipe("Roast Chicken"));
        recipeDao.addRecipe(new Recipe("Noodle Soup"));
        recipeDao.addRecipe(new Recipe("Steamed Cauliflower"));
        recipeDao.addRecipe(new Recipe("Fried Tofu"));
        recipeDao.addRecipe(new Recipe("Stir-fried Green Beans"));
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return recipeDao.getAllRecipes();
    }

    public LiveData<String> getRecipeName(int id) {
        return recipeDao.getRecipeName(id);
    }

    public long addRecipe(String name) {
        return recipeDao.addRecipe(new Recipe(name));
    }

    /**** Ingredients Table ****/

    public LiveData<List<Ingredient>> getIngredientsForRecipe(int id) {
        return recipeDao.getIngredientsForRecipe(id);
    }

    public void addIngredient(String name, int recipe_id, double quantity, MeasurementUnit unit) {
        recipeDao.addIngredient(new Ingredient(recipe_id, name, quantity, unit));
    }

    public void updateIngredientName(String old_name, String new_name, int recipe_id) {
        recipeDao.updateIngredientName(recipe_id, old_name, new_name);
    }

    public void updateIngredientUnit(String ingredient_name, int recipe_id, MeasurementUnit unit) {
        recipeDao.updateIngredientUnit(recipe_id, ingredient_name, MeasurementUnit.getMeasurementUnitString(unit));
    }

    public void updateIngredientQuantity(String ingredient_name, int recipe_id, double quantity) {
        recipeDao.updateIngredientQuantity(recipe_id, ingredient_name, quantity);
    }

    /**** Steps Table ****/

    public LiveData<List<Step>> getStepsForRecipe(int id) {
        return recipeDao.getStepsForRecipe(id);
    }

    public void updateStep(int step_id, String instructions) {
        recipeDao.updateStep(instructions, step_id);
    }

    public void addStep(String instructions, int recipe_id) {
        recipeDao.addStepToRecipe(recipe_id, instructions);
    }

    /**** Planner Table ****/

    public LiveData<List<Entry>> getMealsForDays(int[] days) {
        return recipeDao.getMealsForDays(days);
    }

    public void addMealToDay(int day, int recipe_id) {
        recipeDao.addMealToDay(day, recipe_id);
    }

    public void removeMeal(int meal_id) {
        recipeDao.removeMeal(meal_id);
    }

}
