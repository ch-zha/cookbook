package com.cookbook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
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
import com.example.cookbook.R;

import java.util.ArrayList;
import java.util.List;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.MenuViewHolder> implements LifecycleObserver {

    private Fragment owner;
    private List<LiveData<List<Meal>>> mDays = null;
    private RecyclerView.RecycledViewPool viewPool = null;
    private String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}; //TODO make this generated

    public PlannerAdapter(List<LiveData<List<Meal>>> planner_days, Fragment owner) {

        this.owner = owner;
        this.mDays = planner_days;
        this.viewPool = new RecyclerView.RecycledViewPool();

    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.menu_card, parent, false);

        // Return a new holder instance
        MenuViewHolder viewHolder = new MenuViewHolder(contactView);
        viewHolder.mealListRv.setRecycledViewPool(viewPool);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {

        LiveData<List<Meal>> day = mDays.get(position);

        holder.name.setText(dayNames[position]);
        //TODO find if there is way to move callback out of onbind
        day.observe(owner, meals -> {
            ((MealListAdapter) holder.mealListRv.getAdapter()).updateList(meals);
        });

    }

    @Override
    public int getItemCount() {

        return mDays.size();

    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private RecyclerView mealListRv;

        MenuViewHolder(View view) {

            super(view);
            name = (TextView) view.findViewById(R.id.menu_recipe_name);
            mealListRv = (RecyclerView) view.findViewById(R.id.rv_menu_card_meals);
            mealListRv.setAdapter(new MealListAdapter(new ArrayList<>(), owner));
            mealListRv.setLayoutManager(new LinearLayoutManager(mealListRv.getContext()));

        }

    }
}
