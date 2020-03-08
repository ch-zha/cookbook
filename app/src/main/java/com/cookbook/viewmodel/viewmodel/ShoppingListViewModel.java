package com.cookbook.viewmodel.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;
import com.cookbook.data.entity.Ingredient;
import com.cookbook.data.entity.Recipe;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {

    Repository repository;

    private final LiveData<List<Ingredient>> shoppingList;

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        RecipeDao dao = RecipeDatabase.getInstance(application.getApplicationContext()).getRecipeDao();
        this.repository = new Repository(dao);
        this.shoppingList = repository.getShoppingList();
    }

    public LiveData<List<Ingredient>> getShoppingList() {
        return this.shoppingList;
    }

}
