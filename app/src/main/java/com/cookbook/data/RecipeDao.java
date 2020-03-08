package com.cookbook.data;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cookbook.data.entity.Entry;
import com.cookbook.data.entity.Ingredient;
import com.cookbook.data.entity.Recipe;
import com.cookbook.data.entity.Step;

import java.util.List;

@Dao
public interface RecipeDao {

    /**** Recipes Table ****/

    @Query("SELECT * FROM recipes " +
            "ORDER BY name")
    public LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT name, id " +
            "FROM recipes " +
            "WHERE name LIKE :query " +
            "ORDER BY name")
    public Cursor getRecipesLike(String query);

    @Query("SELECT name from recipes " +
            "WHERE id=:id")
    public LiveData<String> getRecipeName(int id);

    @Insert
    public long addRecipe(Recipe recipe);

    @Query("DELETE FROM recipes")
    public void deleteAll();

    /**** Ingredients Table ****/
    @Query("SELECT * FROM ingredients WHERE recipe_id = :id")
    public LiveData<List<Ingredient>> getIngredientsForRecipe(int id);

    @Insert
    public void addIngredient(Ingredient ingredient);

    @Query("UPDATE ingredients " +
            "SET name=:new_name " +
            "WHERE recipe_id=:recipe_id AND name=:old_name")
    public void updateIngredientName(int recipe_id, String old_name, String new_name);

    @Query("UPDATE ingredients " +
            "SET unit=:unit " +
            "WHERE recipe_id=:recipe_id AND name=:ingredient_name")
    public void updateIngredientUnit(int recipe_id, String ingredient_name, String unit);

    @Query("UPDATE ingredients " +
            "SET quantity=:quantity " +
            "WHERE recipe_id=:recipe_id AND name=:ingredient_name")
    public void updateIngredientQuantity(int recipe_id, String ingredient_name, double quantity);

    @Query("DELETE FROM ingredients WHERE recipe_id = :recipe_id AND name = :ingredient_name")
    public void deleteIngredientFromRecipe(int recipe_id, String ingredient_name);

    /**** Steps Table ****/

    @Query("SELECT * FROM steps " +
            "WHERE recipe_id = :id " +
            "ORDER BY place")
    public LiveData<List<Step>> getStepsForRecipe(int id);

    @Query("UPDATE steps " +
            "SET instructions=:step " +
            "WHERE step_id=:step_id")
    public void updateStep(String step, int step_id);

    @Query("INSERT INTO steps (recipe_id, place, instructions) VALUES (:id, " +
            "(SELECT COUNT(place) FROM steps WHERE recipe_id = :id)+1, " +
            ":step)")
    public void addStepToRecipe(int id, String step);

    @Query("DELETE FROM steps WHERE step_id = :step_id")
    public void deleteStepFromRecipe(int step_id);

    /**** Planner Table ****/

    @Query("SELECT * FROM planner " +
            "WHERE day IN (:days) " +
            "ORDER BY day, recipe_id")
    public LiveData<List<Entry>> getMealsForDays(int[] days);

    //TODO fix place
    @Query("INSERT INTO planner (day, place, recipe_id) VALUES (:day, " +
            "(SELECT COUNT(place) FROM planner WHERE day = :day)+1, " +
            ":recipe_id)")
    public void addMealToDay(int day, int recipe_id);

    @Query("DELETE FROM planner WHERE id = :meal_id")
    public void removeMeal(int meal_id);

    @Query("DELETE FROM planner WHERE day = :day")
    public void clearDayInPlanner(int day);

}
