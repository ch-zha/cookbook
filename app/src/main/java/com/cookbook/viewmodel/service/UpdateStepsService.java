package com.cookbook.viewmodel.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;

public class UpdateStepsService extends IntentService {

    public static final String ACTION_KEY = "action";
    public static final String STEP_ID_KEY = "step_id";
    public static final String RECIPE_ID_KEY = "recipe_id";
    public static final String INSTR_KEY = "instructions";

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

        int stepId = intent.getIntExtra(STEP_ID_KEY, -1);
        int recipeId = intent.getIntExtra(RECIPE_ID_KEY, -1);
        String instructions = intent.getStringExtra(INSTR_KEY);
        Action action = (Action) intent.getSerializableExtra(ACTION_KEY);

        switch (action) {
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
                //TODO
                break;
            default:
                Log.d("UpdateStepsService", "Unrecognized action: " + action);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
