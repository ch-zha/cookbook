package com.cookbook.viewmodel.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cookbook.data.RecipeDao;
import com.cookbook.data.RecipeDatabase;
import com.cookbook.data.Repository;
import com.cookbook.data.api.ApiRecipe;
import com.cookbook.data.api.ApiRecipeGsonAdapter;
import com.cookbook.data.api.ApiResults;
import com.cookbook.data.api.MealApi;
import com.cookbook.data.entity.MeasurementUnit;
import com.cookbook.ui.EditRecipeActivity;
import com.cookbook.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateRecipeService extends IntentService {

    public enum Action {
        UPDATE,
        ADD,
        IMPORT,
        DELETE
    }

    Repository repository;

    private Action action;
    private long recipe_id;
    private String recipe_name;
    private boolean show_warning = false;

    public UpdateRecipeService() {
        super("UpdateRecipeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        RecipeDao dao = RecipeDatabase.getInstance(getApplicationContext()).getRecipeDao();
        this.repository = new Repository(dao);
        this.action = (Action) intent.getSerializableExtra("action");

        switch (action) {
            case UPDATE:
                break;
            case ADD:
                this.recipe_name = intent.getStringExtra("recipe_name");
                this.recipe_id = repository.addRecipe(recipe_name);
                break;
            case IMPORT:
                this.recipe_name = intent.getStringExtra("recipe_name");
                String api_id = intent.getStringExtra("api_id");
                this.recipe_id = importRecipe(api_id);
                break;
            case DELETE:
                break;
            default:
                System.err.println("Unrecognized action in UpdateStepsService");
                break;
        }
    }

    /** Import recipe from MealDB. Should be a valid recipe name from suggestions. **/
    long importRecipe(String recipe_id) {

        int new_id = -1;

        Gson jsonReader = new GsonBuilder()
                .registerTypeAdapter(ApiRecipe.class, new ApiRecipeGsonAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getApplicationContext().getString(R.string.mealdb_base_url))
                .addConverterFactory(GsonConverterFactory.create(jsonReader))
                .build();
        MealApi api = retrofit.create(MealApi.class);
        Call<ApiRecipe> call = api.getRecipe(getApplicationContext().getString(R.string.mealdb_api_key), recipe_id);
        Log.d("api_result", recipe_id);
        Log.d("api_result", call.request().url().toString());

        try {
            ApiRecipe result = call.execute().body();
            if (result != null) {

                // Check to make sure that names match.
                // If input recipe name does not match imported
                // recipe name, only do regular add recipe
                if (!this.recipe_name.equals(result.getMealName())) {
                    return repository.addRecipe(recipe_name);
                }

                //Add recipe to recipes table
                new_id = Math.toIntExact(repository.addRecipe(result.getMealName()));
                Log.d("api_result", result.getMealName());

                //Add steps to steps table
                for (String step : result.getSteps()) {
                    repository.addStep(step, new_id);
                }

                //Add ingredients to ingredients table
                List<String> ingredients = result.getIngredients();
                List<Double> quantity = result.getQuantity();
                List<MeasurementUnit> measurementUnits = result.getUnits();

                if (!(measurementUnits.size() == ingredients.size()
                        && ingredients.size() == quantity.size())) {
                    Log.e("Importing recipe", "Imported recipe is improperly formatted");
                }

                int size = Math.min(measurementUnits.size(), Math.min(quantity.size(), ingredients.size()));

                for (String ingredient : ingredients) {
                    Log.d("Import recipe service - list ingredients", ingredient);
                }

                for (int i = 0; i < size; i++) {
                    double thisQuantity = quantity.get(i) > 0 ? quantity.get(i) : 0;
                    Log.d("Import recipe service - add ingredient", ingredients.get(i));
                    repository.addIngredient(ingredients.get(i), new_id, thisQuantity, measurementUnits.get(i));
                }

                this.show_warning = result.hasUnreadableFields();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (long) new_id;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (action.equals(Action.ADD) || action.equals(Action.IMPORT)) {
            Intent newRecipe = new Intent(this, EditRecipeActivity.class);
            newRecipe.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //NOTE long to int conversion could cause issues if DB is too big, but unlikely
            newRecipe.putExtra("recipe_id", Math.toIntExact(recipe_id));
            newRecipe.putExtra("recipe_name", recipe_name);
            newRecipe.putExtra("show_warning", show_warning);
            startActivity(newRecipe);
//                overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
        }
    }
}
