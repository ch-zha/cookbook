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

import com.cookbook.data.entities.Recipe;
import com.cookbook.ui.helper.ItemClickListener;
import com.example.cookbook.R;
import com.cookbook.viewmodel.RecipeListItem;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipes;
    private ItemClickListener clickListener = null;

    public RecipeListAdapter(List<Recipe> recipes, ItemClickListener clickListener) {
        this.mRecipes = recipes;
        this.clickListener = clickListener;
    }

    public void updateList(List<Recipe> recipes) {
        this.mRecipes = recipes;
        synchronized (this) {
            notify();
        }
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
        Recipe recipe = mRecipes.get(position);

        holder.name.setText(recipe.getName());
        holder.icon.setImageDrawable(ContextCompat.getDrawable(holder.icon.getContext(), R.drawable.ic_launcher_background));
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public ImageView icon;

        public RecipeViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.recipe_item_name);
            icon = (ImageView) view.findViewById(R.id.recipe_item_icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe clicked = mRecipes.get(getAdapterPosition());
            clickListener.onClick(v, clicked.getId(), clicked.getName());
        }
    }
}
