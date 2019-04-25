package com.anujupadhyay.smsinfoline.activity;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anujupadhyay.smsinfoline.R;
import com.anujupadhyay.smsinfoline.fragment.FragmentAddCourses;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AddCourse extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> course_type_list = new ArrayList<>();
    private DatabaseReference CourseListTypeReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        CourseListTypeReference = FirebaseDatabase.getInstance().getReference().child("Courses");

        InitializeFields();
        RetrieveAndDispCourseType();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentCourseType = parent.getItemAtPosition(position).toString();
                getSupportFragmentManager().beginTransaction().add(android.R.id.content, new FragmentAddCourses()).commit();
                getIntent().putExtra("CourseTypeName",currentCourseType);
            }
        });
    }

    private void InitializeFields() {
        listView = findViewById(R.id.view_course_type);
        arrayAdapter = new ArrayAdapter<String>(AddCourse.this, android.R.layout.simple_list_item_1, course_type_list);
        listView.setAdapter(arrayAdapter);
    }

    private void RetrieveAndDispCourseType() {
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
