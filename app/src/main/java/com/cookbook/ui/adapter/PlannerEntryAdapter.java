package com.cookbook.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Entry;
import com.cookbook.ui.adapter.diffutils.PlannerEntryDiffCallback;
import com.cookbook.ui.listener.PlannerEntryListener;
import com.cookbook.ui.util.EditMode;
import com.cookbook.viewmodel.viewmodel.PlannerViewModel;
import com.cookbook.R;

import java.util.List;

public class PlannerEntryAdapter extends RecyclerView.Adapter<PlannerEntryAdapter.MealViewHolder> implements EditMode {

    private List<Entry> mEntries;
    private PlannerViewModel viewModel;
    private Fragment owner;
    private PlannerEntryListener clickListener;

    private boolean editMode = false;

    /** Payloads **/
    public final String ENTER_EDIT_MODE = "ENTER_EDIT_MODE";
    public final String EXIT_EDIT_MODE = "EXIT_EDIT_MODE";

    PlannerEntryAdapter(List<Entry> entries, Fragment owner, PlannerEntryListener listener) {
        mEntries = entries;
        this.owner = owner;
        this.clickListener = listener;
        this.viewModel = ViewModelProviders.of(owner).get(PlannerViewModel.class);
    }

    @Override
    public void enterEditMode() {
        if (!editMode) {
            editMode = true;
            synchronized (this) {
                notifyItemInserted(mEntries.size());
                notifyItemRangeChanged(0, mEntries.size(), ENTER_EDIT_MODE);
            }
        }
    }

    @Override
    public void exitEditMode() {
        if (editMode) {
            editMode = false;
            synchronized (this) {
                notifyItemRemoved(mEntries.size());
                notifyItemRangeChanged(0, mEntries.size(), EXIT_EDIT_MODE);
            }
        }
    }

    public void updateList(@NonNull List<Entry> entries) {
        PlannerEntryDiffCallback diff = new PlannerEntryDiffCallback(this.mEntries, entries);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diff, true);
        this.mEntries = entries;
        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.planner_entry, parent, false);

        // Return a new holder instance
        return new MealViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            for (Object payload : payloads) {
                if (payload.equals(ENTER_EDIT_MODE)) {
                    holder.clear.setVisibility(View.VISIBLE);
                }
                if (payload.equals(EXIT_EDIT_MODE)) {
                    holder.clear.setVisibility(View.GONE);
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {

        if (position < mEntries.size()) {
            int recipe_id = mEntries.get(position).getRecipeId();
            holder.name.setTypeface(null, Typeface.NORMAL);
            //TODO this will probably cause bugs if recipe name changing is implemented. Find a way
            //to put this in main fragment/activity?
            viewModel.getRecipeName(recipe_id).observe(owner, name -> {
                holder.name.setText(name);
            });
        }

        if (editMode) {
            if (position == mEntries.size()) {
                holder.clear.setVisibility(View.INVISIBLE);
                holder.name.setText(owner.getString(R.string.add_entry_prompt));
                holder.name.setTypeface(null, Typeface.ITALIC);
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Note: real value for day gets set by clickListener (after bubbling up)
                        clickListener.startSearch(0);
                    }
                });
            } else {
                holder.name.setOnClickListener(null);
                holder.clear.setVisibility(View.VISIBLE);
            }
        } else
            holder.clear.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        if (editMode) return mEntries.size() + 1;
        return mEntries.size();
    }

    class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;
        private ImageButton clear;

        MealViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.entry_name);
            clear = view.findViewById(R.id.clear);

            clear.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            System.out.println("Position: " + getAdapterPosition());
            clickListener.onClick(mEntries.get(getAdapterPosition()).getMealId());
        }
    }
}
