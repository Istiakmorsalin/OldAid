package com.onisha.oldaid.nutrition;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.onisha.oldaid.BaseActivity;
import com.onisha.oldaid.R;
import com.onisha.oldaid.reminder.model.PrefsModelDB;
import com.onisha.oldaid.webview.CommonWebViewActivity;

import io.realm.Realm;
import io.realm.RealmResults;

public class BMIActivity extends BaseActivity {


    private EditText weight, height;
    private Button calculateBMI;
    private TextView bmiValue;
    double bmi;


    @Override
    protected int layoutResId() {
        return R.layout.activity_bmi1;
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
        height = (EditText) findViewById(R.id.height);
        calculateBMI = (Button) findViewById(R.id.calculate_bmi);

        bmiValue = (TextView) findViewById(R.id.bmi_value);
        // read the saved values in database




        calculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float current_weight = Float.valueOf(weight.getText().toString());

                Float current_height = Float.valueOf(height.getText().toString());

                bmi =  ((current_weight * 703)/(current_height * current_height));

                if( bmi < 18.5) {
                    bmiValue.setText("Your BMI is: " + String.valueOf(bmi) + "\n" + "Health Condition:" + "underweight");
                } else if (bmi < 24.9 && bmi> 18.5) {
                    bmiValue.setText("Your BMI is: " + String.valueOf(bmi) + "\n" + "Health Condition:" + "normal weight");
                } else if (bmi < 29.9 && bmi >25) {
                    bmiValue.setText("Your BMI is: " + String.valueOf(bmi) + "\n" + "Health Condition:" + "overweight");
                } else if(bmi <34.9 && bmi > 30) {
                    bmiValue.setText("Your BMI is: " + String.valueOf(bmi) + "\n" + "Health Condition:" + "class I obese");
                } else if(bmi <39.9 && bmi>35) {
                    bmiValue.setText("Your BMI is: " + String.valueOf(bmi) + "\n" + "Health Condition:" + "class II obese");
                } else if (bmi>40) {
                    bmiValue.setText("Your BMI is: " + String.valueOf(bmi) + "\n" + "Health Condition:" + "class III obese");
                }
            }
        });
    }
}
