package com.cookbook.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cookbook.viewmodel.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipes")
    public abstract List<Recipe> getRecipeIndex();

    @Insert(entity = Recipe.class)
    public abstract void addRecipe(String name);

}
