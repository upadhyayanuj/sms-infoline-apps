package com.anujupadhyay.smsinfoline.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;
import com.anujupadhyay.smsinfoline.fragment.FragmentAboutUs;
import com.anujupadhyay.smsinfoline.fragment.FragmentAddNotice;
import com.anujupadhyay.smsinfoline.fragment.FragmentAdmission;
import com.anujupadhyay.smsinfoline.fragment.FragmentCollegeProfile;
import com.anujupadhyay.smsinfoline.fragment.FragmentCourse;
import com.anujupadhyay.smsinfoline.fragment.FragmentFloorInformation;
import com.anujupadhyay.smsinfoline.fragment.FragmentLibrary;
import com.anujupadhyay.smsinfoline.fragment.FragmentViewCourse;
import com.anujupadhyay.smsinfoline.fragment.FragmentViewNotice;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private  FirebaseAuth mAuth;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FirebaseUser firebaseUser;
    private DatabaseReference RootRef, UserRefs;
    private String CurrentUserNames, CurrentUserIds, AuthType;
    private TextView textViewNames, textViewEmails;
    private CircleImageView imageViewProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();
        UserRefs = FirebaseDatabase.getInstance().getReference().child("Users");

        CurrentUserIds = mAuth.getCurrentUser().getUid();
        if(firebaseUser.getEmail() !=null){
            AuthType = firebaseUser.getEmail();
        }else {
            AuthType = firebaseUser.getPhoneNumber();
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.mainid);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        textViewNames = view.findViewById(R.id.header_names);
        textViewEmails = view.findViewById(R.id.header_emails);
        imageViewProfile = view.findViewById(R.id.my_profiile_image);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentCollegeProfile()).commit();
            navigationView.setCheckedItem(R.id.clgProfile);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clgProfile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentCollegeProfile()).commit();
                break;
            case R.id.idFloor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentFloorInformation()).commit();
                break;
            case R.id.idAdmission:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentAdmission()).commit();
                break;
            case R.id.idCourse:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentViewCourse()).commit();
                break;
            case R.id.idLibrary:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentLibrary()).commit();
                break;
            case R.id.idAddNotice:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentAddNotice()).commit();
                break;
            case R.id.idViewNotice:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentViewNotice()).commit();
                break;
            case R.id.Feedback:
                startActivity(new Intent(MainActivity.this,FeedbackActivity.class));
                break;
            case R.id.idAboutUs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentAboutUs()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                startActivity(new Intent(this,SettingActivity.class));
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        else{
            VerifyUserExistance();
        }
    }

    private void VerifyUserExistance() {
        String currentUserId = mAuth.getCurrentUser().getUid();
        RootRef.child("Users").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((dataSnapshot.exists()) && (dataSnapshot.hasChild("Name") && (dataSnapshot.hasChild("Images")))){
                    String retriveProfileImage = dataSnapshot.child("Images").getValue().toString();
                    CurrentUserNames = dataSnapshot.child("Name").getValue().toString();
                    Picasso.get().load(retriveProfileImage).into(imageViewProfile);
                    textViewNames.setText("Hi "+CurrentUserNames);
                    textViewEmails.setText(AuthType);
                    Toast.makeText(MainActivity.this, "Welcome "+CurrentUserNames, Toast.LENGTH_SHORT).show();
                } else if((dataSnapshot.exists()) && (dataSnapshot.hasChild("Name"))){
                    CurrentUserNames = dataSnapshot.child("Name").getValue().toString();
                    textViewNames.setText("Hi "+CurrentUserNames);
                    textViewEmails.setText(AuthType);
                    Toast.makeText(MainActivity.this, "Welcome "+CurrentUserNames, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Please Update Your Profile Information", Toast.LENGTH_SHORT).show();
                    SendUserToProfileSettingsActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SendUserToProfileSettingsActivity() {
        Intent intent = new Intent(MainActivity.this, ProfileSettingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
