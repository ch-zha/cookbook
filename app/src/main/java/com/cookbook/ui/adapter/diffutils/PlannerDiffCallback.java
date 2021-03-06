package com.cookbook.ui.adapter.diffutils;

import android.util.SparseArray;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.cookbook.data.entity.Entry;

import java.util.List;

public class PlannerDiffCallback extends DiffUtil.Callback {

    private SparseArray<List<Entry>> newEntries;
    private SparseArray<List<Entry>> oldEntries;

    public PlannerDiffCallback(SparseArray<List<Entry>> oldEntries, SparseArray<List<Entry>> newEntries) {
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
        return  oldItemPosition == newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (oldEntries.get(oldItemPosition).size() != newEntries.get(newItemPosition).size())
            return false;
        for (int i = 0; i < oldEntries.get(oldItemPosition).size(); i++) {
            if (oldEntries.get(oldItemPosition).get(i).getMealId() != newEntries.get(newItemPosition).get(i).getMealId())
                return false;
        }
        return true;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
