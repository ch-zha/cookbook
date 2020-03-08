package com.cookbook.data.api;

import com.cookbook.data.entity.MeasurementUnit;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ApiRecipe {

    //Only used for displaying results
    @SerializedName("strMeal")
    private String mealName;

    @SerializedName("idMeal")
    private String mealId;

    //Used for everything else
    private List<String> steps;
    private List<String> ingredients;
    private List<Double> quantity;
    private List<MeasurementUnit> units;
    private String imageUrl;

    private boolean unreadableFields = false;

    ApiRecipe() {

        this.ingredients = new ArrayList<>();
        this.quantity = new ArrayList<>();
        this.units = new ArrayList<>();
        this.steps = new ArrayList<>();

    }

    void setMealName(String mealName) {
        this.mealName = mealName;
    }

    void setMealId(String mealId) {
        this.mealId = mealId;
    }

    void addSteps(List<String> steps) {
        this.steps.addAll(steps);
    }

    void addImageUrl(String url) {
        this.imageUrl = url;
    }

    void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addQuantity(Double quantity) {
        this.quantity.add(quantity);
    }

    public void addUnit(MeasurementUnit unit) {
        this.units.add(unit);
    }

    public void setUnreadableFields(boolean unreadableFields) {
        this.unreadableFields = unreadableFields;
    }

    /** Getters **/

    public String getMealName() {
        return mealName;
    }

    public String getMealId() {
        return mealId;
    }

    public List<String> getSteps() {
        return steps;
    }

    public String getImageurl() {
        return imageUrl;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<Double> getQuantity() {
        return quantity;
    }

    public List<MeasurementUnit> getUnits() {
        return units;
    }

    public boolean hasUnreadableFields() {
        return unreadableFields;
    }
}
