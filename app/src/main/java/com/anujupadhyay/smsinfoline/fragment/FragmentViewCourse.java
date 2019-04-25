package com.anujupadhyay.smsinfoline.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anujupadhyay.smsinfoline.R;
import com.anujupadhyay.smsinfoline.adapter.AdmissionAdapter;
import com.anujupadhyay.smsinfoline.adapter.ViewCourseAdapter;

public class FragmentViewCourse extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public FragmentViewCourse(){
        //Constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_course_view, container,false);

        tabLayout = (TabLayout)view.findViewById(R.id.course_tabs);
        viewPager = (ViewPager)view.findViewById(R.id.course_view_pager);

        viewPager.setAdapter(new ViewCourseAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
