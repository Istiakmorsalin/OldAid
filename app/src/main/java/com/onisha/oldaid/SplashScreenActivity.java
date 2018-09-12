package com.onisha.oldaid;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.onisha.oldaid.auth.LoginActivity;
import com.onisha.oldaid.auth.RegistrationActivity;
import com.pixplicity.easyprefs.library.Prefs;


/**
 * Created by Tasnim Onisha on 2/26/2018.
 */

public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final MediaPlayer mPlayer = MediaPlayer.create(SplashScreenActivity.this, R.raw.welcome);
        mPlayer.start();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

               // mPlayer.stop();

                String reg = Prefs.getString(SharedPreferenceKey.shouldRegistrationLaunch, "false");

                if (reg.equals("false")) {
                    Intent i = new Intent(SplashScreenActivity.this, RegistrationActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                }



                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}