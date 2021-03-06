package com.cookbook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Recipe;
import com.cookbook.ui.listener.RecipeListListener;
import com.cookbook.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipes;
    private RecipeListListener clickListener = null;

    public RecipeListAdapter(List<Recipe> recipes, RecipeListListener clickListener) {
        this.mRecipes = recipes;
        this.clickListener = clickListener;
    }

    public void updateList(List<Recipe> recipes) {
        this.mRecipes = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recipe_item, parent, false);

        // Return a new holder instance
        return new RecipeViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);

        holder.name.setText(recipe.getName());
        if (recipe.getThumb() != null && !recipe.getThumb().isEmpty()) {
            Picasso.get().load(recipe.getThumb() + "/preview").into(holder.icon);
        } else {
            holder.icon.setImageDrawable(ContextCompat.getDrawable(holder.icon.getContext(), R.drawable.ic_launcher_foreground));
        }
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

            name = view.findViewById(R.id.recipe_item_name);
            icon = view.findViewById(R.id.recipe_item_icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe clicked = mRecipes.get(getAdapterPosition());
            clickListener.onClick(v, clicked.getId(), clicked.getName());
        }

        public Recipe getRecipe() {
            return mRecipes.get(getAdapterPosition());
        }
    }
}
