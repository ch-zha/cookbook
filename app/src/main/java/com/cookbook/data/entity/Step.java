package com.cookbook.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "steps",
        foreignKeys = {@ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipe_id",
                onDelete = ForeignKey.CASCADE)})
public class Step {

    @ColumnInfo(name = "recipe_id", index = true)
    private final int recipeId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "step_id")
    private final int stepId;

    @ColumnInfo(defaultValue = "1")
    private final int place;

    private final String instructions;

    public Step(int recipeId, int stepId, int place, String instructions) {
        this.recipeId = recipeId;
        this.stepId = stepId;
        this.place = place;
        this.instructions = instructions;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getStepId() {
        return stepId;
    }

    public int getPlace() {
        return getPlace();
    }

    public String getInstructions() {
        return instructions;
    }
}
