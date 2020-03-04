package com.cookbook.viewmodel.livedata;

import androidx.lifecycle.LiveData;

import com.cookbook.data.Repository;
import com.cookbook.data.entities.Ingredient;
import com.cookbook.data.entities.Step;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RecipeStepsLiveData extends LiveData<List<Step>> {

    private Repository repository;
    int recipeId;

    public RecipeStepsLiveData(Repository repository, int recipeId) {
        this.repository = repository;
        this.recipeId = recipeId;
        getSteps();
    }

    private void getSteps() {
        FutureTask<List<Step>> task = new FutureTask<>(new FetchSteps(this, recipeId));
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

    private class FetchSteps implements Callable<List<Step>> {

        RecipeStepsLiveData liveData;
        int recipeId;

        FetchSteps(RecipeStepsLiveData liveData, int recipeId) {
            this.liveData = liveData;
            this.recipeId = recipeId;
        }

        @Override
        public List<Step> call() {
            liveData.getRepository().fillRecipeWithSampleSteps(recipeId);
            List<Step> steps = liveData.getRepository().getStepsForRecipe(recipeId);
            return steps;
        }
    }

}