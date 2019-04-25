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
import com.anujupadhyay.smsinfoline.adapter.CollegeProfileAdapter;

public class FragmentCollegeProfile extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public FragmentCollegeProfile(){
        //Constructor
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_college_profile, container,false);

        tabLayout = (TabLayout)view.findViewById(R.id.college_profile_tabs);
        viewPager = (ViewPager)view.findViewById(R.id.college_profile_view_pager);

        viewPager.setAdapter(new CollegeProfileAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
