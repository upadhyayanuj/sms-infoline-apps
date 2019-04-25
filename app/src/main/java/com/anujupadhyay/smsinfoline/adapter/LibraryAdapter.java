package com.anujupadhyay.smsinfoline.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.anujupadhyay.smsinfoline.fragment.LibIntroFragment;
import com.anujupadhyay.smsinfoline.fragment.LibResourcesFragment;
import com.anujupadhyay.smsinfoline.fragment.LibServicesFragment;
import com.anujupadhyay.smsinfoline.fragment.LibTimeFragment;

public class LibraryAdapter extends FragmentPagerAdapter {

    private static final String TAG = LibraryAdapter.class.getSimpleName();
    private static final int FRAGMENT_COUNT = 4;
    public LibraryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new LibIntroFragment();
            case 1:
                return new LibTimeFragment();
            case 2:
                return new LibServicesFragment();
            case 3:
                return new LibResourcesFragment();
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
                return "Time";
            case 2:
                return "Services";
            case 3:
                return "Resources";
        }
        return null;
    }
}
