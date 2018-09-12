package com.onisha.oldaid.auth;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.onisha.oldaid.BaseActivity;
import com.onisha.oldaid.R;
import com.onisha.oldaid.SharedPreferenceKey;
import com.onisha.oldaid.datainput.DataInputActivity;
import com.onisha.oldaid.reminder.model.RegistrationModelDB;
import com.pixplicity.easyprefs.library.Prefs;

import io.realm.Realm;
import io.realm.RealmResults;


public class LoginActivity extends BaseActivity {

    private Realm myRealm;
    private RealmResults<RegistrationModelDB> results1;

    private EditText loginEmail, loginPhoneNumber;

    @Override
    protected int layoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected int menuResId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_login);o

        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPhoneNumber = (EditText) findViewById(R.id.login_number);
        // read the saved values in database
        myRealm = Realm.getInstance(getBaseContext());
        results1 = myRealm.where(RegistrationModelDB.class).findAll();

        final MediaPlayer mPlayer = MediaPlayer.create(LoginActivity.this, R.raw.login);
        mPlayer.start();


        Button bt = (Button) findViewById(R.id.login_save);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString();
                String phoneNumber = loginPhoneNumber.getText().toString();

                if (email.equals(results1.first().getEmail()) && phoneNumber.equals(results1.first().getMobileNumber())) {

                    Toast.makeText(LoginActivity.this, "Login SuccessFull", Toast.LENGTH_SHORT).show();
                    Prefs.putString(SharedPreferenceKey.shouldLoginLaunch, "true");
                    Intent i = new Intent(LoginActivity.this, DataInputActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Email & Number Did not match", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
