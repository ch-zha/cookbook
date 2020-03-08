package com.cookbook.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.ui.adapter.PlannerAdapter;
import com.cookbook.ui.listener.PlannerEntryListener;
import com.cookbook.ui.util.EditMode;
import com.cookbook.ui.util.PlannerLayout;
import com.cookbook.viewmodel.service.UpdatePlannerService;
import com.cookbook.viewmodel.viewmodel.PlannerViewModel;
import com.cookbook.R;

import java.util.ArrayList;
import java.util.List;

public class PlannerFragment extends Fragment implements EditMode, PlannerEntryListener {

    private RecyclerView recyclerView;
    private GestureDetectorCompat mDetector;

    private int dayToAddEntry = -1;
    private final List<AlertDialog> dialogs = new ArrayList<>();

    public static int DAYS_DISPLAYED = 7;

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
        this.recyclerView.setAdapter(new PlannerAdapter(new ArrayList<>(), this, this));
        viewModel.getPlanner().observe(this, entries -> {
            ((PlannerAdapter) recyclerView.getAdapter()).updateList(entries);
        });
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return root;

    }

    /** Should only be used by PlannerEntryAdapter when clicking "add entry" prompt **/
    public void addActiveDialog(AlertDialog dialog) {
        this.dialogs.add(dialog);
    }

    /** Should only be used by PlannerEntryAdapter when clicking "add entry" prompt **/
    public void setDayToAddEntry(int day) {
        this.dayToAddEntry = day;
    }

    /** Should only be used by MainActivity when adding entry **/
    void dismissAllActiveDialogs() {
        for (AlertDialog dialog : dialogs) {
            dialog.dismiss();
        }
        dialogs.clear();
    }

    /** Should only be used by MainActivity when adding entry **/
    int getDayToAddEntry() {
        if (dayToAddEntry < 0) {
            throw new IllegalStateException("Unable to identify day to add entry to");
        }
        return this.dayToAddEntry;
    }

    public void enterEditMode() {
        ((PlannerAdapter) recyclerView.getAdapter()).enterEditMode();
        ((MainActivity) getActivity()).setFabBehaviorToEdit();
    }

    public void exitEditMode() {
        ((PlannerAdapter) recyclerView.getAdapter()).exitEditMode();
    }

    @Override
    public void onClick(int meal_id) {

        Intent updateDB = new Intent(getContext(), UpdatePlannerService.class);
        updateDB.putExtra("action", UpdatePlannerService.Action.DELETE);
        updateDB.putExtra("entry_id", meal_id);
        getContext().startService(updateDB);

    }

    @Override
    public void startSearch(int day) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View search = getLayoutInflater().inflate(R.layout.search_widget, null);

        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = search.findViewById(R.id.search_widget);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        dialog.setView(search);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        setDayToAddEntry(day);
        addActiveDialog(alertDialog);
    }

    /*** Gesture Detectors ***/

    class PlannerGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            enterEditMode();
        }

    }
}
