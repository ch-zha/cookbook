package com.cookbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
import com.cookbook.ui.adapters.RecipeListAdapter;
import com.cookbook.ui.helpers.ItemClickListener;
import com.cookbook.viewmodel.RecipeListItem;
import com.example.cookbook.R;

import java.util.List;

public class ViewRecipeActivity extends AppCompatActivity {

    private Recipe mRecipe = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_recipe_main);

        // Get recipe instance
        Intent intent = getIntent();
        String recipe_hash = null;
        if (intent.hasExtra("recipe_hash")) {
            recipe_hash = intent.getStringExtra("recipe_hash");
            this.mRecipe = buildSampleRecipe(recipe_hash);
        } else {
            this.mRecipe = new Recipe(" ");
            //TODO error handling
        }

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_recipe_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mRecipe.getName());
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
