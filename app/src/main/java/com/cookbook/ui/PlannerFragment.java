package com.cookbook.ui;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.ui.adapter.PlannerAdapter;
import com.cookbook.ui.util.EditMode;
import com.cookbook.ui.util.PlannerLayout;
import com.cookbook.viewmodel.viewmodel.PlannerViewModel;
import com.example.cookbook.R;

public class PlannerFragment extends Fragment implements EditMode {

    private RecyclerView recyclerView;
    private GestureDetectorCompat mDetector;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        PlannerLayout root = (PlannerLayout) inflater.inflate(R.layout.fragment_planner, container, false);
        this.mDetector = new GestureDetectorCompat(getActivity(), new PlannerGestureListener());
        root.setGestureDetector(mDetector);

        PlannerViewModel viewModel = ViewModelProviders.of(this).get(PlannerViewModel.class);

        this.recyclerView = root.findViewById(R.id.rv_menu_cards);
        this.recyclerView.setAdapter(new PlannerAdapter(viewModel.getPlanner(), this));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return root;

    }

    public void enterEditMode() {
        ((PlannerAdapter) recyclerView.getAdapter()).enterEditMode();
        ((MainActivity) getActivity()).setFabBehaviorToEdit();
    }

    public void exitEditMode() {
        ((PlannerAdapter) recyclerView.getAdapter()).exitEditMode();
    }

    /*** Gesture Detectors ***/

    class PlannerGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            System.out.println("Down");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            System.out.println("Long press");
            enterEditMode();
        }

    }
}
