package com.cookbook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.R;
import com.cookbook.data.entity.Ingredient;
import com.cookbook.data.entity.MeasurementUnit;
import com.cookbook.ui.adapter.diffutils.IngredientDiffCallback;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.PantryViewHolder> {

    private List<com.cookbook.data.entity.Ingredient> mIngredients;

    public ShoppingListAdapter(List<com.cookbook.data.entity.Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public void updateList(List<Ingredient> ingredients) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new IngredientDiffCallback(mIngredients, ingredients), true);
        this.mIngredients = ingredients;
        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.shoppinglist_ingredient_item, parent, false);

        // Return a new holder instance
        return new PantryViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);

        holder.name.setText(ingredient.getName());
        holder.quantity.setText(Double.toString(ingredient.getQuantity()));
        holder.unit.setText(MeasurementUnit.getMeasurementUnitString(ingredient.getUnit()));
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView quantity;
        public TextView unit;
        public CheckBox checkBox;

        public PantryViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.pantry_item_name);
            quantity = (TextView) view.findViewById(R.id.pantry_item_quantity);
            unit = (TextView) view.findViewById(R.id.pantry_item_unit);
            checkBox = (CheckBox) view.findViewById(R.id.pantry_item_checkbox);
        }

    }
}
