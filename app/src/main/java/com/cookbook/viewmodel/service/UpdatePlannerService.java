package com.cookbook.viewmodel.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;
import com.cookbook.ui.EditRecipeActivity;

public class UpdatePlannerService extends IntentService {

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
        this.action = (Action) intent.getSerializableExtra("action");
        int day = intent.getIntExtra("day", -1);

        switch (action) {
            case UPDATE:
                break;
            case ADD:
                int recipe_id = intent.getIntExtra("recipe_id", -1);
                repository.addMealToDay(day, recipe_id);
                System.out.println("Add " + recipe_id + "to day " + day);
                break;
            case DELETE:
                int meal_id = intent.getIntExtra("entry_id", -1);
                repository.removeMeal(meal_id);
                System.out.println("Remove meal " + meal_id);
                break;
            default:
                System.err.println("Unrecognized action in UpdatePlannerService");
                break;
        }
    }
}
