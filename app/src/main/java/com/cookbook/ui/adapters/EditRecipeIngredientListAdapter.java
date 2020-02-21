package com.cookbook.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.model.Ingredient;
import com.example.cookbook.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditRecipeIngredientListAdapter extends RecyclerView.Adapter<EditRecipeIngredientListAdapter.IngredientViewHolder> {

    private HashMap<Ingredient, Float> mIngredients;
    private List<Ingredient> mIngredientsSorted;

    public EditRecipeIngredientListAdapter(HashMap<Ingredient, Float> ingredients) {
        mIngredients = ingredients;
        mIngredientsSorted = new ArrayList<> (mIngredients.keySet());
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.edit_recipe_ingredient_item, parent, false);

        // Return a new holder instance
        IngredientViewHolder viewHolder = new IngredientViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = mIngredientsSorted.get(position);

        holder.name.setText(ingredient.getDisplayName());
        holder.quantity.setText(mIngredients.get(ingredient).toString());
    }

    @Override
    public int getItemCount() {
        return mIngredientsSorted.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        public EditText name;
        public EditText quantity;

        public IngredientViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.recipe_ingredient_name);
            quantity = view.findViewById(R.id.recipe_ingredient_quantity);
        }

    }
}