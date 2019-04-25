package com.anujupadhyay.smsinfoline.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FragmentAddCourses extends Fragment {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference CourseRef, AddCourseKeyRef;
    private EditText CourseName, CourseDuration, CourseDescription;
    private Button AddCourse;
    private String CurrentCourseName;
    public FragmentAddCourses() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_courses, container,false);

        CurrentCourseName = getActivity().getIntent().getExtras().get("CourseTypeName").toString();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(CurrentCourseName);
        Toast.makeText(getContext(), "Welcome to " +CurrentCourseName, Toast.LENGTH_SHORT).show();

        CourseName = view.findViewById(R.id.course_name);
        CourseDuration = view.findViewById(R.id.course_duration);
        CourseDescription  = view.findViewById(R.id.course_description);
        AddCourse = view.findViewById(R.id.add_courses);

        firebaseAuth = FirebaseAuth.getInstance();
        CourseRef = FirebaseDatabase.getInstance().getReference().child("Courses").child(CurrentCourseName);
        AddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCourseInDb();
                AddNewCourse();
            }
        });
        return view;
    }

    private void AddCourseInDb() {
        String CName = CourseName.getText().toString();
        String CDuration = CourseDuration.getText().toString();
        String CDesc = CourseDescription.getText().toString();
        String CourseKey = CourseRef.push().getKey();

        if(TextUtils.isEmpty(CName)){
            Toast.makeText(getContext(), "Enter Course Name", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(CDuration)){
            Toast.makeText(getContext(), "Enter Course Duration", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(CDesc)){
            Toast.makeText(getContext(), "Enter Course Description", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String, Object> AddCourseKeys = new HashMap<>();
            CourseRef.updateChildren(AddCourseKeys);
            AddCourseKeyRef = CourseRef.child(CourseKey);

            HashMap<String, Object> AddCourseMap = new HashMap<>();
            AddCourseMap.put("Name",CName);
            AddCourseMap.put("Duration",CDuration);
            AddCourseMap.put("Description",CDesc);
            AddCourseKeyRef.updateChildren(AddCourseMap);
            Toast.makeText(getContext(), "Course Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void AddNewCourse() {
        CourseName.setText("");
        CourseDuration.setText("");
        CourseDescription.setText("");
    }
}
