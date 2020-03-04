package com.cookbook.viewmodel.livedata;

import androidx.lifecycle.LiveData;

import com.cookbook.data.Repository;
import com.cookbook.data.entities.Ingredient;
import com.cookbook.data.entities.Recipe;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RecipeIngredientsLiveData extends LiveData<List<Ingredient>> {

    private Repository repository;
    int recipeId;

    public RecipeIngredientsLiveData(Repository repository, int recipeId) {
        this.repository = repository;
        this.recipeId = recipeId;
        getIngredients();
    }

    private void getIngredients() {
        FutureTask<List<Ingredient>> task = new FutureTask<>(new FetchIngredientList(this, recipeId));
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

    private class FetchIngredientList implements Callable<List<Ingredient>> {

        RecipeIngredientsLiveData liveData;
        int recipeId;

        FetchIngredientList(RecipeIngredientsLiveData liveData, int recipeId) {
            this.liveData = liveData;
            this.recipeId = recipeId;
        }

        @Override
        public List<Ingredient> call() {
            liveData.getRepository().fillRecipeWithSampleIngredients(recipeId);
            List<Ingredient> ingredients = liveData.getRepository().getIngredientsForRecipe(recipeId);
            return ingredients;
        }
    }

}