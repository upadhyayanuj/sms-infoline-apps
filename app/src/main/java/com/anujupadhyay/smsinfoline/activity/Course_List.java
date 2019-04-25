package com.anujupadhyay.smsinfoline.activity;

public class Course_List {
    private String CourseName;
    private String CourseDuration;
    private String CourseDescription;

    public Course_List(String courseName, String courseDuration, String courseDescription) {
        CourseName = courseName;
        CourseDuration = courseDuration;
        CourseDescription = courseDescription;
    }

    public String getCourseName() {
        return CourseName;
    }

    public String getCourseDuration() {
        return CourseDuration;
    }

    public String getCourseDescription() {
        return CourseDescription;
    }
}
