package com.anujupadhyay.smsinfoline.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.anujupadhyay.smsinfoline.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.util.HashMap;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class ProfileSettingActivity extends AppCompatActivity {

    private EditText ProfileName, ProfileAbout, ProfileClassName, ProfileDivName, ProfileRollNum;
    private Button ProfileSave;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference RootRef;
    private StorageReference storageReference;
    private String CurrentUserId;
    private ImageView ProfileImages;
    private static final int GallaryPick = 1;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUserId = firebaseAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Images");
        InitializeMethod();
        ProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfiles();
            }
        });
        RetriveUserData();

        ProfileImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GallaryPick);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GallaryPick && resultCode == RESULT_OK && data != null ){
            Uri ImageUri = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){
                progressDialog.setTitle("Setting Profile Image");
                progressDialog.setMessage("Please Wait...Your Profile Image is Uploading");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                Uri resultUri = result.getUri();
                final StorageReference filepath = storageReference.child(CurrentUserId+".jpg");
                StorageTask<UploadTask.TaskSnapshot> uploadTask = filepath.putFile(resultUri);
                Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            //Toast.makeText(ProfileSettingActivity.this, "Profile Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            Uri downUri = task.getResult();
                            final String DownloadUrl = downUri.toString();

                            RootRef.child("Users").child(CurrentUserId).child("Images").setValue(DownloadUrl)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(ProfileSettingActivity.this, "Profile Saved In Database", Toast.LENGTH_SHORT).show();
                                            } else{
                                                progressDialog.dismiss();
                                                String er = task.getException().toString();
                                                Toast.makeText(ProfileSettingActivity.this, "Error : "+er, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            progressDialog.dismiss();
                            String msgs = task.getException().toString();
                            Toast.makeText(ProfileSettingActivity.this, "Error : "+msgs, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    }

    private void InitializeMethod() {
        ProfileName = findViewById(R.id.profile_name);
        ProfileImages = findViewById(R.id.profile_image);
        ProfileAbout = findViewById(R.id.profile_about);
        ProfileClassName = findViewById(R.id.profile_class);
        ProfileDivName = findViewById(R.id.profile_div);
        ProfileRollNum = findViewById(R.id.profile_rollNum);
        ProfileSave = findViewById(R.id.profile_update);
        progressDialog = new ProgressDialog(this);
    }

    private void UpdateProfiles() {
        String setUserName = ProfileName.getText().toString();
        String setUserAbout = ProfileAbout.getText().toString();
        String setUserClass = ProfileClassName.getText().toString();
        String setUserDiv = ProfileDivName.getText().toString();
        String setUserRoll = ProfileRollNum.getText().toString();
        if(TextUtils.isEmpty(setUserName)){
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(setUserAbout)){
            Toast.makeText(this, "Please Enter Some Information about yourself", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(setUserClass)){
            Toast.makeText(this, "Please Enter Class", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(setUserDiv)){
            Toast.makeText(this, "Please Enter Div", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(setUserRoll)){
            Toast.makeText(this, "Please Enter Roll Number", Toast.LENGTH_SHORT).show();
        } else{
            HashMap<String, String> profileMap = new HashMap<>();
            profileMap.put("uid",CurrentUserId);
            profileMap.put("Name",setUserName);
            profileMap.put("About",setUserAbout);
            profileMap.put("Class",setUserClass);
            profileMap.put("Division",setUserDiv);
            profileMap.put("Roll_Number",setUserRoll);

            RootRef.child("Users").child(CurrentUserId).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ProfileSettingActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                        goToMainActivity();
                    }
                    else{
                        String msg = task.getException().toString();
                        Toast.makeText(ProfileSettingActivity.this, "Something Went Wrong..."+msg, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void goToMainActivity() {
        Intent mainIntent = new Intent(ProfileSettingActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }

    private void RetriveUserData() {
        RootRef.child("Users").child(CurrentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((dataSnapshot.exists()) && (dataSnapshot.hasChild("Name") && (dataSnapshot.hasChild("Images")))){
                    String retriveProfileImage = dataSnapshot.child("Images").getValue().toString();
                    String retriveProfileName = dataSnapshot.child("Name").getValue().toString();
                    String retriveProfileAbout = dataSnapshot.child("About").getValue().toString();
                    String retriveProfileClass = dataSnapshot.child("Class").getValue().toString();
                    String retriveProfileDiv = dataSnapshot.child("Division").getValue().toString();
                    String retriveProfileRollNum = dataSnapshot.child("Roll_Number").getValue().toString();

                    Picasso.get().load(retriveProfileImage).into(ProfileImages);
                    ProfileName.setText(retriveProfileName);
                    ProfileAbout.setText(retriveProfileAbout);
                    ProfileClassName.setText(retriveProfileClass);
                    ProfileDivName.setText(retriveProfileDiv);
                    ProfileRollNum.setText(retriveProfileRollNum);
                }
                else if((dataSnapshot.exists()) && (dataSnapshot.hasChild("Name"))){
                    String retriveProfileName = dataSnapshot.child("Name").getValue().toString();
                    String retriveProfileAbout = dataSnapshot.child("About").getValue().toString();
                    String retriveProfileClass = dataSnapshot.child("Class").getValue().toString();
                    String retriveProfileDiv = dataSnapshot.child("Division").getValue().toString();
                    String retriveProfileRollNum = dataSnapshot.child("Roll_Number").getValue().toString();

                    ProfileName.setText(retriveProfileName);
                    ProfileAbout.setText(retriveProfileAbout);
                    ProfileClassName.setText(retriveProfileClass);
                    ProfileDivName.setText(retriveProfileDiv);
                    ProfileRollNum.setText(retriveProfileRollNum);
                }
                else{
                    Toast.makeText(ProfileSettingActivity.this, "Pleas Update Your Profile....", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
