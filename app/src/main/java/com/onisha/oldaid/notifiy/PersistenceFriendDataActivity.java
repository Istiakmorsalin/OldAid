package com.onisha.oldaid.notifiy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.onisha.oldaid.R;

import static android.R.attr.value;

public class PersistenceFriendDataActivity extends AppCompatActivity {
    EditText ed1,ed2;
    Button b1,b2;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static String Name = "nameKey";
    public static final String Phone = "phoneKey";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.onisha.oldaid.R.layout.activity_persistence);

//        final MediaPlayer mPlayer = MediaPlayer.create(PersistenceFriendDataActivity.this, R.raw.danger);
//        mPlayer.start();


        ed1=(EditText)findViewById(com.onisha.oldaid.R.id.editText);
        ed2=(EditText)findViewById(com.onisha.oldaid.R.id.editText2);

        b1=(Button)findViewById(com.onisha.oldaid.R.id.button);
        sharedpreferences = getApplication().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String phoneNumber = sharedpreferences.getString(Phone, "");
        if (!phoneNumber.equals("")) {
            gotoNotifyActivity();
        }

        b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String n = ed1.getText().toString();
                        String ph = ed2.getText().toString();

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Name, n);
                        editor.putString(Phone, ph);
                        editor.commit();

                        Toast.makeText(PersistenceFriendDataActivity.this, "Thanks", Toast.LENGTH_LONG).show();
                        gotoNotifyActivity();
                    }
                });



            }

            public void gotoNotifyActivity () {
                Intent myIntent = new Intent(PersistenceFriendDataActivity.this, NotifyFriendActivity.class);
                myIntent.putExtra("key", value); //Optional parameters
                PersistenceFriendDataActivity.this.startActivity(myIntent);
            }


          }