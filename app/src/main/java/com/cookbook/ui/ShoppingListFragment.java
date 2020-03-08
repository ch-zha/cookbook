package com.cookbook.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.ui.adapter.ShoppingListAdapter;
import com.cookbook.R;
import com.cookbook.viewmodel.viewmodel.RecipeDetailViewModel;
import com.cookbook.viewmodel.viewmodel.ShoppingListViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingListFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shoppinglist, container, false);

        // Set viewmodel
        ShoppingListViewModel viewModel = ViewModelProviders.of(this).get(ShoppingListViewModel.class);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv_pantry_ingredients);
        recyclerView.setAdapter(new ShoppingListAdapter(new ArrayList<com.cookbook.data.entity.Ingredient>()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getShoppingList().observe(getViewLifecycleOwner(), list -> {
            ((ShoppingListAdapter) recyclerView.getAdapter()).updateList(list);
        });

        return root;
    }

}
