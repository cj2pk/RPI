package com.example.taranair.rpi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.AsyncTask;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends Activity {
    Context mContext;
    Twitter mTwitter;
    ListView mListView;
    List<Status> statuses = new ArrayList<Status>();
    ArrayList<String> statusTexts = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        mListView = (ListView) findViewById(R.id.listview);
        mContext = getApplicationContext();
        new updateTimeline().execute();



    }

    //@Override

    /*
    public void onResume() {
        super.onResume();
        showTweetsAbout("Potatos");
    }
    */


    //return new TwitterFactory(cb.build()).getInstance();
    class updateTimeline extends AsyncTask<Void, Void, Void> {


        protected Void doInBackground(Void... arg0) {
            mListView = (ListView) findViewById(R.id.listview);
            mContext = getApplicationContext();
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setOAuthConsumerKey(getString(R.string.twitter_consumer_key));
            cb.setOAuthConsumerSecret(getString(R.string.twitter_consumer_secret));
            cb.setOAuthAccessToken(getString(R.string.access_token));
            cb.setOAuthAccessTokenSecret(getString(R.string.access_token_secret));
            mTwitter = new TwitterFactory(cb.build()).getInstance();


            try {
                statuses = mTwitter.search(new Query("Rotunda UVa")).getTweets();

                for (twitter4j.Status s : statuses) {
                    statusTexts.add(s.getText() + "\n\n");
                }
            } catch (Exception e) {
                statusTexts.add("Twitter query failed: " + e.toString());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_list_item_1, android.R.id.text1, statusTexts);
                mListView.setAdapter(adapter);
            }


            return null;
        }




        protected void onPostExecute(Void result) {
            // dismiss the dialog after getting all products
            //pDialog.dismiss();

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_list_item_1, android.R.id.text1, statusTexts);
                    mListView.setAdapter(adapter);

                }

            });
        }

    }
}