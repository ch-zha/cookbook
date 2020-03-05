package com.cookbook.ui;

import android.content.Intent;
import android.os.Bundle;

import com.cookbook.ui.adapter.SectionsPagerAdapter;
import com.cookbook.viewmodel.viewmodel.RecipeListViewModel;
import com.example.cookbook.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeListViewModel viewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        // Set up tabs
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // Set up FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToRecipes = new Intent(view.getContext(), RecipesActivity.class);
                startActivity(goToRecipes);
                overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
            }
        });
    }
}