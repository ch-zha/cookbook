package com.cookbook.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.ui.MainActivity;
import com.cookbook.ui.PlannerFragment;
import com.cookbook.viewmodel.MenuDay;
import com.cookbook.viewmodel.livedata.PlannerLiveData;
import com.example.cookbook.R;

import java.util.ArrayList;
import java.util.List;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.MenuViewHolder> {

    private List<PlannerLiveData> mDays = null;
    private RecyclerView.RecycledViewPool viewPool = null;
    private String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}; //TODO make this generated

    public PlannerAdapter(List<PlannerLiveData> planner_days) {
        mDays = planner_days;
        viewPool = new RecyclerView.RecycledViewPool();
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
        PlannerLiveData day = mDays.get(position);

        holder.name.setText(dayNames[position]);
        ((MealListAdapter) holder.mealListRv.getAdapter()).updateList(mDays.get(position).getValue()); //TODO figure out how to get observe working
    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public RecyclerView mealListRv;

        public MenuViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.menu_recipe_name);
            mealListRv = (RecyclerView) view.findViewById(R.id.rv_menu_card_meals);
            mealListRv.setAdapter(new MealListAdapter(new ArrayList<>()));
            mealListRv.setLayoutManager(new LinearLayoutManager(mealListRv.getContext()));
        }

    }
}
