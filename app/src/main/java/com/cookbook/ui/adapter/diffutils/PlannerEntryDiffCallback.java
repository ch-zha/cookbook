package com.cookbook.ui.adapter.diffutils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.cookbook.data.entity.Entry;

import java.util.List;

public class PlannerEntryDiffCallback extends DiffUtil.Callback {

    private List<Entry> newEntries;
    private List<Entry> oldEntries;

    public PlannerEntryDiffCallback(List<Entry> oldEntries, List<Entry> newEntries) {
        this.newEntries = newEntries;
        this.oldEntries = oldEntries;
    }

    @Override
    public int getOldListSize() {
        return oldEntries.size();
    }

    @Override
    public int getNewListSize() {
        return newEntries.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldEntries.get(oldItemPosition).getMealId() == newEntries.get(newItemPosition).getMealId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldEntries.get(oldItemPosition).getMealId() == newEntries.get(newItemPosition).getMealId();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
