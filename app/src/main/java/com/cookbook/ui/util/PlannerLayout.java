package com.cookbook.ui.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

public class PlannerLayout extends ConstraintLayout {

    private GestureDetectorCompat mDetector;

    public PlannerLayout(Context context) {
        super(context);
    }

    public PlannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*** Custom stuff for gestures ***/

    public void setGestureDetector(GestureDetectorCompat detector) {
        this.mDetector = detector;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDetector.onTouchEvent(ev) || super.onInterceptTouchEvent(ev);
    }

}
