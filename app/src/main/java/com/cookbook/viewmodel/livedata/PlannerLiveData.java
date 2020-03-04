package com.cookbook.viewmodel.livedata;

import androidx.lifecycle.LiveData;

import com.cookbook.data.Repository;
import com.cookbook.data.entities.Meal;
import com.cookbook.data.entities.Step;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class PlannerLiveData extends LiveData<List<Meal>> {

    private Repository repository;
    int day;

    public PlannerLiveData(Repository repository, int day) {
        this.repository = repository;
        this.day = day;
        getMeals();
    }

    private void getMeals() {
        FutureTask<List<Meal>> task = new FutureTask<>(new FetchSteps(this, day));
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

    private class FetchSteps implements Callable<List<Meal>> {

        PlannerLiveData liveData;
        int recipeId;

        FetchSteps(PlannerLiveData liveData, int recipeId) {
            this.liveData = liveData;
            this.recipeId = recipeId;
        }

        @Override
        public List<Meal> call() {
            liveData.getRepository().createSamplePlannerForDay(day);
            List<Meal> meal = liveData.getRepository().getMealsForDay(day);
            return meal;
        }
    }

}