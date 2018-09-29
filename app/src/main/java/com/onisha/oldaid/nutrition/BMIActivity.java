package com.onisha.oldaid.nutrition;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onisha.oldaid.BaseActivity;
import com.onisha.oldaid.R;
import com.onisha.oldaid.SharedPreferenceKey;
import com.onisha.oldaid.auth.LoginActivity;
import com.onisha.oldaid.datainput.DataInputActivity;
import com.onisha.oldaid.reminder.model.PrefsModelDB;
import com.onisha.oldaid.reminder.model.RegistrationModelDB;
import com.onisha.oldaid.webview.CommonWebViewActivity;
import com.pixplicity.easyprefs.library.Prefs;

import io.realm.Realm;
import io.realm.RealmResults;

public class CalculateBMIActivity extends BaseActivity {

    private Realm myRealm;
    private RealmResults<PrefsModelDB> results1;

    private EditText weight;
    private Button calculateBMI, navigateToFood;
    private TextView bmiValue;
    double bmi;


    @Override
    protected int layoutResId() {
        return R.layout.activity_bmi;
    }

    @Override
    protected int menuResId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_login);

        weight = (EditText) findViewById(R.id.current_weight);
        calculateBMI = (Button) findViewById(R.id.calculate_bmi);
        navigateToFood = (Button) findViewById(R.id.food_suggestion);
        bmiValue = (TextView) findViewById(R.id.bmi_value);
        // read the saved values in database
        myRealm = Realm.getInstance(getBaseContext());
        results1 = myRealm.where(PrefsModelDB.class).findAll();

        final MediaPlayer mPlayer = MediaPlayer.create(CalculateBMIActivity.this, R.raw.foods);
        mPlayer.start();

        navigateToFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CalculateBMIActivity.this, CommonWebViewActivity.class);
                if( bmi < 18.5) {
                    i.putExtra("URL", "https://www.stylecraze.com/articles/4-simple-diet-tips-and-a-diet-chart-to-gain-weight");
                    startActivity(i);
                } else if (bmi < 24.9 && bmi> 18.5) {
                    i.putExtra("URL", "https://truweight.in/blog/food-and-nutrition/balanced-diet-chart.html");
                    startActivity(i);
                } else if (bmi < 29.9 && bmi >25) {
                    i.putExtra("URL", "https://www.pritikin.com/your-health/healthy-living/eating-right/1720-healthy-meal-plan-for-weight-loss.html");
                    startActivity(i);
                } else if(bmi <34.9 && bmi > 30) {
                    i.putExtra("URL", "https://www.pritikin.com/your-health/healthy-living/eating-right/1720-healthy-meal-plan-for-weight-loss.html");
                    startActivity(i);
                } else if(bmi <39.9 && bmi>35) {
                    i.putExtra("URL", "https://www.pritikin.com/your-health/healthy-living/eating-right/1720-healthy-meal-plan-for-weight-loss.html");
                    startActivity(i);
                } else if (bmi>40) {
                    i.putExtra("URL", "https://www.pritikin.com/your-health/healthy-living/eating-right/1720-healthy-meal-plan-for-weight-loss.html");
                    startActivity(i);
                }
            }
        });


        calculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float current_weight = Float.valueOf(weight.getText().toString());

                Float current_height = results1.last().getHeight()/100;

                bmi =  current_weight / (Math.pow(current_height,2));

                if( bmi < 18.5) {
                    bmiValue.setText("underweight");
                } else if (bmi < 24.9 && bmi> 18.5) {
                    bmiValue.setText("normal weight");
                } else if (bmi < 29.9 && bmi >25) {
                    bmiValue.setText("overweight");
                } else if(bmi <34.9 && bmi > 30) {
                    bmiValue.setText("class I obese");
                } else if(bmi <39.9 && bmi>35) {
                    bmiValue.setText("class II obese");
                } else if (bmi>40) {
                    bmiValue.setText("class III obese");
                }
            }
        });
    }
}
