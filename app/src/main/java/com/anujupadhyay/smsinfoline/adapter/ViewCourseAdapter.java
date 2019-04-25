package com.anujupadhyay.smsinfoline.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.anujupadhyay.smsinfoline.fragment.FragmentCourse;
import com.anujupadhyay.smsinfoline.fragment.FragmentViewCourses;
import com.anujupadhyay.smsinfoline.fragment.MediathequeFragment;

public class ViewCourseAdapter extends FragmentPagerAdapter {

    private static final String TAG = ViewCourseAdapter.class.getSimpleName();
    private static final int FRAGMENT_COUNT = 2;
    public ViewCourseAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentCourse();
            case 1:
                return new FragmentViewCourses();
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
                return "About";
            case 1:
                return "Courses";
        }
        return null;
    }
}
