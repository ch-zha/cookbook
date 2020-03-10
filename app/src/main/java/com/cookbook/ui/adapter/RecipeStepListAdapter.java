package com.cookbook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Step;
import com.cookbook.R;
import com.cookbook.ui.adapter.diffutils.StepDiffCallback;

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
        String displayStep = mSteps.get(position).getInstructions();
        holder.step.setText(displayStep);
        holder.number.setText((position + 1) + ". ");
    }

    public void updateList(List<Step> steps) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new StepDiffCallback(mSteps, steps), true);
        this.mSteps = steps;
        result.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder {

        TextView number;
        TextView step;

        StepViewHolder(View view) {
            super(view);

            number = view.findViewById(R.id.recipe_step_number);
            step = view.findViewById(R.id.recipe_step);
        }

    }
}
