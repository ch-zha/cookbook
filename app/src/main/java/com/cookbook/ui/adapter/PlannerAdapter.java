package com.cookbook.ui.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Entry;
import com.cookbook.ui.PlannerFragment;
import com.cookbook.ui.adapter.diffutils.PlannerDiffCallback;
import com.cookbook.ui.listener.PlannerEntryListener;
import com.cookbook.ui.util.EditMode;
import com.cookbook.R;

import java.util.ArrayList;
import java.util.List;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.MenuViewHolder> implements EditMode {

    private Fragment owner;
    private List<Entry> mEntries = null;
    private SparseArray<List<Entry>> mDays = null;
    private RecyclerView.RecycledViewPool viewPool = null;
    private PlannerEntryListener listener;
    private String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}; //TODO make this generated

    private boolean editMode = false;

    /*** Payloads ***/
    private final String ENTER_EDIT_MODE = "ENTER_EDIT_MODE";
    private final String EXIT_EDIT_MODE = "EXIT_EDIT_MODE";


    public PlannerAdapter(List<Entry> planner_days, Fragment owner, PlannerEntryListener listener) {

        this.owner = owner;
        this.listener = listener;
        this.mEntries = planner_days;
        this.mDays = sortEntriesIntoDays(mEntries);
        this.viewPool = new RecyclerView.RecycledViewPool();

    }

    public void updateList(@NonNull List<Entry> entries) {
        this.mEntries = entries;
        SparseArray<List<Entry>> newDays = sortEntriesIntoDays(entries);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new PlannerDiffCallback(mDays, newDays), false);
        this.mDays = newDays;
        result.dispatchUpdatesTo(this);
    }

    private SparseArray<List<Entry>> sortEntriesIntoDays(List<Entry> entries) {
        SparseArray<List<Entry>> newDays = new SparseArray<>();
        for (int i = 0; i < PlannerFragment.DAYS_DISPLAYED; i++) {
            newDays.put(i, new ArrayList<>());
        }
        for (Entry entry : entries) {
            newDays.get(entry.getDay()).add(entry);
        }
        return newDays;
    }

    @Override
    public void enterEditMode() {
        if (!editMode) {
            editMode = true;
            notifyItemRangeChanged(0, mDays.size(), ENTER_EDIT_MODE);
        }
    }

    @Override
    public void exitEditMode() {
        if (editMode) {
            editMode = false;
            notifyItemRangeChanged(0, mDays.size(), EXIT_EDIT_MODE);
        }
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.planner_card, parent, false);

        // Return a new holder instance
        MenuViewHolder viewHolder = new MenuViewHolder(contactView);
//        viewHolder.mealListRv.setRecycledViewPool(viewPool);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            for (Object payload : payloads) {
                if (payload.equals(ENTER_EDIT_MODE)) {
                    holder.adapter.enterEditMode();
                } else if (payload.equals(EXIT_EDIT_MODE)) {
                    holder.adapter.exitEditMode();
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {

        List<Entry> day = mDays.get(position);

        holder.name.setText(dayNames[position]);
        ((PlannerEntryAdapter) holder.mealListRv.getAdapter()).updateList(day);

        if (editMode)
            holder.adapter.enterEditMode();
        else
            holder.adapter.exitEditMode();

    }

    @Override
    public int getItemCount() {

        return mDays.size();

    }

    class MenuViewHolder extends RecyclerView.ViewHolder implements EditMode, PlannerEntryListener {

        private TextView name;
        private RecyclerView mealListRv;
        private PlannerEntryAdapter adapter;

        MenuViewHolder(View view) {

            super(view);
            name = (TextView) view.findViewById(R.id.menu_recipe_name);
            adapter = new PlannerEntryAdapter(new ArrayList<>(), owner, this);

            mealListRv = (RecyclerView) view.findViewById(R.id.rv_menu_card_meals);
            mealListRv.setAdapter(adapter);
            mealListRv.setLayoutManager(new LinearLayoutManager(mealListRv.getContext()));

        }

        @Override
        public void onClick(int meal_id) {
            listener.onClick(meal_id);
        }

        @Override
        public void startSearch(int day) {
            listener.startSearch(getAdapterPosition());
        }

        @Override
        public void enterEditMode() {
            adapter.enterEditMode();
        }

        @Override
        public void exitEditMode() {
            adapter.exitEditMode();
        }
    }
}
