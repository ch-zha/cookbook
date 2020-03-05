package com.cookbook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Ingredient;
import com.cookbook.data.entity.MeasurementUnit;
import com.cookbook.ui.listener.EditIngredientListener;
import com.example.cookbook.R;

import java.util.List;

public class EditRecipeIngredientListAdapter extends RecyclerView.Adapter<EditRecipeIngredientListAdapter.IngredientViewHolder> {

    private List<Ingredient> mIngredients;
    private EditIngredientListener listener;
    private double tempQuantity = 0;

    public EditRecipeIngredientListAdapter(List<Ingredient> ingredients, EditIngredientListener listener) {
        mIngredients = ingredients;
        this.listener = listener;
    }

    public void updateList(List<Ingredient> ingredients) {
        this.mIngredients = ingredients;
        synchronized (this) {
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.edit_recipe_ingredient_item, parent, false);

        // Return a new holder instance
        return new IngredientViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        if (position < mIngredients.size()) {
            Ingredient ingredient = mIngredients.get(position);

            holder.name.setText(ingredient.getName());
            holder.quantity.setText(Double.toString(ingredient.getQuantity()));
        } else if (position == mIngredients.size()) {
            holder.quantity.setText(Double.toString(tempQuantity));
        }
    }

    @Override
    public int getItemCount() {
        return mIngredients.size() + 1;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        public EditText name;
        public EditText quantity;

        public IngredientViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.recipe_ingredient_name);
            quantity = view.findViewById(R.id.recipe_ingredient_quantity);

            name.setHorizontallyScrolling(false);
            quantity.setHorizontallyScrolling(true);

            quantity.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (getAdapterPosition() < mIngredients.size()) {
                        Ingredient edited = mIngredients.get(getAdapterPosition());
                        listener.onUpdateIngredientQuantity(edited.getName(), Double.valueOf(v.getText().toString()));
                    } else if (getAdapterPosition() == mIngredients.size()) {
                        tempQuantity = Double.valueOf(v.getText().toString());
                    }
                    v.clearFocus();
                }
                return false;
            });

            name.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (getAdapterPosition() < mIngredients.size()) {
                        Ingredient edited = mIngredients.get(getAdapterPosition());
                        listener.onUpdateIngredientName(edited.getName(), v.getText().toString());
                    } else if (getAdapterPosition() == mIngredients.size()) {
                        String name = v.getText().toString();
                        double quantity = Double.valueOf(tempQuantity);
                        listener.onAddIngredient(name, quantity, MeasurementUnit.Whole); //todo make unit editable
                    }
                    v.clearFocus();
                }
                return false;
            });
        }
    }
}
