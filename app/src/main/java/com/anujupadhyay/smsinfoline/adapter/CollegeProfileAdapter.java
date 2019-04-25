package com.anujupadhyay.smsinfoline.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.anujupadhyay.smsinfoline.fragment.CollegeIntroFragment;
import com.anujupadhyay.smsinfoline.fragment.CollegeVisionFragment;
import com.anujupadhyay.smsinfoline.fragment.PrincipalDeskFragment;

public class CollegeProfileAdapter extends FragmentPagerAdapter {

    private static final String TAG = CollegeProfileAdapter.class.getSimpleName();
    private static final int FRAGMENT_COUNT = 3;
    public CollegeProfileAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CollegeIntroFragment();
            case 1:
                return new CollegeVisionFragment();
            case 2:
                return new PrincipalDeskFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Introduction";
            case 1:
                return "Vision";
            case 2:
                return "Principal's Desk";
        }
        return null;
    }
}
