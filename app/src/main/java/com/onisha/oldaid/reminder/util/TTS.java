package com.onisha.oldaid.reminder.util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TTS extends Service implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {
    private TextToSpeech mTts;
    private String spokenText;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getExtras() != null){
            String spokenT = intent.getStringExtra("ReminderMsg");
            spokenText = spokenT;
            mTts.speak(spokenText, TextToSpeech.QUEUE_FLUSH, null);
        }

        return startId;
    }

    @Override
    public void onCreate() {
        mTts = new TextToSpeech(this, this);
        // This is a good place to set spokenText
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.US);
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {

                Thread thread = new Thread(){
                    public void run(){
                        System.out.println("Thread Running");
                        mTts.speak(spokenText, TextToSpeech.QUEUE_FLUSH, null);
                    }
                };

                thread.start();

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    @Override
    public void onUtteranceCompleted(String uttId) {
        stopSelf();
    }

    @Override
    public void onDestroy() {
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
