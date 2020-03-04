package com.cookbook.viewmodel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;
import com.cookbook.viewmodel.livedata.RecipeIngredientsLiveData;
import com.cookbook.viewmodel.livedata.RecipeStepsLiveData;

public class RecipeDetailViewModel extends AndroidViewModel {

    Repository repository;
    private int recipeId;

    private RecipeIngredientsLiveData ingredients;
    private RecipeStepsLiveData steps;

    public RecipeDetailViewModel(@NonNull Application application) {
        super(application);
        RecipeDao dao = RecipeDatabase.getInstance(application.getApplicationContext()).getRecipeDao();
        repository = new Repository(dao);
    }

    public void setRecipeId(int id) {
        this.recipeId = id;
        this.ingredients = new RecipeIngredientsLiveData(repository, recipeId);
        this.steps = new RecipeStepsLiveData(repository, recipeId);
    }

    public RecipeIngredientsLiveData getIngredients() {
        return this.ingredients;
    }

    public RecipeStepsLiveData getSteps() {
        return this.steps;
    }
}
