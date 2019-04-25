package com.anujupadhyay.smsinfoline.activity;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;

import java.util.Locale;

public class UpdateNoticeActivity extends AppCompatActivity {
    TextToSpeech textToSpeech;
    EditText editText;
    Button button, buttonStopText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notice);
        editText = findViewById(R.id.text_description);
        button = findViewById(R.id.speaks);
        buttonStopText = findViewById(R.id.buttonStop);
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    textToSpeech.setPitch(1.3f);
                    textToSpeech.setSpeechRate(0.6f);
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texts = editText.getText().toString();
                if(TextUtils.isEmpty(texts)){
                    Toast.makeText(UpdateNoticeActivity.this, "Enter Some Texts", Toast.LENGTH_SHORT).show();
                } else{
                    textToSpeech.speak(texts, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        buttonStopText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.stop();
            }
        });
    }
}
