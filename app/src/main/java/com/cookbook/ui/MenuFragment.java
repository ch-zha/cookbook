package com.cookbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.content.DataManagerService;
import com.cookbook.event.MenuMessageEvent;
import com.cookbook.ui.adapters.MenuAdapter;
import com.cookbook.model.MenuDay;
import com.example.cookbook.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MenuFragment extends Fragment {

    private List<MenuDay> days = new ArrayList<>();
    private RecyclerView recyclerView = null;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        this.recyclerView = root.findViewById(R.id.rv_menu_cards);
        this.recyclerView.setAdapter(new MenuAdapter(this.days));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return root;
    }

    /* This event response should only be used to initialize menu from saved data on app startup, not to make changes*/
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MenuMessageEvent event) {
        this.days = event.days;
        if (recyclerView != null) {
            this.recyclerView.setAdapter(new MenuAdapter(this.days));
        }
        System.out.println("Menu Message Received");
    }
}
