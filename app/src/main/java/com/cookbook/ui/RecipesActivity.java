package com.cookbook.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Update;

import com.cookbook.data.entity.Recipe;
import com.cookbook.ui.adapter.RecipeListAdapter;
import com.cookbook.ui.listener.RecipeListListener;
import com.cookbook.ui.listener.SwipeHelper;
import com.cookbook.viewmodel.service.UpdateRecipeService;
import com.cookbook.viewmodel.viewmodel.RecipeListViewModel;
import com.cookbook.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity implements RecipeListListener {

    RecyclerView recyclerView;
    TextView activeDialogText = null;

    /** Use with caution!! Make sure they are always reset on add recipe. **/
    boolean useImportOnEdit = false;
    String apiMealId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_main);

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.recipes_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.recipes_title);

        // Create recyclerview
        recyclerView = findViewById(R.id.recipes_list);
        recyclerView.setAdapter(new RecipeListAdapter(new ArrayList<Recipe>(), this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(new SwipeHelper()).attachToRecyclerView(recyclerView);

        // Get ViewModel
        RecipeListViewModel viewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        viewModel.getRecipeList().observe(this, (recipeList) -> {
            ((RecipeListAdapter) recyclerView.getAdapter()).updateList(recipeList);
        });

        // Set up FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // Create dialog to enter recipe name if new recipe
            openNameDialog();
        });

    }

    private void openNameDialog() {

        useImportOnEdit = false;

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        View search = getLayoutInflater().inflate(R.layout.search_widget, null);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = search.findViewById(R.id.search_widget);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getString(R.string.add_recipe_prompt));
        ImageView searchButton = search.findViewById(getResources().getIdentifier("search_button", "id", "android"));
        searchButton.setVisibility(View.GONE);
        dialog.setView(search);

        TextView textField = search.findViewById(Resources.getSystem().getIdentifier("search_src_text",
                "id", "android"));
        dialog.setPositiveButton("Add", (dialog1, which) -> {

            String recipe_name = textField.getText().toString();
            Intent createRecipe = new Intent(RecipesActivity.this, UpdateRecipeService.class);
            createRecipe.putExtra(UpdateRecipeService.RECIPE_NAME_KEY, recipe_name);
            if (useImportOnEdit) {
                createRecipe.putExtra(UpdateRecipeService.API_ID_KEY, apiMealId);
                createRecipe.putExtra(UpdateRecipeService.ACTION_KEY, UpdateRecipeService.Action.IMPORT);
            }
            else
                createRecipe.putExtra(UpdateRecipeService.ACTION_KEY, UpdateRecipeService.Action.ADD);

            startService(createRecipe);

        });
        dialog.setNegativeButton("Cancel", (dialog12, which) -> {
            activeDialogText = null;
            dialog12.cancel();
        });

        activeDialogText = textField;
        dialog.show();

    }

    public void setActiveDialogText(String text) {
        if (activeDialogText != null) {
            activeDialogText.setText(text);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_INSERT)) {
            setActiveDialogText(intent.getDataString());
            activeDialogText.clearFocus();
            useImportOnEdit = true;
            apiMealId = intent.getStringExtra("intent_extra_data_key");
        }
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
        goToRecipe.putExtra(ViewRecipeActivity.RECIPE_ID_KEY, id);
        goToRecipe.putExtra(ViewRecipeActivity.RECIPE_NAME_KEY, name);
        startActivity(goToRecipe);
    }
}
