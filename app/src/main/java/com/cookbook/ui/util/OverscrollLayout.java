package com.cookbook.ui.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookbook.ui.listener.OverscrollListener;

public class OverscrollLayout extends LinearLayoutManager {

    OverscrollListener listener;

    public OverscrollLayout(Context context, OverscrollListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    public int scrollVerticallyBy ( int dx, RecyclerView.Recycler recycler,
                                    RecyclerView.State state ) {
        int scrollRange = super.scrollVerticallyBy(dx, recycler, state);
        int overscroll = dx - scrollRange;
        if (overscroll > 0) {
            // bottom overscroll
            listener.onBottomOverscroll();
        } else if (overscroll < 0) {
            // top overscroll
            listener.onTopOverscroll();
        }
        return scrollRange;
    }

}
