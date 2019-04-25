package com.anujupadhyay.smsinfoline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anujupadhyay.smsinfoline.R;

public class SettingActivity extends AppCompatActivity {
    private ListView listView;
    String [] ListViewItem = new String[]{"Profile Setting", "Notification Setting", "Courses", "Users"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        listView = findViewById(R.id.listview_id);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, ListViewItem);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        startActivity(new Intent(SettingActivity.this,ProfileSettingActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(SettingActivity.this,NotificationSettingActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(SettingActivity.this,CourseInfo.class));
                        break;
                    case 3:
                        startActivity(new Intent(SettingActivity.this,ViewUsers.class));
                        break;
                }
            }
        });
    }
}
