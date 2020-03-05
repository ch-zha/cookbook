package com.cookbook.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Recipe;
import com.cookbook.ui.adapter.RecipeListAdapter;
import com.cookbook.ui.listener.ItemClickListener;
import com.cookbook.viewmodel.service.UpdateRecipeService;
import com.cookbook.viewmodel.viewmodel.RecipeListViewModel;
import com.example.cookbook.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity implements ItemClickListener {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_main);

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.recipes_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.recipes_title);

        // Create recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recipes_list);
        recyclerView.setAdapter(new RecipeListAdapter(new ArrayList<Recipe>(), this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get ViewModel
        RecipeListViewModel viewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        viewModel.getRecipeList().observe(this, (recipeList) -> {
            ((RecipeListAdapter) recyclerView.getAdapter()).updateList(recipeList);
        });

        // Set up FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create dialog to enter recipe name if new recipe
                openNameDialog();
            }
        });

    }

    private void openNameDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Enter recipe name");
        EditText textField = new EditText(this);
        dialog.setView(textField);
        dialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String recipe_name = textField.getText().toString();
                Intent createRecipe = new Intent(RecipesActivity.this, UpdateRecipeService.class);
                createRecipe.putExtra("recipe_name", recipe_name);
                createRecipe.putExtra("action", UpdateRecipeService.Action.ADD);
                startService(createRecipe);
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
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
    public void onClick(View view, int id, String name) {
        Intent goToRecipe = new Intent(view.getContext(), ViewRecipeActivity.class);
        goToRecipe.putExtra("recipe_id", id);
        goToRecipe.putExtra("recipe_name", name);
        startActivity(goToRecipe);
    }
}
