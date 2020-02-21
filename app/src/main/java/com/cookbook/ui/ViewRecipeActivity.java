package com.cookbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.model.Ingredient;
import com.cookbook.model.Recipe;
import com.cookbook.ui.adapters.RecipeIngredientListAdapter;
import com.cookbook.ui.adapters.RecipeStepListAdapter;
import com.example.cookbook.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewRecipeActivity extends AppCompatActivity {

    private Recipe mRecipe = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recipe_main);

        // Get recipe instance
        Intent intent = getIntent();
        String recipe_id = null;
        if (intent.hasExtra("recipe_id")) {
            recipe_id = intent.getStringExtra("recipe_id");
            this.mRecipe = buildSampleRecipe(recipe_id);
        } else {
            this.mRecipe = new Recipe(" ");
            //TODO error handling
        }
        final String recipe_id_to_pass = recipe_id;

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_recipe_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mRecipe.getName());

        // Find recyclerview
        RecyclerView rv_ingredients = (RecyclerView) findViewById(R.id.rv_recipe_ingredients);
        // Create and set adapter/layoutmanager
        rv_ingredients.setAdapter(new RecipeIngredientListAdapter(mRecipe.getIngredients()));
        rv_ingredients.setLayoutManager(new LinearLayoutManager(this));

        // Find recyclerview
        RecyclerView rv_steps = (RecyclerView) findViewById(R.id.rv_recipe_steps);
        // Create and set adapter/layoutmanager
        rv_steps.setAdapter(new RecipeStepListAdapter(mRecipe.getSteps()));
        rv_steps.setLayoutManager(new LinearLayoutManager(this));

        // Set up FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editRecipe = new Intent(view.getContext(), EditRecipeActivity.class);
                editRecipe.putExtra("recipe_id", recipe_id_to_pass);
                startActivity(editRecipe);
//                overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
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
                finish();
                overridePendingTransition(R.anim.no_anim, R.anim.slide_down);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Recipe buildSampleRecipe(String hash) {
        Recipe recipe = new Recipe(hash);

        recipe.addIngredient(new Ingredient("Ingredient 1"), 2);
        recipe.addIngredient(new Ingredient("Ingredient 2"), 4);
        recipe.addIngredient(new Ingredient("Ingredient 3"), 1);

        recipe.addStep("Do something here");
        recipe.addStep("Do something else here");
        recipe.addStep("Do yet another thing here");

        return recipe;
    }
}
