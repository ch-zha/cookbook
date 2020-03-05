package com.cookbook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Step;
import com.example.cookbook.R;

import java.util.List;

public class RecipeStepListAdapter extends RecyclerView.Adapter<RecipeStepListAdapter.StepViewHolder> {

    private List<Step> mSteps;

    public RecipeStepListAdapter(List<Step> steps) {
        this.mSteps = steps;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recipe_step_item, parent, false);

        // Return a new holder instance
        StepViewHolder viewHolder = new StepViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        String displayStep = Integer.toString(position + 1) + ". " + mSteps.get(position).getInstructions();
        holder.step.setText(displayStep);
    }

    public void updateList(List<Step> steps) {
        this.mSteps = steps;
        synchronized (this) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public static class StepViewHolder extends RecyclerView.ViewHolder {

        public TextView step;

        public StepViewHolder(View view) {
            super(view);

            step = view.findViewById(R.id.recipe_step);
        }

    }
}