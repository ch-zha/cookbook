package com.cookbook.ui.adapter;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.data.entity.Step;
import com.cookbook.ui.listener.EditStepListener;
import com.example.cookbook.R;

import java.util.List;

public class EditRecipeStepListAdapter extends RecyclerView.Adapter<EditRecipeStepListAdapter.StepViewHolder> {

    private List<Step> mSteps;
    private EditStepListener listener;

    public EditRecipeStepListAdapter(List<Step> steps, EditStepListener listener) {
        this.mSteps = steps;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.edit_recipe_step_item, parent, false);

        // Return a new holder instance
        StepViewHolder viewHolder = new StepViewHolder(contactView);
        return viewHolder;
    }

    public void updateList(List<Step> steps) {
        this.mSteps = steps;
        synchronized (this) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        if (position < mSteps.size()) {
            String displayStep = Integer.toString(position + 1) + ". " + mSteps.get(position).getInstructions();
            holder.step.setText(displayStep);
        }
    }

    @Override
    public int getItemCount() {
        return mSteps.size() + 1;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements TextView.OnEditorActionListener {

        public EditText step;

        public StepViewHolder(View view) {
            super(view);

            step = view.findViewById(R.id.recipe_step);
            step.setHorizontallyScrolling(false);
            step.setOnEditorActionListener(this);
        }


        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (getAdapterPosition() < mSteps.size()) {
                    Step edited = mSteps.get(getAdapterPosition());
                    listener.onEditStep(v.getText().toString(), edited.getStepId());
                } else if (getAdapterPosition() == mSteps.size()) {
                    listener.onAddStep(v.getText().toString());
                }
                v.clearFocus();
            }
            return false;
        }
    }
}
