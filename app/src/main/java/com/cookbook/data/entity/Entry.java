package com.cookbook.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "planner")
public class Entry {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int mealId;

    private int day;

    private int place;

    @ColumnInfo(name = "recipe_id")
    private int recipeId;

    public Entry(int mealId, int day, int place, int recipeId) {
        this.mealId = mealId;
        this.day = day;
        this.place = place;
        this.recipeId = recipeId;
    }

    @Ignore
    public Entry(int day, int recipeId) {
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
