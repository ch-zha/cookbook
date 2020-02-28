package com.cookbook.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cookbook.data.entities.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipes")
    public List<Recipe> getAllRecipes();

    @Insert
    public void addRecipe(Recipe recipe);

    @Query("DELETE FROM recipes")
    public void deleteAll();

}
