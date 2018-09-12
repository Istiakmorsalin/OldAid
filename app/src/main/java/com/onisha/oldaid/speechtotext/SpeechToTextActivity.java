package com.onisha.oldaid.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.onisha.oldaid.MainActivity;
import com.onisha.oldaid.R;
import com.onisha.oldaid.location.MyLocationUsingLocationAPI;
import com.onisha.oldaid.notifiy.PersistenceFriendDataActivity;
import com.onisha.oldaid.oldhome.OldHomeActivity;
import com.onisha.oldaid.reminder.activity.ReminderMainActivity;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by Tasnim Onisha on 4/3/2018.
 */

public class SpeechToTextActivity extends AppCompatActivity {

    private TextView voiceInput;
    private TextView speakButton;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.onisha.oldaid.R.layout.speech_main);

//        final MediaPlayer mPlayer = MediaPlayer.create(SpeechToTextActivity.this, R.raw.CreateAlarm);
//        mPlayer.start();
//
//        final MediaPlayer mPlayer = MediaPlayer.create(SpeechToTextActivity.this, R.raw.Location);
//        mPlayer.start();
//
//        final MediaPlayer mPlayer = MediaPlayer.create(SpeechToTextActivity.this, R.raw.home);
//        mPlayer.start();
//
//        final MediaPlayer mPlayer = MediaPlayer.create(SpeechToTextActivity.this, R.raw.Reminder);
//        mPlayer.start();

        voiceInput = (TextView) findViewById(com.onisha.oldaid.R.id.voiceInput);
        speakButton = (TextView) findViewById(com.onisha.oldaid.R.id.btnSpeak);

        speakButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                askSpeechInput();
            }
        });

    }

    // Showing google speech input dialog

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Ok Google");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    // Receiving speech input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voiceInput.setText(result.get(0));
                    String v = result.get(0);

                    if (v.equals("create alarm")) {
                        Intent i = new Intent(SpeechToTextActivity.this, MainActivity.class);
                        startActivity(i);
                    } else if (v.equals("danger")) {
                        Intent i = new Intent(SpeechToTextActivity.this, PersistenceFriendDataActivity.class);
                        startActivity(i);
                    } else if (v.equals("location")) {
                        Intent i = new Intent(SpeechToTextActivity.this, MyLocationUsingLocationAPI.class);
                        startActivity(i);
                    }  else if (v.equals("home")) {
                        Intent i = new Intent(SpeechToTextActivity.this, OldHomeActivity.class);
                        startActivity(i);
                    }  else if (v.equals("reminder")) {
                        Intent i = new Intent(SpeechToTextActivity.this, ReminderMainActivity.class);
                        startActivity(i);
                    }

                }
                break;
            }

        }
    }
}
