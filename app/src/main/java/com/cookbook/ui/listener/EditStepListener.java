package com.cookbook.ui.listener;

public interface EditStepListener {

    void onEditStep(String step, int step_id);

    void onAddStep(String step);

    void onDeleteStep(int step_id);

    void onReorderStep(String step, int step_id, int new_place);

}
