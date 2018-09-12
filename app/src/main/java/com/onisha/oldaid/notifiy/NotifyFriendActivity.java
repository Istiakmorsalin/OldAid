
package com.onisha.oldaid.notifiy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.onisha.oldaid.R;
import com.onisha.oldaid.auth.RegistrationActivity;

import static com.onisha.oldaid.notifiy.PersistenceFriendDataActivity.Phone;


public class NotifyFriendActivity extends AppCompatActivity {
            Button notify;
            public static final String MyPREFERENCES = "MyPrefs" ;
            SharedPreferences sharedpreferences;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(com.onisha.oldaid.R.layout.notify_friend_activity);
                sharedpreferences = getApplication().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                String phoneNumber = sharedpreferences.getString(PersistenceFriendDataActivity.Phone, "");
                if (phoneNumber != null) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phoneNumber, null));
                    startActivity(intent);
                }

//                final MediaPlayer mPlayer = MediaPlayer.create(NotifyFriendActivity.this, R.raw.notify);
//                mPlayer.start();

                notify=(Button)findViewById(com.onisha.oldaid.R.id.button3);



                notify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        }


