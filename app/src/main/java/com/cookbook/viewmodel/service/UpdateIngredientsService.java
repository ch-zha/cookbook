package com.cookbook.viewmodel.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;
import com.cookbook.data.entity.MeasurementUnit;

public class UpdateIngredientsService extends IntentService {

    public enum Action {
        UPDATE_NAME,
        UPDATE_UNIT,
        UPDATE_QUANTITY,
        ADD,
        DELETE
    }

    Repository repository;

    public UpdateIngredientsService() {
        super("UpdateIngredientsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        RecipeDao dao = RecipeDatabase.getInstance(getApplicationContext()).getRecipeDao();
        this.repository = new Repository(dao);

        Action action = (Action) intent.getSerializableExtra("action");
        int recipe_id = intent.getIntExtra("recipe_id", -1);
        String ingredient_name = intent.getStringExtra("ingredient_name");
        double quantity = intent.getDoubleExtra("quantity", 0);
        MeasurementUnit unit = (MeasurementUnit) intent.getSerializableExtra("unit");

        switch (action) {
            case UPDATE_NAME:
                String new_name = intent.getStringExtra("new_name");
                repository.updateIngredientName(ingredient_name, new_name, recipe_id);
                break;
            case UPDATE_UNIT:
                repository.updateIngredientUnit(ingredient_name, recipe_id, unit);
                break;
            case UPDATE_QUANTITY:
                repository.updateIngredientQuantity(ingredient_name, recipe_id, quantity);
                break;
            case ADD:
                repository.addIngredient(ingredient_name, recipe_id, quantity, unit);
                break;
            case DELETE:
                repository.deleteIngredientFromRecipe(ingredient_name, recipe_id);
                break;
            default:
                System.err.println("Unrecognized action in UpdateRecipeService");
                break;
        }
    }

}
