package com.cookbook.viewmodel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;
import com.cookbook.data.entity.Recipe;

import java.util.List;

public class RecipeListViewModel extends AndroidViewModel {

    Repository repository;

    private final LiveData<List<Recipe>> recipeList;

    public RecipeListViewModel(@NonNull Application application) {
        super(application);
        RecipeDao dao = RecipeDatabase.getInstance(application.getApplicationContext()).getRecipeDao();
        repository = new Repository(dao);
        recipeList = repository.getAllRecipes();
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return this.recipeList;
    }

}
