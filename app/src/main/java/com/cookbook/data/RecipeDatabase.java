package com.cookbook.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cookbook.data.entities.Ingredient;
import com.cookbook.data.entities.Meal;
import com.cookbook.data.entities.Recipe;
import com.cookbook.data.entities.Step;

@Database(  version = 4,
            exportSchema = true,
            entities = {Recipe.class, Ingredient.class, Step.class, Meal.class})
public abstract class RecipeDatabase extends RoomDatabase {

    private static final String db_name = "recipes_db";

    private static RecipeDatabase INSTANCE;

    public abstract RecipeDao getRecipeDao();

    public static RecipeDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecipeDatabase.class, db_name).fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

}
