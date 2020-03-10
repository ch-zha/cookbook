package com.cookbook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Ingredient;
import com.cookbook.R;
import com.cookbook.data.entity.MeasurementUnit;
import com.cookbook.ui.adapter.diffutils.IngredientDiffCallback;

import java.util.List;

public class RecipeIngredientListAdapter extends RecyclerView.Adapter<RecipeIngredientListAdapter.IngredientViewHolder> {

    private List<Ingredient> mIngredients;

    public RecipeIngredientListAdapter(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public void updateList(List<Ingredient> ingredients) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new IngredientDiffCallback(mIngredients, ingredients), true);
        this.mIngredients = ingredients;
        result.dispatchUpdatesTo(this);
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
        holder.unit.setText(MeasurementUnit.getMeasurementUnitString(ingredient.getUnit()));
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView quantity;
        public TextView unit;

        public IngredientViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.recipe_ingredient_name);
            quantity = view.findViewById(R.id.recipe_ingredient_quantity);
            unit = view.findViewById(R.id.recipe_ingredient_unit);
        }

    }
}
