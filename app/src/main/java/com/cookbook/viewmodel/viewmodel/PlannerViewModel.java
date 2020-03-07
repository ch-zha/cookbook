package com.cookbook.viewmodel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;
import com.cookbook.data.entity.Entry;
import com.cookbook.ui.PlannerFragment;

import java.util.ArrayList;
import java.util.List;

public class PlannerViewModel extends AndroidViewModel {

    Repository repository;

    private final LiveData<List<Entry>> planner;

    public PlannerViewModel(@NonNull Application application) {
        super(application);
        RecipeDao dao = RecipeDatabase.getInstance(application.getApplicationContext()).getRecipeDao();
        repository = new Repository(dao);

        int[] days = new int[PlannerFragment.DAYS_DISPLAYED];
        for (int i = 0; i < PlannerFragment.DAYS_DISPLAYED; i++) {
            days[i] = i;
        }

        planner = repository.getMealsForDays(days);
    }

    public LiveData<List<Entry>> getPlanner() {
        return this.planner;
    }

    public LiveData<String> getRecipeName(int id) {
        return repository.getRecipeName(id);
    }

}
