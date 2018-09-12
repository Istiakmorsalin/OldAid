package com.onisha.oldaid.location;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.onisha.oldaid.R;
import com.onisha.oldaid.location.LocationUtil.LocationHelper;
import com.onisha.oldaid.notifiy.PersistenceFriendDataActivity;


import butterknife.Bind;
import butterknife.ButterKnife;

import static com.onisha.oldaid.notifiy.PersistenceFriendDataActivity.MyPREFERENCES;

public class MyLocationUsingHelper extends AppCompatActivity implements ConnectionCallbacks,
        OnConnectionFailedListener,OnRequestPermissionsResultCallback {


    @Bind(R.id.btnLocation)Button btnProceed;
    @Bind(R.id.tvAddress)TextView tvAddress;
    @Bind(R.id.tvEmpty)TextView tvEmpty;
    @Bind(R.id.rlPickLocation)RelativeLayout rlPick;

    private Location mLastLocation;

    double latitude;
    double longitude;

    LocationHelper locationHelper;
    String currentLocationText;
    String latitudeText, longitudeText;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static String Name = "nameKey";
    public static final String Phone = "phoneKey";

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_service);


        locationHelper=new LocationHelper(this);
        locationHelper.checkpermission();

        ButterKnife.bind(this);

        rlPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mLastLocation=locationHelper.getLocation();

                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    getAddress();

                } else {

                    if(btnProceed.isEnabled())
                        btnProceed.setEnabled(false);

                    showToast("Couldn't get the location. Make sure location is enabled on the device");
                }
            }
        });



        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Proceed to the next step");
                sharedpreferences = getApplication().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                String phoneNumber = sharedpreferences.getString(Phone, "");
                if (!phoneNumber.equals("")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                    intent.putExtra("sms_body", currentLocationText);
                    startActivity(intent);
                }

            }
        });

        // check availability of play services
        if (locationHelper.checkPlayServices()) {

            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient();
        }

    }


    public void getAddress()
    {
        Address locationAddress;

        locationAddress=locationHelper.getAddress(latitude,longitude);

        latitudeText = "Latitude:"+ String.valueOf(latitude) + "\n";
        longitudeText = "Longitude:" + "\n"+ String.valueOf(longitude);

        if(locationAddress!=null)
        {

            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();


            String currentLocation;

            if(!TextUtils.isEmpty(address))
            {
                currentLocation=address;

                currentLocation+= latitudeText;
                currentLocation+= longitudeText;

                if (!TextUtils.isEmpty(address1))
                    currentLocation+="\n"+address1;

                if (!TextUtils.isEmpty(city))
                {
                    currentLocation+="\n"+city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+=" - "+postalCode;
                }
                else
                {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+="\n"+postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation+="\n"+state;

                if (!TextUtils.isEmpty(country))
                    currentLocation+="\n"+country;

                tvEmpty.setVisibility(View.GONE);
                tvAddress.setText(currentLocation);
                currentLocationText = currentLocation;
                tvAddress.setVisibility(View.VISIBLE);

                if(!btnProceed.isEnabled())
                    btnProceed.setEnabled(true);
            }

        }
        else
            showToast("Something went wrong");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        locationHelper.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    protected void onResume() {
        super.onResume();
        locationHelper.checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        mLastLocation=locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        locationHelper.connectApiClient();
    }


    // Permission check functions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        locationHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);

    }

    public void showToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }



}
