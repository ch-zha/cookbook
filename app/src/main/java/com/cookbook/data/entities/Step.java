package com.cookbook.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "steps",
        primaryKeys = {"recipe_id", "step_no"},
        foreignKeys = {@ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipe_id",
                onDelete = ForeignKey.CASCADE)})
public class Step {

    @ColumnInfo(name = "recipe_id")
    private final int recipeId;

    @ColumnInfo(name = "step_no", defaultValue = "1")
    private final int stepNo;

    private final String instructions;

    public Step(int recipeId, int stepNo, String instructions) {
        this.recipeId = recipeId;
        this.stepNo = stepNo;
        this.instructions = instructions;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getStepNo() {
        return stepNo;
    }

    public String getInstructions() {
        return instructions;
    }
}
