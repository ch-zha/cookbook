package com.cookbook.viewmodel.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;

public class UpdateStepsService extends IntentService {

    public enum Action {
        UPDATE,
        ADD,
        DELETE,
        REORDER
    }

    Repository repository;

    public UpdateStepsService() {
        super("UpdateStepsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        RecipeDao dao = RecipeDatabase.getInstance(getApplicationContext()).getRecipeDao();
        this.repository = new Repository(dao);

        int stepId = intent.getIntExtra("step_id", -1);
        int recipeId = intent.getIntExtra("recipe_id", -1);
        String instructions = intent.getStringExtra("instructions");

        switch ((Action) intent.getSerializableExtra("action")) {
            case UPDATE:
                repository.updateStep(stepId, instructions);
                break;
            case ADD:
                repository.addStep(instructions, recipeId);
                break;
            case DELETE:
                repository.deleteStep(stepId);
                break;
            case REORDER:
                break;
            default:
                System.err.println("Unrecognized action in UpdateStepsService");
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
