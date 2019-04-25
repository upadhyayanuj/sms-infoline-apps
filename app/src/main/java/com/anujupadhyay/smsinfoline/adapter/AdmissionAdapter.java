package com.anujupadhyay.smsinfoline.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.anujupadhyay.smsinfoline.fragment.AdmissionGuideLinesFragment;
import com.anujupadhyay.smsinfoline.fragment.MediathequeFragment;

public class AdmissionAdapter extends FragmentPagerAdapter {

    private static final String TAG = AdmissionAdapter.class.getSimpleName();
    private static final int FRAGMENT_COUNT = 2;
    public AdmissionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AdmissionGuideLinesFragment();
            case 1:
                return new MediathequeFragment();
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
                return "Guidelines";
            case 1:
                return "Mediatheque";
        }
        return null;
    }
}
