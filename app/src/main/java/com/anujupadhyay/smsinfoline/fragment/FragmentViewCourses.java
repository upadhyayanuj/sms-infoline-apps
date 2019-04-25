package com.anujupadhyay.smsinfoline.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anujupadhyay.smsinfoline.R;
import com.anujupadhyay.smsinfoline.activity.DisplayCourse;
import com.anujupadhyay.smsinfoline.activity.ViewNotice;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FragmentViewCourses extends Fragment {

    private View course_type_view;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> course_type_list = new ArrayList<>();
    private DatabaseReference CourseListTypeReference;

    public FragmentViewCourses() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        course_type_view = inflater.inflate(R.layout.fragment_view_courses, container,false);

        CourseListTypeReference = FirebaseDatabase.getInstance().getReference().child("Courses");

        InitializeFields();
        RetrieveAndDispNoticeType();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentCourseType = parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(getContext(), DisplayCourse.class);
                intent.putExtra("CourseTypeName",currentCourseType);
                startActivity(intent);
            }
        });

        return course_type_view;
    }



    private void InitializeFields() {

        listView = course_type_view.findViewById(R.id.view_course_type);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, course_type_list);
        listView.setAdapter(arrayAdapter);
    }

    private void RetrieveAndDispNoticeType() {
        CourseListTypeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<>();

                Iterator iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()){

                    set.add(((DataSnapshot) iterator.next()).getKey());
                }

                course_type_list.clear();
                course_type_list.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
