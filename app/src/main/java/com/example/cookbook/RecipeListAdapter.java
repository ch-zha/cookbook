package com.example.cookbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<RecipeListItem> mRecipes;

    public RecipeListAdapter(List<RecipeListItem> recipes) {
        mRecipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recipe_item, parent, false);

        // Return a new holder instance
        RecipeViewHolder viewHolder = new RecipeViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        RecipeListItem recipe = mRecipes.get(position);

        holder.name.setText(recipe.getName());
        holder.icon.setImageDrawable(ContextCompat.getDrawable(holder.icon.getContext(), R.drawable.ic_launcher_background));
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView icon;

        public RecipeViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.recipe_item_name);
            icon = (ImageView) view.findViewById(R.id.recipe_item_icon);
        }

    }
}
