package com.example.taranair.rpi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.json.JSONArray;

import android.location.LocationListener;
import android.location.Criteria;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity implements LocationListener {

    Button button1;
    CharSequence edit_text_value;


    public static boolean ifClicked = false;
    private Context mContext;
    private GoogleMap googleMap;
    double currentlat;
    double currentlong;
    Location location;
    private static final long MIN_DIST_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 1000*30;
    double distPrev;
    double dist;
    double finalLat = 38.035972;
    double finalLng = -78.503337;
    Lights mapLights;
    String url;


    protected LocationManager locationManager;
    private boolean canGetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        Intent i = getIntent();
        url = i.getStringExtra("key");
        //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();

        try {
            initializeMap();

        } catch (Exception e){
            e.printStackTrace();
        }
        //find_and_modify_text_view();


        // get reference to the views

        //Toast.makeText(getApplicationContext(), "lat " + finallat, Toast.LENGTH_SHORT).show();






        // check if you are connected or not
        /*
        if(isConnected()){
            System.out.println("App is to go");
        }
        else{
            System.out.println("NONO");
        }
        // add click listener to Button "POST"
        button1.setOnClickListener(this);
        */


    }

    private void initializeMap(){
        if (googleMap == null){
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        }

        if(googleMap == null){
            Toast.makeText(getApplicationContext(), "Sorry, unable to make map!", Toast.LENGTH_SHORT).show();
        }

        if(googleMap != null){

            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.setMyLocationEnabled(true);
            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            String provider = service.getBestProvider(criteria, true);
            Location location = service.getLastKnownLocation(provider);
            //Toast.makeText(getApplicationContext(), "Made", Toast.LENGTH_SHORT).show();
            //CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(38.0356,-78.503));
            //System.out.println(currentlat);
            //System.out.println(getLatitude());
            LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 15);
            //CameraUpdate zoom = CameraUpdateFactory.zoomTo(9);
            //this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(38.0356,-78.503)));
            this.googleMap.animateCamera(cameraUpdate);
            dist = getDist();
            //mapLights.onStart();
            /*
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(finalLat, finalLng))
                    .title("Destination"));
                    */
            //Toast.makeText(getApplicationContext(), "Distance " + dist, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "lat " + finalLat, Toast.LENGTH_SHORT).show();
            //new HttpAsyncTask().execute(url);


        }
    }

    public void GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {

        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_FOR_UPDATE,
                            MIN_DIST_FOR_UPDATE, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            currentlat = location.getLatitude();
                            currentlong = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_FOR_UPDATE,
                                MIN_DIST_FOR_UPDATE, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                currentlat = location.getLatitude();
                                currentlong = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            currentlat = location.getLatitude();
        }

        // return latitude
        return currentlat;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            currentlong = location.getLongitude();
        }

        // return longitude
        return currentlong;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    @Override
    public void onLocationChanged(Location location) {
        currentlat = location.getLatitude();
        currentlong = location.getLongitude();
        distPrev = dist;
        dist = getDist();
        if (distanceChecker()){
            lightsChangePos();
        }
        else {
            lightsChangeNeg();
        }
    }
    
    public void lightsChangePos() {
        new HttpAsyncTaskBlue().execute(url);
    }
    
    public void lightsChangeNeg(){
        new HttpAsyncTaskRed().execute(url);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }



    private boolean distanceChecker(){
        if (dist <=  distPrev){
            return true;
        }
        else
            return false;
    }

    private double torad(double x){
        return x * Math.PI / 180;
    }

    private double getDist(){
        double R = 6378137; // EarthÃ•s mean radius in meter
        double dLat = torad(finalLat - currentlat);
        double dLong = torad(finalLng - currentlong);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(torad(currentlat)) * Math.cos(torad(finalLat)) *
                        Math.sin(dLong / 2) * Math.sin(dLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d; // returns the distance in meter
    }

    public void sendMessage1(View view)
    {

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);


    }


    public static String POST(String url, Lights lights){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonLight = new JSONObject();
            try{
                jsonLight.accumulate("lightId", 1);
                jsonLight.accumulate("red", 255);
                jsonLight.accumulate("blue", 0);
                jsonLight.accumulate("green", 0);
                jsonLight.accumulate("intensity", 0.5);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonLight);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lights", jsonArray);
            jsonObject.put("propagate", true);


            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();


            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content   
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

            System.out.println(inputStream);

        } catch (Exception e) {
            Log.d("InputStream", "HELLO");
        }

        // 11. return result
        return result;
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    private class HttpAsyncTaskBlue extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String jsonhello = "{\"lights\": [{\"lightId\": 1, \"red\":0,\"green\":0,\"blue\":255, \"intensity\": 0.5}],\"propagate\": true}";

            return POST(urls[0], jsonhello);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }
    
    private class HttpAsyncTaskRed extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String jsonhello = "{\"lights\": [{\"lightId\": 1, \"red\":255,\"green\":0,\"blue\":0, \"intensity\": 0.5}],\"propagate\": true}";

            return POST(urls[0], jsonhello);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }
    
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

}
