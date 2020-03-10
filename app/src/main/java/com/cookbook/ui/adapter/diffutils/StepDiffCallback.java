package com.cookbook.ui.adapter.diffutils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.cookbook.data.entity.Step;
import com.cookbook.data.entity.Step;

import java.util.List;

public class StepDiffCallback extends DiffUtil.Callback {

    private List<Step> newSteps;
    private List<Step> oldSteps;

    public StepDiffCallback(List<Step> oldSteps, List<Step> newSteps) {
        this.newSteps = newSteps;
        this.oldSteps = oldSteps;
    }

    @Override
    public int getOldListSize() {
        return oldSteps.size();
    }

    @Override
    public int getNewListSize() {
        return newSteps.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // Not checking for recipe id bc it is irrelevant for View/Edit recipe
        return (oldSteps.get(oldItemPosition).getStepId() == newSteps.get(newItemPosition).getStepId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Step oldIng = oldSteps.get(oldItemPosition);
        Step newIng = newSteps.get(newItemPosition);

        return (oldIng.getInstructions().equals(newIng.getInstructions()));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
