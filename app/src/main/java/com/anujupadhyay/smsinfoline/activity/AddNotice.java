package com.anujupadhyay.smsinfoline.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddNotice extends AppCompatActivity {


    private Button button;
    private EditText editTextNoticeHeadLine, editTextNoticeLink, editTextNoticeDesc;
    private String currentUserId, CurrentUsername,CurrentTime, CurrentDate;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef, NotificationRef, AddNotificationKeyRef;
    private String CurrentNoticeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        CurrentNoticeName = getIntent().getExtras().get("NoticeTypeName").toString();
        Toast.makeText(this, "Welcome to "+CurrentNoticeName+" Notice", Toast.LENGTH_SHORT).show();
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        NotificationRef = FirebaseDatabase.getInstance().getReference().child("Notification").child(CurrentNoticeName);

        Initialize();

        GetUserInfo();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNoticeIntoDb();
                AddNewNotice();
            }
        });
    }



    private void Initialize() {
        getSupportActionBar().setTitle(CurrentNoticeName);
        button = findViewById(R.id.add_notices);
        editTextNoticeHeadLine = findViewById(R.id.notice_headline);
        editTextNoticeLink = findViewById(R.id.notice_link);
        editTextNoticeDesc =  findViewById(R.id.notice_description);
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

    private void AddNoticeIntoDb() {
        String noticeHead = editTextNoticeHeadLine.getText().toString();
        String noticeLink = editTextNoticeLink.getText().toString();
        String noticeDescription = editTextNoticeDesc.getText().toString();
        String NotificationKey = NotificationRef.push().getKey();

        if(TextUtils.isEmpty(noticeHead)){
            Toast.makeText(this, "Please Enter Notice Headline", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(noticeLink)){
            Toast.makeText(this, "Please Enter Notice Link", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(noticeDescription)){
            Toast.makeText(this, "Please Enter Notice Description", Toast.LENGTH_SHORT).show();

        }
        else{

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            CurrentDate = simpleDateFormat.format(calendar.getTime());

            Calendar calendarTime = Calendar.getInstance();
            SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm a");
            CurrentTime = simpleTimeFormat.format(calendarTime.getTime());

            HashMap<String, Object> AddNotificationKeys = new HashMap<>();
            NotificationRef.updateChildren(AddNotificationKeys);
            AddNotificationKeyRef = NotificationRef.child(NotificationKey);

            HashMap<String, Object> AddNoticeMap = new HashMap<>();
            AddNoticeMap.put("Username",CurrentUsername);
            AddNoticeMap.put("Notice_Headline", noticeHead);
            AddNoticeMap.put("Notice_Link", noticeLink);
            AddNoticeMap.put("Notice_Description", noticeDescription);
            AddNoticeMap.put("Time",CurrentTime);
            AddNoticeMap.put("Date",CurrentDate);
            AddNotificationKeyRef.updateChildren(AddNoticeMap);
            Toast.makeText(this, "Notice Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void AddNewNotice() {
        editTextNoticeHeadLine.setText("");
        editTextNoticeLink.setText("");
        editTextNoticeDesc.setText("");
    }
}