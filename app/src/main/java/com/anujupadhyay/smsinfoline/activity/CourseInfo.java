package com.anujupadhyay.smsinfoline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anujupadhyay.smsinfoline.R;
import com.anujupadhyay.smsinfoline.fragment.FragmentAddCourseType;
import com.anujupadhyay.smsinfoline.fragment.FragmentAddCourses;
import com.anujupadhyay.smsinfoline.fragment.FragmentViewCourses;

public class CourseInfo extends AppCompatActivity {

    private ListView listView;
    String [] ListViewItem = new String[]{"Add Course Type", "Add Course", "View Courses"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);
        listView = findViewById(R.id.listview_cid);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, ListViewItem);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        getSupportFragmentManager().beginTransaction().add(android.R.id.content, new FragmentAddCourseType()).commit();
                        break;
                    case 1:
                        startActivity(new Intent(CourseInfo.this,AddCourse.class));
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().add(android.R.id.content, new FragmentViewCourses()).commit();
                        break;
                }
            }
        });
    }
}
