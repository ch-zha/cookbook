package com.cookbook.data;

import com.cookbook.data.entities.Ingredient;
import com.cookbook.data.entities.Meal;
import com.cookbook.data.entities.MeasurementUnit;
import com.cookbook.data.entities.Recipe;
import com.cookbook.data.entities.Step;

import java.util.List;
import java.util.Random;

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

    public List<Recipe> getAllRecipes() {
        return recipeDao.getAllRecipes();
    }

    /**** Ingredients Table ****/

    public void fillRecipeWithSampleIngredients(int id) {
        recipeDao.deleteIngredientsFromRecipe(id);
        recipeDao.addIngredient(new Ingredient(id, "Ingredient 1", 2, MeasurementUnit.Whole));
        recipeDao.addIngredient(new Ingredient(id, "Ingredient 2", 250, MeasurementUnit.Gram));
        recipeDao.addIngredient(new Ingredient(id, "Ingredient 3", 3, MeasurementUnit.TBSP));
    }

    public List<Ingredient> getIngredientsForRecipe(int id) {
        return recipeDao.getIngredientsForRecipe(id);
    }

    /**** Steps Table ****/

    public void fillRecipeWithSampleSteps(int id) {
        recipeDao.deleteStepsFromRecipe(id);
        recipeDao.addStepToRecipe(id, "Step 1 is to do something");
        recipeDao.addStepToRecipe(id, "Step 2 is to do something else");
        recipeDao.addStepToRecipe(id, "Step 3 is to do a different thing");
    }

    public List<Step> getStepsForRecipe(int id) {
        return recipeDao.getStepsForRecipe(id);
    }

    /**** Planner Table ****/

    public void createSamplePlannerForDay(int day) {

        List<Recipe> recipes = getAllRecipes();
        if (recipes.size() < 1) return;

        recipeDao.clearDayInPlanner(day);
        Random rand = new Random();
        recipeDao.addMealToDay(day, recipes.get(rand.nextInt(recipes.size())).getId());
        recipeDao.addMealToDay(day, recipes.get(rand.nextInt(recipes.size())).getId());

    }

    public List<Meal> getMealsForDay(int day) {
        return recipeDao.getMealsForDay(day);
    }

}
