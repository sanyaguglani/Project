package com.example.android.sportit.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.sportit.Fragments.AttendingEventsFrag;
import com.example.android.sportit.Fragments.MyEventsFrag;
import com.example.android.sportit.Fragments.ViewAllEventsFrag;
import com.example.android.sportit.R;

/**
 * Created by Sanya on 19/05/2017.
 */

public class FixTabPages extends FragmentPagerAdapter {
    private Context context;

    public FixTabPages(Context context,FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {    //Setting Tab Titles
        switch (position){
            case 0:
                return context.getString(R.string.category_attending);
            case 1:
                return context.getString(R.string.category_myEvents);
            case 2:
                return context.getString(R.string.category_viewAll);
            default:
                return null;
        }
    }


    @Override
    public Fragment getItem(int position) {         //Instantiate Tab fragments
        switch (position){
            case 0:
                return new AttendingEventsFrag();
            case 1:
                return new MyEventsFrag();
            case 2:
                return new ViewAllEventsFrag();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
