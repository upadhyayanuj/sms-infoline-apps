package com.anujupadhyay.smsinfoline.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class LoginActivity extends AppCompatActivity {
    private Button ButtonLogin, PhoneButtonLogin;
    private EditText EditTextEmail, EditTextPassword;
    private TextView ForgetPassword, NewUser;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        InitializationOfFields();
        NewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterUser();
            }
        });
        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passwordIntent = new Intent(LoginActivity.this, PasswordActivity.class);
                passwordIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(passwordIntent);
            }
        });
        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogin();
            }
        });
        PhoneButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,PhoneAuthantication.class);
                startActivity(intent);
            }
        });
    }

    private void UserLogin() {
        String email = EditTextEmail.getText().toString();
        String password = EditTextPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Enter Your Email Id", Toast.LENGTH_SHORT).show();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            EditTextEmail.setError("Please Enter Valid Email ID!");
            EditTextEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }
        else{

            progressDialog.setTitle("Authenticating User...");
            progressDialog.setMessage("Please Wait....");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                            goToMainActivity();
                            Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else{
                            Toast.makeText(LoginActivity.this, "Verify your email address before login ", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                    else{
                        if (!(task.getException() instanceof FirebaseAuthUserCollisionException)){
                            Toast.makeText(getApplicationContext(), "Access Denied ! Enter Valid Credantials", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else{
                            String msg = task.getException().toString();
                            Toast.makeText(LoginActivity.this, "Error " + msg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void InitializationOfFields() {
        ButtonLogin = (Button) findViewById(R.id.login_button);
        PhoneButtonLogin = (Button) findViewById(R.id.login_phone);
        EditTextEmail = (EditText) findViewById(R.id.login_Email);
        EditTextPassword = (EditText) findViewById(R.id.login_password);
        ForgetPassword = (TextView) findViewById(R.id.forget_password);
        NewUser = (TextView) findViewById(R.id.register_link);
        progressDialog = new ProgressDialog(this);
    }


    private void goToMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }
    private void goToRegisterUser() {
        Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        regIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(regIntent);
    }
}
