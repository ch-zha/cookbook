package com.cookbook.ui.listener;

import android.view.View;

import com.cookbook.data.entity.Ingredient;
import com.cookbook.data.entity.MeasurementUnit;

public interface EditIngredientListener {

    void onAddIngredient(String ingredient_name, double quantity, MeasurementUnit unit);

    void onDeleteIngredient(String ingredient_name);

    void onUpdateIngredientName(String ingredient_name, String new_name);

    void onUpdateIngredientQuantity(String ingredient_name, double quantity);

    void onUpdateIngredientUnit(String ingredient_name, MeasurementUnit unit);

    void onClickUnitButton(View view, Ingredient ingredient);

}
