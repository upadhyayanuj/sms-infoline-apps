package com.anujupadhyay.smsinfoline.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        editText = findViewById(R.id.forget_Email);
        button = findViewById(R.id.forget_button);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPassword();
            }
        });
    }

    private void ResetPassword() {
        String email = editText.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter Registered Email", Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.setTitle("Sending Email to Reset Password");
            progressDialog.setMessage("Please Wait...We are sending email to your registered email id");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(PasswordActivity.this, "Password Reset Link has been sent to your Email Id", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(PasswordActivity.this, "No Account found with this email id", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
