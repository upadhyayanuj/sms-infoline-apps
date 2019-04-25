package com.anujupadhyay.smsinfoline.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class PhoneAuthantication extends AppCompatActivity {
    private Spinner spinner;
    private EditText PhoneNumbers, VerificationCodes;
    private Button PhoneNumbersButton, VerifyAccountButton;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_authantication);
        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        mAuth = FirebaseAuth.getInstance();
        InitializationOfFields();
        progressDialog = new ProgressDialog(this);
        PhoneNumbersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String phoneNumber = PhoneNumbers.getText().toString();
                String verificationCode = VerificationCodes.getText().toString();

                if(TextUtils.isEmpty(phoneNumber)){
                    Toast.makeText(PhoneAuthantication.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                }
                if(phoneNumber.length()<9){
                    Toast.makeText(PhoneAuthantication.this, "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.setTitle("Registering User...");
                    progressDialog.setMessage("Please Wait....");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+" + code + phoneNumber,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            PhoneAuthantication.this,               // Activity (for callback binding)
                            callbacks);        // OnVerificationStateChangedCallbacks
                }
            }
        });

        VerifyAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneNumbers.setVisibility(View.INVISIBLE);
                PhoneNumbersButton.setVisibility(View.INVISIBLE);

                String verificationCodes = VerificationCodes.getText().toString();

                if(TextUtils.isEmpty(verificationCodes)){
                    Toast.makeText(PhoneAuthantication.this, "Please Enter Verification Code....", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.setTitle("Verification Code");
                    progressDialog.setMessage("Please Wait...Verifying Code...");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCodes );
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progressDialog.dismiss();
                Toast.makeText(PhoneAuthantication.this, "It's look like you are using another country's mobile numbers", Toast.LENGTH_SHORT).show();
                PhoneNumbers.setVisibility(View.VISIBLE);
                PhoneNumbersButton.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);

                VerificationCodes.setVisibility(View.INVISIBLE);
                VerifyAccountButton.setVisibility(View.INVISIBLE);
            }

            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {

                progressDialog.dismiss();
                mVerificationId = verificationId;
                mResendToken = token;
                Toast.makeText(PhoneAuthantication.this, "Code Send to Your Mobile Number", Toast.LENGTH_SHORT).show();
                spinner.setVisibility(View.INVISIBLE);
                PhoneNumbers.setVisibility(View.INVISIBLE);
                PhoneNumbersButton.setVisibility(View.INVISIBLE);

                VerificationCodes.setVisibility(View.VISIBLE);
                VerifyAccountButton.setVisibility(View.VISIBLE);
            }

        };
    }


    private void InitializationOfFields() {
        PhoneNumbers = (EditText) findViewById(R.id.phone_number_verify);
        VerificationCodes = (EditText) findViewById(R.id.phone_verification_code);
        PhoneNumbersButton = (Button) findViewById(R.id.send_verification_code);
        VerifyAccountButton = (Button) findViewById(R.id.verify_account);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(PhoneAuthantication.this, "Phone Verification Successfully", Toast.LENGTH_SHORT).show();
                    goToMainActivity();
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"User is Already Registered!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String msg = task.getException().toString();
                        Toast.makeText(PhoneAuthantication.this, "Error " + msg, Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    private void goToMainActivity() {
        Intent mainsIntent = new Intent(PhoneAuthantication.this, MainActivity.class);
        mainsIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainsIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }
}
