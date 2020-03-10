package com.cookbook.ui.adapter.diffutils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.cookbook.data.entity.Entry;
import com.cookbook.data.entity.Ingredient;

import java.util.List;

public class IngredientDiffCallback extends DiffUtil.Callback {

    private List<Ingredient> newIngs;
    private List<Ingredient> oldIngs;

    public IngredientDiffCallback(List<Ingredient> oldIngs, List<Ingredient> newIngs) {
        this.newIngs = newIngs;
        this.oldIngs = oldIngs;
    }

    @Override
    public int getOldListSize() {
        return oldIngs.size();
    }

    @Override
    public int getNewListSize() {
        return newIngs.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // Not checking for recipe id bc that would return false positive in Shopping List and
        // is irrelevant for View/Edit recipe
        return (oldIngs.get(oldItemPosition).getName().equals(newIngs.get(newItemPosition).getName()));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Ingredient oldIng = oldIngs.get(oldItemPosition);
        Ingredient newIng = newIngs.get(newItemPosition);

        return (oldIng.getQuantity() == newIng.getQuantity()) && (oldIng.getUnit() == newIng.getUnit());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
