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

import com.cookbook.ui.adapters.MenuAdapter;
import com.cookbook.viewmodel.MenuCard;
import com.example.cookbook.R;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private List<MenuCard> days;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        days = new ArrayList<>();
        days.add(new MenuCard("Monday"));
        days.add(new MenuCard("Tuesday"));
        days.add(new MenuCard("Wednesday"));
        days.add(new MenuCard("Thursday"));
        days.add(new MenuCard("Friday"));
        days.add(new MenuCard("Saturday"));
        days.add(new MenuCard("Sunday"));

        for (MenuCard day : days) {
            day.addSampleMeals();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv_menu_cards);
        recyclerView.setAdapter(new MenuAdapter(this.days));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return root;
    }
}
