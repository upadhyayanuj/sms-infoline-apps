package com.anujupadhyay.smsinfoline.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anujupadhyay.smsinfoline.R;
import com.anujupadhyay.smsinfoline.adapter.UserAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewUsers extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DatabaseReference UsersRef;
    private List<UserListItem> userListItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        recyclerView = findViewById(R.id.user_view_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userListItems = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Users");
    }

    @Override
    protected void onStart() {
        super.onStart();
        UsersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    RetrieveAndDispUser(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    RetrieveAndDispUser(dataSnapshot);
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

    private void RetrieveAndDispUser(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();
        //userListItems.clear();
        if((dataSnapshot.exists()) && (dataSnapshot.hasChild("Images"))){
            while (iterator.hasNext()){
               // userListItems.clear();
                String UserAbout = (String) ((DataSnapshot)iterator.next()).getValue();
                String UserClass = (String) ((DataSnapshot)iterator.next()).getValue();
                String UserDivision = (String) ((DataSnapshot)iterator.next()).getValue();
                String UserImage = (String) ((DataSnapshot)iterator.next()).getValue();
                String UserName = (String) ((DataSnapshot)iterator.next()).getValue();
                String UserRollNumber= (String) ((DataSnapshot)iterator.next()).getValue();
                String Userid= (String) ((DataSnapshot)iterator.next()).getValue();

                String n = dataSnapshot.child("Name").getValue().toString();
                String img = dataSnapshot.child("Images").getValue().toString();
                String ab = dataSnapshot.child("About").getValue().toString();

                UserListItem userListItem = new UserListItem(n, img, ab);
                userListItems.add(userListItem);
            }
        }else{
            while (iterator.hasNext()){
                //userListItems.clear();
                String UserAbout = (String) ((DataSnapshot)iterator.next()).getValue();
                String UserClass = (String) ((DataSnapshot)iterator.next()).getValue();
                String UserDivision = (String) ((DataSnapshot)iterator.next()).getValue();
                String UserName = (String) ((DataSnapshot)iterator.next()).getValue();
                String UserRollNumber= (String) ((DataSnapshot)iterator.next()).getValue();
                String Userid= (String) ((DataSnapshot)iterator.next()).getValue();

                String n = dataSnapshot.child("Name").getValue().toString();
                //String img = dataSnapshot.child("Name").getValue().toString();
                String ab = dataSnapshot.child("About").getValue().toString();

                UserListItem userListItem = new UserListItem(n, null, ab);
                userListItems.add(userListItem);

            }
        }
        adapter = new UserAdapter(userListItems, this);

//        adapter.notify();
//        adapter.notifyAll();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
    }
}
