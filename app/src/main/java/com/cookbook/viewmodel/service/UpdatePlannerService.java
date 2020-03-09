package com.cookbook.viewmodel.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;
import com.cookbook.ui.EditRecipeActivity;

public class UpdatePlannerService extends IntentService {

    public static final String RECIPE_ID_KEY = "recipe_id";
    public static final String DAY_KEY = "day";
    public static final String ENTRY_ID_KEY = "entry_id";
    public static final String ACTION_KEY = "action";

    public enum Action {
        UPDATE,
        ADD,
        DELETE
    }

    Repository repository;

    private Action action;

    public UpdatePlannerService() {
        super("UpdatePlannerService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        RecipeDao dao = RecipeDatabase.getInstance(getApplicationContext()).getRecipeDao();
        this.repository = new Repository(dao);
        this.action = (Action) intent.getSerializableExtra(ACTION_KEY);
        int day = intent.getIntExtra(DAY_KEY, -1);

        switch (action) {
            case UPDATE:
                break;
            case ADD:
                int recipe_id = intent.getIntExtra(RECIPE_ID_KEY, -1);
                repository.addMealToDay(day, recipe_id);
                break;
            case DELETE:
                int meal_id = intent.getIntExtra(ENTRY_ID_KEY, -1);
                repository.removeMeal(meal_id);
                break;
            default:
                Log.e("UpdatePlannerService", "Unrecognized action: " + action.toString());
                break;
        }
    }
}
