package com.cookbook.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cookbook.ui.PlannerFragment;
import com.cookbook.ui.ShoppingListFragment;
import com.example.cookbook.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private PlannerFragment planner;
    private ShoppingListFragment shoppingList;

    @StringRes
    private static final int[] TAB_TITLES = new int[] {R.string.menu_tab_text, R.string.pantry_tab_text};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    public PlannerFragment getPlanner() {
        return planner;
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("get item");
        // getItem is called to instantiate the fragment for the given page.
        if (position == 0) {
            this.planner = new PlannerFragment();
            return planner;
        }
        shoppingList = new ShoppingListFragment();
        return shoppingList;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}