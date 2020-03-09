package com.cookbook.ui;

import android.content.Intent;
import android.os.Bundle;

import com.cookbook.ui.adapter.SectionsPagerAdapter;
import com.cookbook.viewmodel.service.UpdatePlannerService;
import com.cookbook.viewmodel.viewmodel.RecipeListViewModel;
import com.cookbook.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeListViewModel viewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        // Set up tabs
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {}

            /** Exit edit mode if leaving Planner tab **/
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    sectionsPagerAdapter.getPlanner().exitEditMode();
                    setFabBehaviorToNormal();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Set up FAB
        fab = findViewById(R.id.fab);
        setFabBehaviorToNormal();

    }

    /** Handle adding entry to planner **/
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getAction().equals(Intent.ACTION_INSERT)) {

            Intent updateDB = new Intent(this, UpdatePlannerService.class);
            updateDB.putExtra("action", UpdatePlannerService.Action.ADD);
            updateDB.putExtra("day", sectionsPagerAdapter.getPlanner().getDayToAddEntry());
            updateDB.putExtra("recipe_id", Integer.valueOf(intent.getDataString()));
            startService(updateDB);

            sectionsPagerAdapter.getPlanner().dismissAllActiveDialogs();

        }
    }

    /** Floating action button exits planner edit mode if editing planner,
     *  otherwise it opens recipe activity**/

    public void setFabBehaviorToEdit() {
        fab.setOnClickListener(new FABEditModeBehavior());
        fab.setImageDrawable(getDrawable(R.drawable.ic_done_24px));
    }

    public void setFabBehaviorToNormal() {
        fab.setOnClickListener(new FABNormalBehavior());
        fab.setImageDrawable(getDrawable(R.drawable.ic_list_24px));
    }

    class FABNormalBehavior implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent goToRecipes = new Intent(v.getContext(), RecipesActivity.class);
            startActivity(goToRecipes);
            overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
        }

    }

    class FABEditModeBehavior implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            sectionsPagerAdapter.getPlanner().exitEditMode();
            setFabBehaviorToNormal();
        }

    }
}