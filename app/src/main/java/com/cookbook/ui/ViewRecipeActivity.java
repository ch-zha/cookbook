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

import com.cookbook.event.RecipeMessageEvent;
import com.cookbook.event.RequestRecipeEvent;
import com.cookbook.viewmodel.Recipe;
import com.cookbook.ui.adapters.RecipeIngredientListAdapter;
import com.cookbook.ui.adapters.RecipeStepListAdapter;
import com.example.cookbook.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ViewRecipeActivity extends AppCompatActivity {

    private Recipe mRecipe = null;
    private RecyclerView rv_ingredients = null;
    private RecyclerView rv_steps = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recipe_main);
        EventBus.getDefault().register(this);

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_recipe_toolbar);
        setSupportActionBar(toolbar);

        // Find recyclerviews
        rv_ingredients = (RecyclerView) findViewById(R.id.rv_recipe_ingredients);
        rv_steps = (RecyclerView) findViewById(R.id.rv_recipe_steps);

        // Get recipe instance (default value is -1)
        Intent intent = getIntent();
        int recipe_id = -1;
        recipe_id = intent.getIntExtra("recipe_id", -1);
        sendRecipeRequest(recipe_id);
        final int recipe_id_to_pass = recipe_id;

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RecipeMessageEvent event) {

        this.mRecipe = event.recipe;
        System.out.println("Received recipe: " + mRecipe.getName());
        //Set toolbar name
        getSupportActionBar().setTitle(mRecipe.getName());
        // Create and set adapter/layoutmanager
        rv_ingredients.setAdapter(new RecipeIngredientListAdapter(mRecipe.getIngredients()));
        rv_ingredients.setLayoutManager(new LinearLayoutManager(this));
        // Create and set adapter/layoutmanager
        rv_steps.setAdapter(new RecipeStepListAdapter(mRecipe.getSteps()));
        rv_steps.setLayoutManager(new LinearLayoutManager(this));

    }

    public void sendRecipeRequest(int id) {
        System.out.println("Sending recipe request");
        EventBus.getDefault().post(new RequestRecipeEvent(id));
    }
}
