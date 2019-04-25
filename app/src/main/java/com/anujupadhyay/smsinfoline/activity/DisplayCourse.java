package com.anujupadhyay.smsinfoline.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;
import com.anujupadhyay.smsinfoline.adapter.CourseAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DisplayCourse extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Course_List> course_lists;
    private String CurrentCourseName;
    private FirebaseAuth mAuth;
    private String currentUserId, CurrentUsername;
    private DatabaseReference UserRef, CourseRef, AddCourseKeyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_course);

        recyclerView = findViewById(R.id.recyclerview_course);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        course_lists = new ArrayList<>();

        CurrentCourseName = getIntent().getExtras().get("CourseTypeName").toString();
        Toast.makeText(this, "Welcome to "+CurrentCourseName+" Course", Toast.LENGTH_SHORT).show();
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        CourseRef = FirebaseDatabase.getInstance().getReference().child("Courses").child(CurrentCourseName);

        Initialize();

        GetUserInfo();
    }

    private void Initialize() {
        getSupportActionBar().setTitle(CurrentCourseName);
    }

    private void GetUserInfo() {
        UserRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    CurrentUsername = dataSnapshot.child("Name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        CourseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    RetriveAndDispCourses(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    RetriveAndDispCourses(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void RetriveAndDispCourses(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while(iterator.hasNext()){
            String Course_Desc = (String) ((DataSnapshot)iterator.next()).getValue();
            String Course_Duration = (String) ((DataSnapshot)iterator.next()).getValue();
            String Course_Name = (String) ((DataSnapshot)iterator.next()).getValue();

            Course_List courseList = new Course_List(Course_Name, Course_Duration, Course_Desc);
            course_lists.add(courseList);
        }
        adapter = new CourseAdapter(course_lists, this);
        recyclerView.setAdapter(adapter);
    }
}
