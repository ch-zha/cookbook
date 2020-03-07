package com.cookbook.data.api;

import com.google.gson.annotations.SerializedName;

public class ApiRecipe {

    @SerializedName("idMeal")
    private String mealId;

    @SerializedName("strMeal")
    private String mealName;

    public ApiRecipe(String mealId, String mealName) {
        this.mealId = mealId;
        this.mealName = mealName;
    }

    public String getMealId() {
        return mealId;
    }

    public String getMealName() {
        return mealName;
    }

}
