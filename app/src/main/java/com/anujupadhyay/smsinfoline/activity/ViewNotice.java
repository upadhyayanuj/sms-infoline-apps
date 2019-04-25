package com.anujupadhyay.smsinfoline.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;
import com.anujupadhyay.smsinfoline.adapter.NoticeAdapter;
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

public class ViewNotice extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Notice_List> noticeLists;
    private String CurrentNoticeName;
    private FirebaseAuth mAuth;
    private String currentUserId, CurrentUsername;
    private DatabaseReference UserRef, NotificationRef, AddNotificationKeyRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notice);
        recyclerView = findViewById(R.id.recyclerview_notice);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noticeLists = new ArrayList<>();

        CurrentNoticeName = getIntent().getExtras().get("NoticeTypeName").toString();
        Toast.makeText(this, "Welcome to "+CurrentNoticeName+" Notice", Toast.LENGTH_SHORT).show();
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        NotificationRef = FirebaseDatabase.getInstance().getReference().child("Notification").child(CurrentNoticeName);

        Initialize();

        GetUserInfo();

    }

    @Override
    protected void onStart() {
        super.onStart();
        NotificationRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(dataSnapshot.exists()){
                    RetrieveAndDispNotice(dataSnapshot);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(dataSnapshot.exists()){
                    RetrieveAndDispNotice(dataSnapshot);
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

    private void Initialize() {
        getSupportActionBar().setTitle(CurrentNoticeName);
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

    private void RetrieveAndDispNotice(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()){
            String Notice_Date = (String) ((DataSnapshot)iterator.next()).getValue();
            String Notice_Desc = (String) ((DataSnapshot)iterator.next()).getValue();
            String Notice_Head = (String) ((DataSnapshot)iterator.next()).getValue();
            String Notice_Link = (String) ((DataSnapshot)iterator.next()).getValue();
            String Notice_Time = (String) ((DataSnapshot)iterator.next()).getValue();
            String Notice_Admin= (String) ((DataSnapshot)iterator.next()).getValue();

            Notice_List noticeList = new Notice_List(Notice_Head, Notice_Link, Notice_Desc, Notice_Admin, Notice_Time +" "+Notice_Date);
            noticeLists.add(noticeList);
        }

        adapter = new NoticeAdapter(noticeLists, this);
        recyclerView.setAdapter(adapter);
    }
}
