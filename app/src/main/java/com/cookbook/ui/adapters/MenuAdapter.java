package com.cookbook.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.viewmodel.MenuCard;
import com.example.cookbook.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<MenuCard> mDays = null;
    private RecyclerView.RecycledViewPool viewPool = null;

    public MenuAdapter(List<MenuCard> menuCards) {
        mDays = menuCards;
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
        MenuCard day = mDays.get(position);

        holder.name.setText(day.getDayName());
        holder.mealListRv.setAdapter(new MealListAdapter(day.getMeals()));
        holder.mealListRv.setLayoutManager(new LinearLayoutManager(holder.mealListRv.getContext()));
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
        }

    }
}
