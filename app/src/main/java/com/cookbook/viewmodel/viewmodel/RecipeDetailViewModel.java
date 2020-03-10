package com.cookbook.viewmodel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;
import com.cookbook.data.entity.Ingredient;
import com.cookbook.data.entity.Step;

import java.util.List;

public class RecipeDetailViewModel extends AndroidViewModel {

    Repository repository;
    private int recipeId;

    private LiveData<List<Ingredient>> ingredients;
    private LiveData<List<Step>> steps;
    private LiveData<String> img;

    public RecipeDetailViewModel(@NonNull Application application) {
        super(application);
        RecipeDao dao = RecipeDatabase.getInstance(application.getApplicationContext()).getRecipeDao();
        repository = new Repository(dao);
    }

    public void setRecipeId(int id) {
        this.recipeId = id;
        this.ingredients = repository.getIngredientsForRecipe(recipeId);
        this.steps = repository.getStepsForRecipe(recipeId);
        this.img = repository.getRecipeImg(id);
    }

    public LiveData<List<Ingredient>> getIngredients() {
        return this.ingredients;
    }

    public LiveData<List<Step>> getSteps() {
        return this.steps;
    }

    public LiveData<String> getImg() {
        return this.img;
    }
}
