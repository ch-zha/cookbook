package com.cookbook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Meal;
import com.cookbook.ui.util.EditMode;
import com.example.cookbook.R;

import java.util.ArrayList;
import java.util.List;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.MenuViewHolder> implements EditMode {

    private Fragment owner;
    private List<LiveData<List<Meal>>> mDays = null;
    private RecyclerView.RecycledViewPool viewPool = null;
    private String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}; //TODO make this generated

    private boolean editMode = false;

    /*** Payloads ***/
    private final String ENTER_EDIT_MODE = "ENTER_EDIT_MODE";
    private final String EXIT_EDIT_MODE = "EXIT_EDIT_MODE";


    public PlannerAdapter(List<LiveData<List<Meal>>> planner_days, Fragment owner) {

        this.owner = owner;
        this.mDays = planner_days;
        this.viewPool = new RecyclerView.RecycledViewPool();

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
        viewHolder.mealListRv.setRecycledViewPool(viewPool);
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

        LiveData<List<Meal>> day = mDays.get(position);

        holder.name.setText(dayNames[position]);
        //TODO find if there is way to move callback out of onbind
        day.observe(owner, meals -> {
            ((PlannerEntryAdapter) holder.mealListRv.getAdapter()).updateList(meals);
        });
        if (editMode)
            holder.adapter.enterEditMode();
        else
            holder.adapter.exitEditMode();

    }

    @Override
    public int getItemCount() {

        return mDays.size();

    }

    class MenuViewHolder extends RecyclerView.ViewHolder implements EditMode {

        private TextView name;
        private RecyclerView mealListRv;
        private PlannerEntryAdapter adapter;

        MenuViewHolder(View view) {

            super(view);
            name = (TextView) view.findViewById(R.id.menu_recipe_name);
            adapter = new PlannerEntryAdapter(new ArrayList<>(), owner);

            mealListRv = (RecyclerView) view.findViewById(R.id.rv_menu_card_meals);
            mealListRv.setAdapter(adapter);
            mealListRv.setLayoutManager(new LinearLayoutManager(mealListRv.getContext()));

            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("Planner on touch");
                    return false;
                }
            });

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
