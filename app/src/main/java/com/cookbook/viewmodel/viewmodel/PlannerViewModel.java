package com.cookbook.viewmodel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;
import com.cookbook.viewmodel.livedata.PlannerLiveData;
import com.cookbook.viewmodel.livedata.RecipeListLiveData;

import java.util.ArrayList;
import java.util.List;

public class PlannerViewModel extends AndroidViewModel {

    Repository repository;

    private final List<PlannerLiveData> planner;

    public PlannerViewModel(@NonNull Application application) {
        super(application);
        RecipeDao dao = RecipeDatabase.getInstance(application.getApplicationContext()).getRecipeDao();
        repository = new Repository(dao);
        planner = new ArrayList<PlannerLiveData>();
        for (int day = 0; day < 7; day++) { //CHANGE 7 TO CUSTOMIZABLE PARAMETER
            planner.add(new PlannerLiveData(repository, day));
        }
    }

    public List<PlannerLiveData> getPlanner() {
        return this.planner;
    }

}
