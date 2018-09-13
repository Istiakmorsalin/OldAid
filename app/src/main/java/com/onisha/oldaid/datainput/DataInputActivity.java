package com.onisha.oldaid.datainput;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.onisha.oldaid.BaseActivity;
import com.onisha.oldaid.R;
import com.onisha.oldaid.SharedPreferenceKey;
import com.onisha.oldaid.auth.LoginActivity;
import com.onisha.oldaid.reminder.model.PrefsModelDB;
import com.onisha.oldaid.speechtotext.SpeechToTextActivity;
import com.pixplicity.easyprefs.library.Prefs;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Tasnim Onisha on 2/26/2018.
 */

public class DataInputActivity extends BaseActivity {

    @Override
    protected int layoutResId() {
        return com.onisha.oldaid.R.layout.activity_datainput;
    }

    @Override
    protected int menuResId() {
        return 0;
    }

    Button next;

    private String height, weight, food, book, tv, news;

    private EditText ht, wt, fd, bk, tvs, ns;

    private Realm myRealm;
    private RealmResults<PrefsModelDB> results1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_datainput);

        myRealm = Realm.getInstance(getBaseContext());

        next = (Button) findViewById(com.onisha.oldaid.R.id.save_data);
        ht = (EditText) findViewById(R.id.height);
        wt = (EditText) findViewById(R.id.weight);
        fd = (EditText) findViewById(R.id.food);
        bk = (EditText) findViewById(R.id.book);
        tvs = (EditText) findViewById(R.id.tv);
        ns = (EditText) findViewById(R.id.news);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRealm.beginTransaction();
                results1 = myRealm.where(PrefsModelDB.class).findAll();
                results1.clear();

                height = ht.getText().toString();
                weight = ht.getText().toString();
                food = ht.getText().toString();
                book = ht.getText().toString();
                tv = ht.getText().toString();
                news = ht.getText().toString();

                PrefsModelDB prefsModelDB = myRealm.createObject(PrefsModelDB.class);
                prefsModelDB.setHeight(Float.valueOf(height));
                prefsModelDB.setWeight(Float.valueOf(weight));
                prefsModelDB.setFoodPreference(String.valueOf(food));
                prefsModelDB.setBooksPreference(String.valueOf(book));
                prefsModelDB.setTvProgramPreference(String.valueOf(tv));
                prefsModelDB.setNewsPreference(String.valueOf(news));

                myRealm.commitTransaction();

                Intent i = new Intent(DataInputActivity.this, SpeechToTextActivity.class);
                Prefs.putString(SharedPreferenceKey.shouldDataPreferenceLaunch, "true");
                startActivity(i);
            }
        });
    }
}
