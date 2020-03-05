package com.cookbook.viewmodel.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;
import com.cookbook.ui.EditRecipeActivity;

public class UpdateRecipeService extends IntentService {

    public enum Action {
        UPDATE,
        ADD,
        DELETE
    }

    Repository repository;

    private Action action;
    private long recipe_id;
    private String recipe_name;

    public UpdateRecipeService() {
        super("UpdateRecipeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        RecipeDao dao = RecipeDatabase.getInstance(getApplicationContext()).getRecipeDao();
        this.repository = new Repository(dao);
        this.action = (Action) intent.getSerializableExtra("action");

        switch (action) {
            case UPDATE:
                break;
            case ADD:
                this.recipe_name = intent.getStringExtra("recipe_name");
                recipe_id = repository.addRecipe(recipe_name);
                break;
            case DELETE:
                break;
            default:
                System.err.println("Unrecognized action in UpdateStepsService");
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (action == Action.ADD) {
            Intent newRecipe = new Intent(this, EditRecipeActivity.class);
            newRecipe.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //NOTE long to int conversion could cause issues if DB is too big, but unlikely
            newRecipe.putExtra("recipe_id", Math.toIntExact(recipe_id));
            newRecipe.putExtra("recipe_name", recipe_name);
            startActivity(newRecipe);
//                overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
        }
    }
}
