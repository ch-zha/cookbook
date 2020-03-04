package com.cookbook.viewmodel.livedata;

import androidx.lifecycle.LiveData;

import com.cookbook.data.Repository;
import com.cookbook.data.entities.Recipe;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RecipeListLiveData extends LiveData<List<com.cookbook.data.entities.Recipe>> {

    private Repository repository;

    public RecipeListLiveData(Repository repository) {
        this.repository = repository;
        getRecipeList();
    }

    private void getRecipeList() {
        FutureTask<List<Recipe>> task = new FutureTask<>(new FetchRecipeList(this));
        new Thread(task).start();
        try {
            setValue(task.get());
        } catch (ExecutionException|InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Repository getRepository() {
        return this.repository;
    }

    private class FetchRecipeList implements Callable<List<Recipe>> {

        RecipeListLiveData liveData;

        FetchRecipeList(RecipeListLiveData liveData) {
            this.liveData = liveData;
        }

        @Override
        public List<Recipe> call() {
            liveData.getRepository().repopulate();
            List<Recipe> recipes = liveData.getRepository().getAllRecipes();
            return recipes;
        }
    }

}