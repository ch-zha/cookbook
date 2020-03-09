package com.cookbook.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Ingredient;
import com.cookbook.data.entity.MeasurementUnit;
import com.cookbook.ui.listener.EditIngredientListener;
import com.cookbook.R;

import java.util.Arrays;
import java.util.List;

public class EditRecipeIngredientListAdapter extends RecyclerView.Adapter<EditRecipeIngredientListAdapter.IngredientViewHolder> {

    private List<Ingredient> mIngredients;
    private EditIngredientListener listener;
    private double tempQuantity = 0;
    private MeasurementUnit tempUnit = MeasurementUnit.Whole;
    private List<String> unitOptions = Arrays.asList(MeasurementUnit.getAllUnitsAsString());

    public EditRecipeIngredientListAdapter(List<Ingredient> ingredients, EditIngredientListener listener) {
        mIngredients = ingredients;
        this.listener = listener;
    }

    public void updateList(List<Ingredient> ingredients) {
        this.mIngredients = ingredients;
        notifyDataSetChanged();
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
            holder.unit.setSelection(unitOptions.indexOf(MeasurementUnit.getMeasurementUnitString(ingredient.getUnit())));
            holder.clear.setVisibility(View.VISIBLE);
        } else if (position == mIngredients.size()) {
            holder.name.getText().clear();
            holder.quantity.getText().clear();
            holder.unit.setSelection(0);
            holder.clear.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mIngredients.size() + 1;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        EditText name;
        EditText quantity;
        Spinner unit;
        ImageButton clear;

        IngredientViewHolder(View view) {
            super(view);
            if (getAdapterPosition() < 0 || getAdapterPosition() > mIngredients.size()) {
                Log.d("Spinner", "adapter at position: " + getAdapterPosition());
            }

            name = view.findViewById(R.id.recipe_ingredient_name);
            quantity = view.findViewById(R.id.recipe_ingredient_quantity);
            unit = view.findViewById(R.id.recipe_ingredient_unit);
            clear = view.findViewById(R.id.clear);

            name.setHorizontallyScrolling(false);
            quantity.setHorizontallyScrolling(true);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(view.getContext(),
                    android.R.layout.simple_spinner_item,
                    unitOptions);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            unit.setAdapter(arrayAdapter);

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
                        listener.onAddIngredient(name, quantity, tempUnit);
                        //reset temps
                        tempQuantity = 0;
                        tempUnit = MeasurementUnit.Whole;
                    }
                    v.clearFocus();
                }
                return false;

            });

            unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String item = (String) parent.getItemAtPosition(position);
                    MeasurementUnit unit = MeasurementUnit.getMeasurementUnit(item);
                    if (getAdapterPosition() < 0) {
                        //TODO not sure why this is happening? maybe because of NestedScrollView? find out
                    } else if (getAdapterPosition() < mIngredients.size()) {
                        String ingredient = mIngredients.get(getAdapterPosition()).getName();
                        listener.onUpdateIngredientUnit(ingredient, unit);
                    } else {
                        tempUnit = unit;
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}

            });

            clear.setOnClickListener(v ->
                    listener.onDeleteIngredient(mIngredients.get(getAdapterPosition()).getName()));
        }
    }
}
