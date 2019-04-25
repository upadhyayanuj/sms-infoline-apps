package com.anujupadhyay.smsinfoline.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNoticeType extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice_type);

        editText = findViewById(R.id.notice_type_name);
        button = findViewById(R.id.btn_notice_type);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TypeName = editText.getText().toString();
                if(TextUtils.isEmpty(TypeName)){
                    Toast.makeText(AddNoticeType.this, "Please Enter Notice Type", Toast.LENGTH_SHORT).show();
                }else{
                CreateNoticeType(TypeName);
                }
            }
        });
    }

    private void CreateNoticeType(final String Typename) {
        databaseReference.child("Notification").child(Typename).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    editText.setText("");
                    Toast.makeText(AddNoticeType.this, "Notice Type Added Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    String msg = task.getException().toString();
                    Toast.makeText(AddNoticeType.this, "Error : "+msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
