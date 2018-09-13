package com.onisha.oldaid.nutrition;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.onisha.oldaid.BaseActivity;
import com.onisha.oldaid.R;
import com.onisha.oldaid.reminder.model.PrefsModelDB;

import io.realm.Realm;
import io.realm.RealmResults;

public class SampleFoodSuggestionActivity extends BaseActivity {

    @Override
    protected int layoutResId() {
        return R.layout.activity_food;
    }

    @Override
    protected int menuResId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_login)

    }
}

