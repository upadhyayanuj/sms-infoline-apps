package com.anujupadhyay.smsinfoline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anujupadhyay.smsinfoline.R;

public class NotificationSettingActivity extends AppCompatActivity {
    private ListView listView;
    String [] ListViewItem = new String[]{"Add Notice Type", "Update Notices"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);

        listView = findViewById(R.id.notice_setting_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, ListViewItem);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(NotificationSettingActivity.this, AddNoticeType.class));
                        break;
                    case 1:
                        startActivity(new Intent(NotificationSettingActivity.this, UpdateNoticeActivity.class));
                        break;
                }
            }
        });
    }
}
