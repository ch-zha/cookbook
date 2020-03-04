package com.cookbook.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.viewmodel.Ingredient;
import com.cookbook.ui.adapters.ShoppingListAdapter;
import com.example.cookbook.R;

import java.util.HashMap;

public class ShoppingListFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pantry, container, false);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv_pantry_ingredients);
        recyclerView.setAdapter(new ShoppingListAdapter(createSampleIngredientList()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    private HashMap<Ingredient, Float> createSampleIngredientList() {
        HashMap<Ingredient, Float> ingredients = new HashMap<>();
        ingredients.put(new Ingredient("Cheese"), 2f);
        ingredients.put(new Ingredient("Cocoa Powder", Ingredient.Measurement.Gram), 50f);
        ingredients.put(new Ingredient("Chicken Broth", Ingredient.Measurement.Gram), 3f);
        ingredients.put(new Ingredient("Spinach"), 5f);
        ingredients.put(new Ingredient("Onions"), 1f);
        ingredients.put(new Ingredient("Garlic"), 2f);
        ingredients.put(new Ingredient("Salt", Ingredient.Measurement.Gram), 30f);
        return ingredients;
    }
}
