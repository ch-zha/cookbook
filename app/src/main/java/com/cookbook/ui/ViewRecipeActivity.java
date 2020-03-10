package com.cookbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.ui.adapter.RecipeIngredientListAdapter;
import com.cookbook.ui.adapter.RecipeStepListAdapter;
import com.cookbook.ui.listener.OverscrollListener;
import com.cookbook.ui.util.AnimUtil;
import com.cookbook.ui.util.OverscrollLayout;
import com.cookbook.viewmodel.viewmodel.RecipeDetailViewModel;
import com.cookbook.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewRecipeActivity extends AppCompatActivity {

    public static final String RECIPE_ID_KEY = "recipe_id";
    public static final String RECIPE_NAME_KEY = "recipe_name";

    private RecyclerView rv_ingredients = null;
    private RecyclerView rv_steps = null;
    private AppBarLayout appBar = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recipe_main);

        // Get recipe instance (default value is -1)
        Intent intent = getIntent();
        int recipe_id = intent.getIntExtra(RECIPE_ID_KEY, -1);
        String recipe_name = intent.getStringExtra(RECIPE_NAME_KEY);

        // Find recyclerviews
        rv_ingredients = findViewById(R.id.rv_recipe_ingredients);
        rv_steps = findViewById(R.id.rv_recipe_steps);

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

        // Set up FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent editRecipe = new Intent(view.getContext(), EditRecipeActivity.class);
            editRecipe.putExtra(EditRecipeActivity.RECIPE_ID_KEY, recipe_id);
            editRecipe.putExtra(EditRecipeActivity.RECIPE_NAME_KEY, recipe_name);
            startActivity(editRecipe);
            // Take this activity off the stack
            finish();
            overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
        });

        // Set section headers
        View ingredients_header = findViewById(R.id.ingredients_header);
        View steps_header = findViewById(R.id.steps_header);

        ingredients_header.setOnClickListener(v -> toggleSelected(1));
        steps_header.setOnClickListener(v -> toggleSelected(2));

        // Set image
        ImageView img = findViewById(R.id.recipe_image);
        viewModel.getImg().observe(this, imgUrl -> {
            if (img != null)
                Picasso.get().load(imgUrl).into(img);
        });

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.edit_recipe_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(recipe_name);
        appBar = findViewById(R.id.expanding_app_bar);
        img.setOnClickListener(v -> toggleSelected(0));
        toolbar.setOnClickListener(v -> toggleSelected(0));

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    void hideIngredients() {
        if (rv_ingredients.isShown()) {
            AnimUtil.slide_up(this, rv_ingredients);
        }
    }

    void showIngredients() {
        if (!rv_ingredients.isShown()) {
            AnimUtil.slide_down(this, rv_ingredients);
        }
    }

    void hideSteps() {
        if (rv_steps.isShown()) {
            AnimUtil.slide_up(this, rv_steps);
        }
    }

    void showSteps() {
        if (!rv_steps.isShown()) {
            AnimUtil.slide_down(this, rv_steps);
        }
    }

    // 0 = appBar, 1 = ingredients, 2 = steps
    void toggleSelected(int selected) {
        switch (selected) {
            case 0:
                boolean expanded = (appBar.getHeight() - appBar.getBottom()) == 0;
                if (expanded)
                    appBar.setExpanded(false, true);
                else {
                    appBar.setExpanded(true, true);
                    hideIngredients();
                    hideSteps();
                }
                return;
            case 1:
                if (rv_ingredients.isShown()) {
                    hideIngredients();
                } else {
                    appBar.setExpanded(false, true);
                    showIngredients();
                    hideSteps();
                }
                return;
            case 2:
                if (rv_steps.isShown()) {
                    hideSteps();
                } else {
                    appBar.setExpanded(false, true);
                    hideIngredients();
                    showSteps();
                }
                return;
            default:
                return;
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
                Intent restart = new Intent(this, MainActivity.class);
                startActivity(restart);
                overridePendingTransition(R.anim.no_anim, R.anim.slide_down);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

}
