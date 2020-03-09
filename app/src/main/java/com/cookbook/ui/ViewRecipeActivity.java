package com.cookbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.ui.adapter.RecipeIngredientListAdapter;
import com.cookbook.ui.adapter.RecipeStepListAdapter;
import com.cookbook.viewmodel.viewmodel.RecipeDetailViewModel;
import com.cookbook.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewRecipeActivity extends AppCompatActivity {

    public static final String RECIPE_ID_KEY = "recipe_id";
    public static final String RECIPE_NAME_KEY = "recipe_name";

    private RecyclerView rv_ingredients = null;
    private RecyclerView rv_steps = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recipe_main);

        // Get recipe instance (default value is -1)
        Intent intent = getIntent();
        int recipe_id = intent.getIntExtra(RECIPE_ID_KEY, -1);
        String recipe_name = intent.getStringExtra(RECIPE_NAME_KEY);

        // Find recyclerviews
        rv_ingredients = (RecyclerView) findViewById(R.id.rv_recipe_ingredients);
        rv_steps = (RecyclerView) findViewById(R.id.rv_recipe_steps);

        // Create and set adapter/layoutmanager
        rv_ingredients.setAdapter(new RecipeIngredientListAdapter(new ArrayList<>()));
        rv_ingredients.setLayoutManager(new LinearLayoutManager(this));

        // Create and set adapter/layoutmanager
        rv_steps.setAdapter(new RecipeStepListAdapter(new ArrayList<>()));
        rv_steps.setLayoutManager(new LinearLayoutManager(this));

        // Set viewmodel
        RecipeDetailViewModel viewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
        viewModel.setRecipeId(recipe_id);
        viewModel.getIngredients().observe(this, (ingredients -> {
            ((RecipeIngredientListAdapter) rv_ingredients.getAdapter()).updateList(ingredients);
        }));
        viewModel.getSteps().observe(this, (steps -> {
            ((RecipeStepListAdapter) rv_steps.getAdapter()).updateList(steps);
        }));

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.edit_recipe_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(recipe_name);


        // Set up FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editRecipe = new Intent(view.getContext(), EditRecipeActivity.class);
                editRecipe.putExtra(EditRecipeActivity.RECIPE_ID_KEY, recipe_id);
                editRecipe.putExtra(EditRecipeActivity.RECIPE_NAME_KEY, recipe_name);
                startActivity(editRecipe);
                // Take this activity off the stack
                finish();
                overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
            }
        });
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.recipes, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close:
                overridePendingTransition(R.anim.no_anim, R.anim.slide_down);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
