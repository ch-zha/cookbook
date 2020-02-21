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

import com.cookbook.ui.adapters.RecipeListAdapter;
import com.cookbook.ui.helper.ItemClickListener;
import com.example.cookbook.R;
import com.cookbook.viewmodel.RecipeListItem;

import java.util.List;

public class RecipesActivity extends AppCompatActivity implements ItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_main);

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.recipes_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.recipes_title);

        // Create sample recipe list
        List<RecipeListItem> recipes = RecipeListItem.createSampleRecipeList();

        // Find recyclerview
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recipes_list);
        // Create and set adapter/layoutmanager
        RecipeListAdapter adapter = new RecipeListAdapter(recipes, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipes, menu);
        return true;
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

    @Override
    public void onClick(View view, String id) {
        Intent goToRecipe = new Intent(view.getContext(), ViewRecipeActivity.class);
        goToRecipe.putExtra("recipe_id", id);
        startActivity(goToRecipe);
    }
}
