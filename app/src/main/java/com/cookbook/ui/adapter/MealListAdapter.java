package com.cookbook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Meal;
import com.cookbook.viewmodel.viewmodel.PlannerViewModel;
import com.example.cookbook.R;

import java.util.List;

public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.MealViewHolder> {

    private List<Meal> mMeals;
    private PlannerViewModel viewModel;
    private Fragment owner;

    MealListAdapter(List<Meal> meals, Fragment owner) {
        mMeals = meals;
        this.owner = owner;
        this.viewModel = ViewModelProviders.of(owner).get(PlannerViewModel.class);
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.menu_meal_item, parent, false);

        // Return a new holder instance
        return new MealViewHolder(contactView);
    }

    public void updateList(List<Meal> meals) {
        this.mMeals = meals;
        synchronized (this) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {

        int recipe_id = mMeals.get(position).getRecipeId();
        viewModel.getRecipeName(recipe_id).observe(owner, name -> {
            holder.name.setText(name);
        });

    }

    @Override
    public int getItemCount() {
        return mMeals.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private Fragment owner;

        public MealViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.menu_meal_name);
        }

    }
}
