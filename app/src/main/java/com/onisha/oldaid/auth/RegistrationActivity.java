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


public class RegistrationActivity extends BaseActivity {

    private String name, email, phoneNumber;

    private EditText ename, eemail, ephoneNumber;

    private Realm myRealm;
    private RealmResults<RegistrationModelDB> results1;
    MediaPlayer mPlayer;

    @Override
    protected int layoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected int menuResId() {
        return 0;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlayer.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_register);

        Button bt = (Button) findViewById(R.id.save);

        mPlayer = MediaPlayer.create(RegistrationActivity.this, R.raw.registration);
        mPlayer.start();

        ename = (EditText) findViewById(R.id.name);
        eemail = (EditText) findViewById(R.id.email);
        ephoneNumber = (EditText) findViewById(R.id.phn_number);

        myRealm = Realm.getInstance(getBaseContext());


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = ename.getText().toString();
                email = eemail.getText().toString();
                phoneNumber = ephoneNumber.getText().toString();

                if (name != null &&
                        !name.isEmpty() &&
                        email !=null &&
                        !email.isEmpty() &&
                        phoneNumber!=null &&
                        !phoneNumber.isEmpty()) {

                    myRealm.beginTransaction();
                    results1 = myRealm.where(RegistrationModelDB.class).findAll();
                    results1.clear();

                    // Create an object
                    RegistrationModelDB registrationModelDB = myRealm.createObject(RegistrationModelDB.class);
                    registrationModelDB.setEmail(email);
                    registrationModelDB.setMobileNumber(phoneNumber);
                    registrationModelDB.setName(name);

                    myRealm.commitTransaction();
                    mPlayer.stop();
                    Toast.makeText(RegistrationActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                    Prefs.putString(SharedPreferenceKey.shouldRegistrationLaunch, "true");
                    startActivity(i);
                } else {
                    Toast.makeText(RegistrationActivity.this, "Input all data", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
