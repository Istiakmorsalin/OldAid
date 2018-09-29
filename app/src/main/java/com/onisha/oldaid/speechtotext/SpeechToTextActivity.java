package com.onisha.oldaid.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.onisha.oldaid.MainActivity;
import com.onisha.oldaid.R;
import com.onisha.oldaid.SharedPreferenceKey;
import com.onisha.oldaid.location.MyLocationUsingLocationAPI;
import com.onisha.oldaid.notifiy.PersistenceFriendDataActivity;
import com.onisha.oldaid.nutrition.BMIActivity;
import com.onisha.oldaid.nutrition.CalculateBMIActivity;
import com.onisha.oldaid.oldhome.OldHomeActivity;
import com.onisha.oldaid.reminder.activity.ReminderMainActivity;
import com.onisha.oldaid.webview.CommonWebViewActivity;
import com.pixplicity.easyprefs.library.Prefs;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by Tasnim Onisha on 4/3/2018.
 */

public class SpeechToTextActivity extends AppCompatActivity {

    private TextView voiceInput;
    private TextView speakButton;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private MediaPlayer mPlayer;

    private Button createAlarm, danger, lcoation, home, reminder, food, news, bmi, inst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.onisha.oldaid.R.layout.speech_main);

        createAlarm  = (Button) findViewById(R.id.createAlarm);
        danger  = (Button) findViewById(R.id.danger);
        lcoation  = (Button) findViewById(R.id.locationBtn);
        home  = (Button) findViewById(R.id.homeButton);
        reminder  = (Button) findViewById(R.id.reminder);
        food  = (Button) findViewById(R.id.food);
        news  = (Button) findViewById(R.id.news);
        bmi  = (Button) findViewById(R.id.bmi);
        inst  = (Button) findViewById(R.id.instructions);

        final String st = "Say 'Create Alarm' for setting an alarm,Say 'Danger' for help,Say 'Location' for help, Say 'Home' for old home, Say 'reminder' for reminder ,Say 'food' for food suggestion";

        mPlayer = MediaPlayer.create(SpeechToTextActivity.this, R.raw.intro);
        mPlayer.start();

        createAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SpeechToTextActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        danger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SpeechToTextActivity.this, PersistenceFriendDataActivity.class);
                startActivity(i);
            }
        });

        lcoation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SpeechToTextActivity.this, MyLocationUsingLocationAPI.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SpeechToTextActivity.this, OldHomeActivity.class);
                startActivity(i);
            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SpeechToTextActivity.this, ReminderMainActivity.class);
                startActivity(i);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SpeechToTextActivity.this, CalculateBMIActivity.class);
                startActivity(i);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SpeechToTextActivity.this, CommonWebViewActivity.class);
                String newsLink = Prefs.getString(SharedPreferenceKey.newsLink, "");

                if (!newsLink.equals("")) {
                    i.putExtra("URL", newsLink);
                    Prefs.putString(SharedPreferenceKey.newsLink, "");
                    startActivity(i);
                } else {
                    Toast.makeText(SpeechToTextActivity.this, "No news Good news", Toast.LENGTH_SHORT).show();
                }


            }
        });

        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SpeechToTextActivity.this, BMIActivity.class);
                startActivity(i);
            }
        });

        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LovelyInfoDialog(SpeechToTextActivity.this)
                        .setTopColorRes(R.color.colorAccent)
                        .setIcon(R.drawable.icon_test)
                        //This will add Don't show again checkbox to the dialog. You can pass any ID as argument
                        .setNotShowAgainOptionEnabled(0)
                        .setNotShowAgainOptionChecked(true)
                        .setTitle("Please Read below")
                        .setMessage(st)
                        .show();
            }
        });

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
    protected void onStop() {
        super.onStop();
        mPlayer.stop();
    }

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
                    }   else if (v.equals("food")) {
                        Intent i = new Intent(SpeechToTextActivity.this, CalculateBMIActivity.class);
                        startActivity(i);
                    }

                }
                break;
            }

        }
    }
}
