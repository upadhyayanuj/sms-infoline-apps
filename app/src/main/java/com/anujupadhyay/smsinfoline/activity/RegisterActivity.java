package com.anujupadhyay.smsinfoline.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText inputName;
    EditText inputEmail;
    EditText inputPassword;
    Button clickLogin;
    Button clickRegister;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    private DatabaseReference Rootref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputName = (EditText) findViewById(R.id.editTextName);
        inputEmail = (EditText) findViewById(R.id.editTextEmail);
        inputPassword = (EditText) findViewById(R.id.editTextPassword);
        clickLogin = (Button) findViewById(R.id.backLogin);
        clickRegister = (Button) findViewById(R.id.buttonRegister);
        mAuth = FirebaseAuth.getInstance();
        Rootref = FirebaseDatabase.getInstance().getReference();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    public void buttonRegisters(View view) {
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if (name.isEmpty()) {
            inputName.setError("Please Enter Name!");
            inputName.requestFocus();
            return;
        }
        if (name.length() < 6) {
            inputName.setError("Enter Full Name");
            inputName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            inputEmail.setError("Please Enter Email!");
            inputEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Please Enter Valid Email ID!");
            inputEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            inputPassword.setError("Please Enter Password!");
            inputPassword.requestFocus();
            return;
        }
        if (password.length() < 8) {
            inputPassword.setError("Minimum Length of Password should be 8");
            inputPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                finish();
                                progressBar.setVisibility(View.GONE);
                                //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                Toast.makeText(getApplicationContext(), "User Registration Successfully, Please Verify your email address", Toast.LENGTH_SHORT).show();
                                String currentUserid = mAuth.getCurrentUser().getUid();
                                Rootref.child("Users").child(currentUserid).setValue("");
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT);
                            }
                        }
                    });
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "User is Already Registered!", Toast.LENGTH_SHORT).show();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT);
                    }
                }
            }
        });
    }
    public void buttonLogins(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
