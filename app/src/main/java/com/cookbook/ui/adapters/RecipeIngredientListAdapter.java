package com.cookbook.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entities.Ingredient;
import com.cookbook.data.entities.Recipe;
import com.example.cookbook.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeIngredientListAdapter extends RecyclerView.Adapter<RecipeIngredientListAdapter.IngredientViewHolder> {

    private List<Ingredient> mIngredients;

    public RecipeIngredientListAdapter(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public void updateList(List<Ingredient> ingredients) {
        this.mIngredients = ingredients;
        synchronized (this) {
            notify();
        }
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recipe_ingredient_item, parent, false);

        // Return a new holder instance
        IngredientViewHolder viewHolder = new IngredientViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);

        holder.name.setText(ingredient.getName());
        holder.quantity.setText(Double.toString(ingredient.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView quantity;

        public IngredientViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.recipe_ingredient_name);
            quantity = (TextView) view.findViewById(R.id.recipe_ingredient_quantity);
        }

    }
}
