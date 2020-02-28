package com.cookbook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cookbook.data.dao.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;

public class RecipeListViewModel extends AndroidViewModel {

    Repository repository;

    private final RecipeListLiveData recipeList;

    public RecipeListViewModel(@NonNull Application application) {
        super(application);
        RecipeDao dao = RecipeDatabase.getInstance(application.getApplicationContext()).getRecipeDao();
        repository = new Repository(dao);
        recipeList = new RecipeListLiveData(repository);
    }

    public RecipeListLiveData getRecipeList() {
        return this.recipeList;
    }

}
