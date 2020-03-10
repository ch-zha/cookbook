package com.cookbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Ingredient;
import com.cookbook.data.entity.MeasurementUnit;
import com.cookbook.ui.adapter.EditRecipeIngredientListAdapter;
import com.cookbook.ui.adapter.EditRecipeStepListAdapter;
import com.cookbook.ui.listener.EditIngredientListener;
import com.cookbook.ui.listener.EditStepListener;
import com.cookbook.ui.util.AnimUtil;
import com.cookbook.viewmodel.service.UpdateIngredientsService;
import com.cookbook.viewmodel.service.UpdateStepsService;
import com.cookbook.viewmodel.viewmodel.RecipeDetailViewModel;
import com.cookbook.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EditRecipeActivity extends AppCompatActivity implements EditStepListener, EditIngredientListener {

    public static final String RECIPE_ID_KEY = "recipe_id";
    public static final String RECIPE_NAME_KEY = "recipe_name";
    public static final String SHOW_WARNING_KEY = "show_warning";
    public static final String ANIMATE_KEY = "animate";

    private RecipeDetailViewModel viewModel;
    private RecyclerView rv_steps;
    private RecyclerView rv_ingredients;
    private AppBarLayout appBar;
    private int recipe_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recipe_main);

        // Get recipe instance (default value is -1)
        Intent intent = getIntent();
        recipe_id = intent.getIntExtra(RECIPE_ID_KEY, -1);
        boolean show_warning = intent.getBooleanExtra(SHOW_WARNING_KEY, false);
        final String recipe_name = intent.getStringExtra(RECIPE_NAME_KEY);

        if (intent.getBooleanExtra(ANIMATE_KEY, false))
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);

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

        //Set recipe
        if (recipe_id != -1) setRecipe(recipe_id); //TODO do error handling

        if (show_warning) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Some fields could not be recognized. Please check to see if the information is correct.");
            builder.show();
        }

        //Set FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageDrawable(getDrawable(R.drawable.ic_done_24px));
        fab.setOnClickListener(v -> {
            Intent goToView = new Intent(this, ViewRecipeActivity.class);
            goToView.putExtra(ViewRecipeActivity.RECIPE_NAME_KEY, recipe_name);
            goToView.putExtra(ViewRecipeActivity.RECIPE_ID_KEY, recipe_id);
            startActivity(goToView);
            overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
            // Take this activity off the stack
            finish();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipes, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        toggleSelected(1);
    }

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
                Intent restart = new Intent(this, MainActivity.class);
                startActivity(restart);
                overridePendingTransition(R.anim.no_anim, R.anim.slide_down);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    /*** Edit Step Listener Functions ***/

    @Override
    public void onEditStep(String step, int step_id) {

        Intent updateDB = new Intent(this, UpdateStepsService.class);
        updateDB.putExtra(UpdateStepsService.INSTR_KEY, step);
        updateDB.putExtra(UpdateStepsService.STEP_ID_KEY, step_id);
        updateDB.putExtra(UpdateStepsService.ACTION_KEY, UpdateStepsService.Action.UPDATE);
        startService(updateDB);

    }

    @Override
    public void onAddStep(String step) {

        Intent updateDB = new Intent(this, UpdateStepsService.class);
        updateDB.putExtra(UpdateStepsService.INSTR_KEY, step);
        updateDB.putExtra(UpdateStepsService.RECIPE_ID_KEY, recipe_id);
        updateDB.putExtra(UpdateStepsService.ACTION_KEY, UpdateStepsService.Action.ADD);
        startService(updateDB);

    }

    @Override
    public void onDeleteStep(int step_id) {

        Intent updateDB = new Intent(this, UpdateStepsService.class);
        updateDB.putExtra(UpdateStepsService.STEP_ID_KEY, step_id);
        updateDB.putExtra(UpdateStepsService.ACTION_KEY, UpdateStepsService.Action.DELETE);
        startService(updateDB);

    }

    @Override
    public void onReorderStep(String step, int step_id, int new_place) {

    }

    /*** Edit Ingredient Listener Functions ***/

    @Override
    public void onAddIngredient(String ingredient_name, double quantity, MeasurementUnit unit) {

        Intent updateDB = new Intent(this, UpdateIngredientsService.class);
        updateDB.putExtra(UpdateIngredientsService.ING_NAME_KEY, ingredient_name);
        updateDB.putExtra(UpdateIngredientsService.RECIPE_ID_KEY, recipe_id);
        updateDB.putExtra(UpdateIngredientsService.ING_QUANT_KEY, quantity);
        updateDB.putExtra(UpdateIngredientsService.ING_UNIT_KEY, unit);
        updateDB.putExtra(UpdateIngredientsService.ACTION_KEY, UpdateIngredientsService.Action.ADD);
        startService(updateDB);

    }

    @Override
    public void onDeleteIngredient(String ingredient_name) {

        Intent updateDB = new Intent(this, UpdateIngredientsService.class);
        updateDB.putExtra(UpdateIngredientsService.ING_NAME_KEY, ingredient_name);
        updateDB.putExtra(UpdateIngredientsService.RECIPE_ID_KEY, recipe_id);
        updateDB.putExtra(UpdateIngredientsService.ACTION_KEY, UpdateIngredientsService.Action.DELETE);
        startService(updateDB);

    }

    @Override
    public void onUpdateIngredientName(String ingredient_name, String new_name) {

        Intent updateDB = new Intent(this, UpdateIngredientsService.class);
        updateDB.putExtra(UpdateIngredientsService.ING_NAME_KEY, ingredient_name);
        updateDB.putExtra(UpdateIngredientsService.ING_NEW_NAME_KEY, new_name);
        updateDB.putExtra(UpdateIngredientsService.RECIPE_ID_KEY, recipe_id);
        updateDB.putExtra(UpdateIngredientsService.ACTION_KEY, UpdateIngredientsService.Action.UPDATE_NAME);
        startService(updateDB);

    }

    @Override
    public void onUpdateIngredientQuantity(String ingredient_name, double quantity) {

        Intent updateDB = new Intent(this, UpdateIngredientsService.class);
        updateDB.putExtra(UpdateIngredientsService.ING_NAME_KEY, ingredient_name);
        updateDB.putExtra(UpdateIngredientsService.RECIPE_ID_KEY, recipe_id);
        updateDB.putExtra(UpdateIngredientsService.ING_QUANT_KEY, quantity);
        updateDB.putExtra(UpdateIngredientsService.ACTION_KEY, UpdateIngredientsService.Action.UPDATE_QUANTITY);
        startService(updateDB);

    }

    @Override
    public void onUpdateIngredientUnit(String ingredient_name, MeasurementUnit unit) {

        Intent updateDB = new Intent(this, UpdateIngredientsService.class);
        updateDB.putExtra(UpdateIngredientsService.ING_NAME_KEY, ingredient_name);
        updateDB.putExtra(UpdateIngredientsService.RECIPE_ID_KEY, recipe_id);
        updateDB.putExtra(UpdateIngredientsService.ING_UNIT_KEY, unit);
        updateDB.putExtra(UpdateIngredientsService.ACTION_KEY, UpdateIngredientsService.Action.UPDATE_UNIT);
        startService(updateDB);

    }

    @Override
    public void onClickUnitButton(View view, Ingredient ingredient) {
        PopupMenu popup = new PopupMenu(this, view);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.opt_whole:
                        onUpdateIngredientUnit(ingredient.getName(), MeasurementUnit.Whole);
                        return true;
                    case R.id.opt_gram:
                        onUpdateIngredientUnit(ingredient.getName(), MeasurementUnit.Gram);
                        return true;
                    case R.id.opt_cup:
                        onUpdateIngredientUnit(ingredient.getName(), MeasurementUnit.Cup);
                        return true;
                    case R.id.opt_lb:
                        onUpdateIngredientUnit(ingredient.getName(), MeasurementUnit.lb);
                        return true;
                    case R.id.opt_ml:
                        onUpdateIngredientUnit(ingredient.getName(), MeasurementUnit.mL);
                        return true;
                    case R.id.opt_tbsp:
                        onUpdateIngredientUnit(ingredient.getName(), MeasurementUnit.TBSP);
                        return true;
                    case R.id.opt_tsp:
                        onUpdateIngredientUnit(ingredient.getName(), MeasurementUnit.TSP);
                        return true;
                    case R.id.opt_pinch:
                        onUpdateIngredientUnit(ingredient.getName(), MeasurementUnit.Pinch);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.ing_unit);
        popup.show();
    }

}
