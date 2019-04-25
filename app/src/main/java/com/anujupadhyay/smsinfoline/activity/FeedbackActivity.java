package com.anujupadhyay.smsinfoline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;

public class FeedbackActivity extends AppCompatActivity {
    private EditText editTextSub, editTextMsg;
    private Button buttonFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        editTextSub = findViewById(R.id.feedback_subject);
        editTextMsg = findViewById(R.id.feedback_msg);
        buttonFeed = findViewById(R.id.send_feedback);
        buttonFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendFeed();
            }
        });
    }

    private void SendFeed() {
        String FeedBackSub = editTextSub.getText().toString().trim();
        String Developer = "anujupadhyay149@gmail.com";
        String[] recp = Developer.split(",");
        String FeedBackMsg = editTextMsg.getText().toString().trim();
        if (TextUtils.isEmpty(FeedBackSub)) {
            Toast.makeText(this, "Enter Subject", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(FeedBackMsg)) {
            Toast.makeText(this, "Enter Message", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, recp);
            intent.putExtra(Intent.EXTRA_SUBJECT, FeedBackSub);
            intent.putExtra(Intent.EXTRA_TEXT, FeedBackMsg);
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Choose an Application"));
        }

    }
}
