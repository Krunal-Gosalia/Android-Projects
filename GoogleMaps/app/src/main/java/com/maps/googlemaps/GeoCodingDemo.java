package com.maps.googlemaps;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.internal.zzon;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class GeoCodingDemo extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double lat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_coding_demo);

        if(Geocoder.isPresent())
        {
            new GeoTask(this).execute("Charlotte");

            //new ReverseGeocodingTask(35.2270869, -80.8431267).execute();
        }
        else
        {
            Toast.makeText(this,"No GeoCoding", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        Log.d("demo", "Hello - "+lat);

        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng charlotte = new LatLng(35.308060, -80.723351);

        Marker utDriveMarker = mMap.addMarker(new MarkerOptions()
                .position(charlotte).title("Marker in UT Drive").snippet("Population : 100")/*.icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow)*/
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));



    }

    class GeoTask extends AsyncTask<String, Void, List<Address>>{

        Context mContext;
        ProgressDialog dialog;

        public GeoTask(Context context)
        {
            this.mContext = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(mContext);
            dialog.setMessage("GeoTask Charlotte");
            dialog.show();
        }

        @Override
        protected List<Address> doInBackground(String... params) {
            List<Address> addressList = null;

            Geocoder geocoder = new Geocoder(mContext);
            try {
                addressList = geocoder.getFromLocationName(params[0], 10);

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return addressList;
        }



        @Override
        protected void onPostExecute(List<Address> addresses) {
            super.onPostExecute(addresses);

            dialog.dismiss();
            if(addresses == null)
            {
                Log.d("demo", "No results found");
            }
            else
            {

                //onMapReady(null, );
                Log.d("demo", "" + addresses.toString());

                lat = addresses.get(0).getLatitude();

                Log.d("demo", ""+lat);


            }

        }
    }

    class ReverseGeocodingTask extends AsyncTask<Void, Void, Address>{
        double lat, lng;
        ProgressDialog dialog;

        public ReverseGeocodingTask(double lat, double lng){
            this.lat = lat; this.lng = lng;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(GeoCodingDemo.this);
            dialog.setMessage("ReverseGeocodingTask Charlotte");
            dialog.show();
        }


        @Override
        protected Address doInBackground(Void... params) {
            Geocoder geocoder = new Geocoder(GeoCodingDemo.this);
            Address address = null;
            try{
                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                if(addresses != null){
                    address = addresses.get(0);
                }
            }
            catch(IOException e){
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return address;
        }
        @Override
        protected void onPostExecute(Address result) {
            dialog.dismiss();
            Log.d("demo", result.toString());
        }
    }
}
