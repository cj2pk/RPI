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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends Activity {

    Button button1;
    CharSequence edit_text_value;
    String url;
    String destination;
    public static boolean ifClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find_and_modify_text_view();


        // get reference to the views
        button1 = (Button) findViewById(R.id.button1);
        Intent i = getIntent();
        destination = i.getStringExtra("key");





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

    public void sendMessage(View view)
    {

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);


    }

    public void sendMessage3(View view)
    {
        ifClicked = true;

    }



    /*
    private void find_and_modify_text_view() {
        Button get_edit_view_button = (Button) findViewById(R.id.get_edit_view_button);
        get_edit_view_button.setOnClickListener(get_edit_view_button_listener);

    }

    private Button.OnClickListener get_edit_view_button_listener = new Button.OnClickListener() {
        public void onClick(View v) {
            EditText edit_text = (EditText) findViewById(R.id.edit_text);
            edit_text_value = edit_text.getText();
            setTitle("EditText:" + edit_text_value);
            url = edit_text_value.toString();
            //System.out.println(url);

        }

    };

    public static String POST(String url, String jsonSent){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 4. convert JSONObject to JSON to String
            json = jsonSent;

            System.out.println(json);
            System.out.println(url);

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
    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.button1:
                if(!validate())
                    Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                // call AsynTask to perform network operation on separate thread
                new HttpAsyncTask().execute(url);
                break;

        }

    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
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

    private boolean validate(){
        return true;
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
    */
}