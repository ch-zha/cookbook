package com.cookbook.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Meal;
import com.cookbook.ui.util.EditMode;
import com.cookbook.viewmodel.viewmodel.PlannerViewModel;
import com.example.cookbook.R;

import java.util.List;

public class PlannerEntryAdapter extends RecyclerView.Adapter<PlannerEntryAdapter.MealViewHolder> implements EditMode {

    private List<Meal> mMeals;
    private PlannerViewModel viewModel;
    private Fragment owner;

    private boolean editMode = false;

    /** Payloads **/
    public final String ENTER_EDIT_MODE = "ENTER_EDIT_MODE";
    public final String EXIT_EDIT_MODE = "EXIT_EDIT_MODE";

    PlannerEntryAdapter(List<Meal> meals, Fragment owner) {
        mMeals = meals;
        this.owner = owner;
        this.viewModel = ViewModelProviders.of(owner).get(PlannerViewModel.class);
    }

    @Override
    public void enterEditMode() {
        if (!editMode) {
            editMode = true;
            notifyItemInserted(mMeals.size());
            notifyItemRangeChanged(0, mMeals.size(), ENTER_EDIT_MODE);
        }
    }

    @Override
    public void exitEditMode() {
        if (editMode) {
            editMode = false;
            notifyItemRemoved(mMeals.size());
            notifyItemRangeChanged(0, mMeals.size(), EXIT_EDIT_MODE);
        }
    }

    public void updateList(List<Meal> meals) {
        this.mMeals = meals;
        synchronized (this) {
            notifyDataSetChanged();
        }
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

        if (position < mMeals.size()) {
            int recipe_id = mMeals.get(position).getRecipeId();
            viewModel.getRecipeName(recipe_id).observe(owner, name -> {
                holder.name.setText(name);
            });
        }

        if (editMode) {
            if (position == mMeals.size()) {
                holder.name.setText("Add an entry...");
                holder.name.setTypeface(null, Typeface.ITALIC);
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Open add entry dialog");
                    }
                });
            } else {
                holder.clear.setVisibility(View.VISIBLE);
            }
        } else
            holder.clear.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if (editMode) return mMeals.size() + 1;
        return mMeals.size();
    }

    class MealViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageButton clear;

        private Fragment owner;

        MealViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.menu_meal_name);
            clear = (ImageButton) view.findViewById(R.id.clear_entry);

            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("clicked " + getAdapterPosition());
                }
            });

            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("planner entry on touch");
                    return false;
                }
            });
        }

    }
}
