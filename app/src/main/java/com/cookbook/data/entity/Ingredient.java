package com.cookbook.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

@TypeConverters({MeasurementUnit.class})
@Entity(tableName = "ingredients",
        primaryKeys = {"recipe_id", "name"},
        foreignKeys = {@ForeignKey(
                entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipe_id",
                onDelete = ForeignKey.CASCADE)
        })
public class Ingredient {

    @ColumnInfo(name = "recipe_id")
    private final int recipeId;

    @NonNull
    private final String name;

    private final double quantity;

    private final MeasurementUnit unit;

    public Ingredient(int recipeId, String name, double quantity, MeasurementUnit unit) {
        this.recipeId = recipeId;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public MeasurementUnit getUnit() {
        return unit;
    }
}
