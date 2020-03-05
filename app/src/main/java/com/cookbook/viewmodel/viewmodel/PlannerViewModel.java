package com.cookbook.viewmodel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;
import com.cookbook.data.entity.Meal;

import java.util.ArrayList;
import java.util.List;

public class PlannerViewModel extends AndroidViewModel {

    Repository repository;

    private final List<LiveData<List<Meal>>> planner;

    public PlannerViewModel(@NonNull Application application) {
        super(application);
        RecipeDao dao = RecipeDatabase.getInstance(application.getApplicationContext()).getRecipeDao();
        repository = new Repository(dao);
        planner = new ArrayList<>();
        for (int day = 0; day < 7; day++) { //CHANGE 7 TO CUSTOMIZABLE PARAMETER
            planner.add(repository.getMealsForDay(day));
        }
    }

    public List<LiveData<List<Meal>>> getPlanner() {
        return this.planner;
    }

    public LiveData<String> getRecipeName(int id) {
        return repository.getRecipeName(id);
    }

}
