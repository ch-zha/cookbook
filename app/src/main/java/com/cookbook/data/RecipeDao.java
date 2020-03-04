package com.cookbook.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cookbook.data.entities.Ingredient;
import com.cookbook.data.entities.Meal;
import com.cookbook.data.entities.Recipe;
import com.cookbook.data.entities.Step;

import java.util.List;

@Dao
public interface RecipeDao {

    /**** Recipes Table ****/

    @Query("SELECT * FROM recipes")
    public List<Recipe> getAllRecipes();

    @Insert
    public void addRecipe(Recipe recipe);

    @Query("DELETE FROM recipes")
    public void deleteAll();

    /**** Ingredients Table ****/
    @Query("SELECT * FROM ingredients WHERE recipe_id = :id")
    public List<Ingredient> getIngredientsForRecipe(int id);

    @Insert
    public void addIngredient(Ingredient ingredient);

    @Query("DELETE FROM ingredients WHERE recipe_id = :id")
    public void deleteIngredientsFromRecipe(int id);

    /**** Steps Table ****/

    @Query("SELECT * FROM steps WHERE recipe_id = :id")
    public List<Step> getStepsForRecipe(int id);

    @Query("INSERT INTO steps VALUES (:id, " +
            "(SELECT COUNT(step_no) FROM steps WHERE recipe_id = :id)+1, " +
            ":step)")
    public void addStepToRecipe(int id, String step);

    @Query("DELETE FROM steps WHERE recipe_id = :id")
    public void deleteStepsFromRecipe(int id);

    /**** Planner Table ****/

    @Query("SELECT * FROM planner WHERE day = :day")
    public List<Meal> getMealsForDay(int day);

    @Query("INSERT INTO planner (day, place, recipe_id) VALUES (:day, " +
            "(SELECT COUNT(place) FROM planner WHERE day = :day)+1, " +
            ":recipe_id)")
    public void addMealToDay(int day, int recipe_id);

    @Query("DELETE FROM planner WHERE day = :day")
    public void clearDayInPlanner(int day);

}
