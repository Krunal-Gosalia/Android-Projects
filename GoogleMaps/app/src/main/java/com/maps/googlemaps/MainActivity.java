package com.maps.googlemaps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    //GoogleMap mMap;
    LocationManager mLocationManager;
    LocationListener mLocationListener;


    @Override
    protected void onResume() {
        super.onResume();

        if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS not enabled")
                .setMessage("Would you like to enable the GPS settings?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                        @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(in);

                            }
                        }

                )
                .setNegativeButton("No", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
            AlertDialog alert = builder.create();

            alert.show();



        }
        else {
            mLocationListener = new LocationListener() {


                @Override
                public void onLocationChanged(Location location) {
                    Log.d("demo", location.getLongitude() + " " + location.getLatitude());
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, mLocationListener);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }
}
