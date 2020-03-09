package com.cookbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.MeasurementUnit;
import com.cookbook.ui.adapter.EditRecipeIngredientListAdapter;
import com.cookbook.ui.adapter.EditRecipeStepListAdapter;
import com.cookbook.ui.listener.EditIngredientListener;
import com.cookbook.ui.listener.EditStepListener;
import com.cookbook.viewmodel.service.UpdateIngredientsService;
import com.cookbook.viewmodel.service.UpdateStepsService;
import com.cookbook.viewmodel.viewmodel.RecipeDetailViewModel;
import com.cookbook.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EditRecipeActivity extends AppCompatActivity implements EditStepListener, EditIngredientListener {

    public static final String RECIPE_ID_KEY = "recipe_id";
    public static final String RECIPE_NAME_KEY = "recipe_name";
    public static final String SHOW_WARNING_KEY = "show_warning";

    private RecipeDetailViewModel viewModel;
    private RecyclerView rv_steps;
    private RecyclerView rv_ingredients;
    private int recipe_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_recipe_main);

        // Get recipe instance (default value is -1)
        Intent intent = getIntent();
        recipe_id = intent.getIntExtra(RECIPE_ID_KEY, -1);
        boolean show_warning = intent.getBooleanExtra(SHOW_WARNING_KEY, false);
        final String recipe_name = intent.getStringExtra(RECIPE_NAME_KEY);

        // Set viewmodel
        viewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);

        // Find recyclerview
        rv_ingredients = (RecyclerView) findViewById(R.id.rv_recipe_ingredients);
        // Create and set adapter/layoutmanager
        rv_ingredients.setAdapter(new EditRecipeIngredientListAdapter(new ArrayList<>(), this));
        rv_ingredients.setLayoutManager(new LinearLayoutManager(this));

        // Find recyclerview
        rv_steps = findViewById(R.id.rv_recipe_steps);
        // Create and set adapter/layoutmanager
        rv_steps.setAdapter(new EditRecipeStepListAdapter(new ArrayList<>(), this));
        rv_steps.setLayoutManager(new LinearLayoutManager(this));

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.edit_recipe_toolbar);
        setSupportActionBar(toolbar);
        setToolbarName(recipe_name);

        //Set recipe
        if (recipe_id != -1) setRecipe(recipe_id); //TODO do error handling

        if (show_warning) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Some fields could not be recognized. Please check to see if the information is correct.");
            builder.show();
        }

        //Set FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent goToView = new Intent(this, ViewRecipeActivity.class);
            goToView.putExtra(ViewRecipeActivity.RECIPE_NAME_KEY, recipe_name);
            goToView.putExtra(ViewRecipeActivity.RECIPE_ID_KEY, recipe_id);
            startActivity(goToView);
            overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
            // Take this activity off the stack
            finish();
        });
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.recipes, menu);
//        return true;
//    }

    private void setRecipe(int recipe_id) {

        viewModel.setRecipeId(recipe_id);
        viewModel.getIngredients().observe(this, (ingredients -> {
            ((EditRecipeIngredientListAdapter) rv_ingredients.getAdapter()).updateList(ingredients);
        }));
        viewModel.getSteps().observe(this, (steps -> {
            ((EditRecipeStepListAdapter) rv_steps.getAdapter()).updateList(steps);
        }));

    }

    private void setToolbarName(String recipe_name) {
        getSupportActionBar().setTitle(recipe_name);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close:
                finish();
                overridePendingTransition(R.anim.no_anim, R.anim.slide_down);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /*** Edit Step Listener Functions ***/

    @Override
    public void onEditStep(String step, int step_id) {

        Intent updateDB = new Intent(this, UpdateStepsService.class);
        updateDB.putExtra("instructions", step);
        updateDB.putExtra("step_id", step_id);
        updateDB.putExtra("action", UpdateStepsService.Action.UPDATE);
        startService(updateDB);

    }

    @Override
    public void onAddStep(String step) {

        Intent updateDB = new Intent(this, UpdateStepsService.class);
        updateDB.putExtra("instructions", step);
        updateDB.putExtra("recipe_id", recipe_id);
        updateDB.putExtra("action", UpdateStepsService.Action.ADD);
        startService(updateDB);

    }

    @Override
    public void onDeleteStep(int step_id) {

        Intent updateDB = new Intent(this, UpdateStepsService.class);
        updateDB.putExtra("step_id", step_id);
        updateDB.putExtra("action", UpdateStepsService.Action.DELETE);
        startService(updateDB);

    }

    @Override
    public void onReorderStep(String step, int step_id, int new_place) {

    }

    /*** Edit Ingredient Listener Functions ***/

    @Override
    public void onAddIngredient(String ingredient_name, double quantity, MeasurementUnit unit) {

        Intent updateDB = new Intent(this, UpdateIngredientsService.class);
        updateDB.putExtra("ingredient_name", ingredient_name);
        updateDB.putExtra("recipe_id", recipe_id);
        updateDB.putExtra("quantity", quantity);
        updateDB.putExtra("unit", unit);
        updateDB.putExtra("action", UpdateIngredientsService.Action.ADD);
        startService(updateDB);

    }

    @Override
    public void onDeleteIngredient(String ingredient_name) {

        Intent updateDB = new Intent(this, UpdateIngredientsService.class);
        updateDB.putExtra("ingredient_name", ingredient_name);
        updateDB.putExtra("recipe_id", recipe_id);
        updateDB.putExtra("action", UpdateIngredientsService.Action.DELETE);
        startService(updateDB);

    }

    @Override
    public void onUpdateIngredientName(String ingredient_name, String new_name) {

        Intent updateDB = new Intent(this, UpdateIngredientsService.class);
        updateDB.putExtra("ingredient_name", ingredient_name);
        updateDB.putExtra("new_name", new_name);
        updateDB.putExtra("recipe_id", recipe_id);
        updateDB.putExtra("action", UpdateIngredientsService.Action.UPDATE_NAME);
        startService(updateDB);

    }

    @Override
    public void onUpdateIngredientQuantity(String ingredient_name, double quantity) {

        Intent updateDB = new Intent(this, UpdateIngredientsService.class);
        updateDB.putExtra("ingredient_name", ingredient_name);
        updateDB.putExtra("recipe_id", recipe_id);
        updateDB.putExtra("quantity", quantity);
        updateDB.putExtra("action", UpdateIngredientsService.Action.UPDATE_QUANTITY);
        startService(updateDB);

    }

    @Override
    public void onUpdateIngredientUnit(String ingredient_name, MeasurementUnit unit) {

        Intent updateDB = new Intent(this, UpdateIngredientsService.class);
        updateDB.putExtra("ingredient_name", ingredient_name);
        updateDB.putExtra("recipe_id", recipe_id);
        updateDB.putExtra("unit", unit);
        updateDB.putExtra("action", UpdateIngredientsService.Action.UPDATE_UNIT);
        startService(updateDB);

    }

}
