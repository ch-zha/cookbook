package com.cookbook.data;

import com.cookbook.data.dao.RecipeDao;
import com.cookbook.data.entities.Recipe;

import java.util.List;

public class Repository {

    private RecipeDao recipeDao;

    public Repository(RecipeDao recipe_dao) {
        this.recipeDao = recipe_dao;
    }

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

}
