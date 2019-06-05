package com.example.android.guide;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    Context context;
    public MyFragmentPagerAdapter(FragmentManager fragmentManager, Context context)
    {
        super(fragmentManager);
        this.context = context;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new HomeFragment();
            case 1:
                return  new HistoryFragment();
            case 2:
                return new FamilyFragment();
            case 3:
                return new FoodFragment();
            default:
                return new NatureFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return context.getResources().getString(R.string.home_fragment_tab_title);
            case 1:
                return context.getResources().getString(R.string.history_fragment_tab_title);
            case 2:
                return context.getResources().getString(R.string.family_fragment_tab_title);
            case 3:
                return context.getResources().getString(R.string.food_fragment_tab_title);
            case 4:
                return context.getResources().getString(R.string.nature_fragment_tab_title);
                default:
                    return null;
        }
    }
}
