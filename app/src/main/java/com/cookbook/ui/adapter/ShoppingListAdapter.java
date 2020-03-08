package com.cookbook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.viewmodel.Ingredient;
import com.cookbook.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.PantryViewHolder> {

    private HashMap<Ingredient, Float> mIngredients;
    private List<Ingredient> mIngredientsSorted;

    public ShoppingListAdapter(HashMap<Ingredient, Float> ingredients) {
        mIngredients = ingredients;
        mIngredientsSorted = new ArrayList<> (mIngredients.keySet());
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.shoppinglist_ingredient_item, parent, false);

        // Return a new holder instance
        PantryViewHolder viewHolder = new PantryViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        Ingredient ingredient = mIngredientsSorted.get(position);

        holder.name.setText(ingredient.getDisplayName());
        holder.quantity.setText(mIngredients.get(ingredient).toString());
    }

    @Override
    public int getItemCount() {
        return mIngredientsSorted.size();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView quantity;
        public CheckBox checkBox;

        public PantryViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.pantry_item_name);
            quantity = (TextView) view.findViewById(R.id.pantry_item_quantity);
            checkBox = (CheckBox) view.findViewById(R.id.pantry_item_checkbox);
        }

    }
}
