package com.cookbook.viewmodel.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;
import com.cookbook.data.entity.MeasurementUnit;

public class UpdateIngredientsService extends IntentService {

    public static final String RECIPE_ID_KEY = "recipe_id";
    public static final String ING_NAME_KEY = "ingredient_name";
    public static final String ING_NEW_NAME_KEY = "new_name";
    public static final String ING_QUANT_KEY = "quantity";
    public static final String ING_UNIT_KEY = "unit";
    public static final String ACTION_KEY = "action";

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

        Action action = (Action) intent.getSerializableExtra(ACTION_KEY);
        int recipe_id = intent.getIntExtra(RECIPE_ID_KEY, -1);
        String ingredient_name = intent.getStringExtra(ING_NAME_KEY);
        double quantity = intent.getDoubleExtra(ING_QUANT_KEY, 0);
        MeasurementUnit unit = (MeasurementUnit) intent.getSerializableExtra(ING_UNIT_KEY);

        switch (action) {
            case UPDATE_NAME:
                String new_name = intent.getStringExtra(ING_NEW_NAME_KEY);
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
                Log.e("UpdateRecipeService", "Unrecognized action: " + action.toString());
                break;
        }
    }

}
