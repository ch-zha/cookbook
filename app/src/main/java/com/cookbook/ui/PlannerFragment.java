package com.cookbook.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.ui.adapter.PlannerAdapter;
import com.cookbook.viewmodel.viewmodel.PlannerViewModel;
import com.example.cookbook.R;

public class PlannerFragment extends Fragment {

    private RecyclerView recyclerView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        PlannerViewModel viewModel = ViewModelProviders.of(this).get(PlannerViewModel.class);

        this.recyclerView = root.findViewById(R.id.rv_menu_cards);
        this.recyclerView.setAdapter(new PlannerAdapter(viewModel.getPlanner(), this));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return root;
    }

}
