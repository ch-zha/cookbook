package com.cookbook.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "planner")
public class Meal {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int mealId;

    @NonNull
    private int day;

    @NonNull
    private int place;

    @ColumnInfo(name = "recipe_id")
    @NonNull
    private int recipeId;

    public Meal(int mealId, int day, int place, int recipeId) {
        this.mealId = mealId;
        this.day = day;
        this.place = place;
        this.recipeId = recipeId;
    }

    @Ignore
    public Meal(int day, int recipeId) {
        this.day = day;
        this.recipeId = recipeId;
    }

    public int getMealId() {
        return mealId;
    }

    public int getDay() {
        return day;
    }

    public int getPlace() {
        return place;
    }

    public int getRecipeId() {
        return recipeId;
    }


}
