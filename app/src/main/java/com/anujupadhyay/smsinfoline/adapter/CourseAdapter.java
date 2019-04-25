package com.anujupadhyay.smsinfoline.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anujupadhyay.smsinfoline.R;
import com.anujupadhyay.smsinfoline.activity.Course_List;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<Course_List> course_lists;
    private Context context;

    public CourseAdapter(List<Course_List> course_lists, Context context) {
        this.course_lists = course_lists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Course_List courseList = course_lists.get(i);
        viewHolder.textCourseName.setText(courseList.getCourseName());
        viewHolder.textCourseDuration.setText(courseList.getCourseDuration());
        viewHolder.textCourseDesc.setText(courseList.getCourseDescription());
    }

    @Override
    public int getItemCount() {
        return course_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textCourseName, textCourseDuration, textCourseDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCourseName = itemView.findViewById(R.id.textview_course_name);
            textCourseDuration = itemView.findViewById(R.id.textview_course_duration);
            textCourseDesc = itemView.findViewById(R.id.textview_course_desc);
        }
    }
}
