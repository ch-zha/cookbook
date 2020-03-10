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

    /**** Shopping List Query ****/
    //TODO this may need to be changed if variable # planner days is allowed
    @Query("SELECT planner.recipe_id, " +
                "ingredients.name, " +
                "sum(ingredients.quantity) as quantity, " +
                "ingredients.unit " +
            "FROM planner " +
            "INNER JOIN ingredients ON ingredients.recipe_id=planner.recipe_id " +
            "GROUP BY ingredients.name, ingredients.unit " +
            "ORDER BY ingredients.name")
    LiveData<List<Ingredient>> getShoppingList();

    /**** Recipes Table ****/

    @Query("SELECT * FROM recipes " +
            "ORDER BY name")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT name, id, thumb " +
            "FROM recipes " +
            "WHERE name LIKE :query " +
            "ORDER BY name")
    Cursor getRecipesLike(String query);

    @Query("SELECT name from recipes " +
            "WHERE id=:id")
    LiveData<String> getRecipeName(int id);

    @Query("SELECT thumb FROM recipes " +
            "WHERE id=:recipe_id")
    LiveData<String> getRecipeImg(int recipe_id);

    @Insert
    long addRecipe(Recipe recipe);

    @Query("DELETE FROM recipes WHERE id=:recipe_id")
    void deleteRecipe(int recipe_id);

    @Query("DELETE FROM recipes")
    void deleteAll();

    /**** Ingredients Table ****/
    @Query("SELECT * FROM ingredients " +
            "WHERE recipe_id = :id " +
            "ORDER BY name")
    LiveData<List<Ingredient>> getIngredientsForRecipe(int id);

    @Insert
    void addIngredient(Ingredient ingredient);

    @Query("UPDATE ingredients " +
            "SET name=:new_name " +
            "WHERE recipe_id=:recipe_id AND name=:old_name")
    void updateIngredientName(int recipe_id, String old_name, String new_name);

    @Query("UPDATE ingredients " +
            "SET unit=:unit " +
            "WHERE recipe_id=:recipe_id AND name=:ingredient_name")
    void updateIngredientUnit(int recipe_id, String ingredient_name, String unit);

    @Query("UPDATE ingredients " +
            "SET quantity=:quantity " +
            "WHERE recipe_id=:recipe_id AND name=:ingredient_name")
    void updateIngredientQuantity(int recipe_id, String ingredient_name, double quantity);

    @Query("UPDATE ingredients " +
            "SET quantity = " +
                "((SELECT quantity FROM ingredients WHERE recipe_id=:recipe_id AND name=:ingredient_name) " +
                "+ :quantity) " +
            "WHERE recipe_id=:recipe_id AND name=:ingredient_name")
    void addIngredientQuantity(int recipe_id, String ingredient_name, double quantity);

    @Query("DELETE FROM ingredients WHERE recipe_id = :recipe_id AND name = :ingredient_name")
    void deleteIngredientFromRecipe(int recipe_id, String ingredient_name);

    /**** Steps Table ****/

    @Query("SELECT * FROM steps " +
            "WHERE recipe_id = :id " +
            "ORDER BY place")
    LiveData<List<Step>> getStepsForRecipe(int id);

    @Query("UPDATE steps " +
            "SET instructions=:step " +
            "WHERE step_id=:step_id")
    void updateStep(String step, int step_id);

    @Query("INSERT INTO steps (recipe_id, place, instructions) VALUES (:id, " +
            "(SELECT COUNT(place) FROM steps WHERE recipe_id = :id)+1, " +
            ":step)")
    void addStepToRecipe(int id, String step);

    @Query("DELETE FROM steps WHERE step_id = :step_id")
    void deleteStepFromRecipe(int step_id);

    /**** Planner Table ****/

    @Query("SELECT * FROM planner " +
            "WHERE day IN (:days) " +
            "ORDER BY day, recipe_id ")
    LiveData<List<Entry>> getMealsForDays(int[] days);

    //TODO fix place
    @Query("INSERT INTO planner (day, place, recipe_id) VALUES (:day, " +
            "(SELECT COUNT(place) FROM planner WHERE day = :day)+1, " +
            ":recipe_id)")
    void addMealToDay(int day, int recipe_id);

    @Query("DELETE FROM planner WHERE id = :meal_id")
    void removeMeal(int meal_id);

    @Query("DELETE FROM planner WHERE day = :day")
    void clearDayInPlanner(int day);

}
