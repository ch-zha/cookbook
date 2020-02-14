package com.cookbook.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.viewmodel.RecipeListItem;
import com.example.cookbook.R;

import java.util.List;

public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.MealViewHolder> {

    private List<RecipeListItem> mMeals;

    public MealListAdapter(List<RecipeListItem> meals) {
        mMeals = meals;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.menu_meal_item, parent, false);

        // Return a new holder instance
        MealViewHolder viewHolder = new MealViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        RecipeListItem recipe = mMeals.get(position);

        holder.name.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return mMeals.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {

        public TextView name;

        public MealViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.menu_meal_name);
        }

    }
}
