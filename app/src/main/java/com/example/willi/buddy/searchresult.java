package com.example.willi.buddy;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
    This provide API for Google Custom Search. Allow user to search on MainActivity page which will display
    search result from StackOverFlow.com website. Result is return by JSON and pharse and displayed on a list view and User can select
    result which will then open either Youtube API or phone browser to view content.

 */

public class searchresult extends AppCompatActivity {

    private static final String TAG = "searchApp";

    static String result = null;
    Integer responseCode = null;
    String responseMessage = "";
    ProgressBar mProgressBar2;
    ListView mListview;
    EditText eTxtSearch;
    Button mbtn_search;
    String[] listTitle = new String[10];
    String[] listUrl = new String[10];
    ListAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_Home:
                    startActivity(new Intent(searchresult.this, MainActivity.class));
                    return true;
                case R.id.navigation_Quiz:
                    startActivity(new Intent(searchresult.this, quiz_home.class));
                    return true;
                case R.id.navigation_todo:
                    startActivity(new Intent(searchresult.this, to_do.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);

        mProgressBar2 = findViewById(R.id.progressBar2);
        mListview = findViewById(R.id.resultListview);
        eTxtSearch = findViewById(R.id.eTxtsearchbar);
        mbtn_search = findViewById(R.id.btn_searchbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(0).setCheckable(false);
        navigation.getMenu().getItem(1).setCheckable(false);
        navigation.getMenu().getItem(2).setCheckable(false);

        //getting intent of user search from MainActivity page
        Intent intent = getIntent();
        final String withspace = intent.getStringExtra(MainActivity.WITHSPACE);
        eTxtSearch.setText(withspace);

        //extracting any space from the search
        String nospace = withspace.replaceAll(" ", "+");
        initiatesearch(nospace);

        //call initiatesearch again based on new user search criteria
        mbtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newsearch = eTxtSearch.getText().toString().trim();
                if(!newsearch.equals("")){
                    initiatesearch(newsearch);
                }
                else
                {
                    Toast.makeText(searchresult.this, "Please enter a search criteria", Toast.LENGTH_SHORT).show();
                    eTxtSearch.requestFocus();
                }
            }
        });
    }

    //Google custom search API
    private void initiatesearch(String message)
    {
        // Your API key
        // TODO replace with your value
        String key = "AIzaSyD4yFOVkXHu7WHoNV9bQ_ZjWd7CvOxH7Xk";

        // Your Search Engine ID
        // TODO replace with your value
        String cx = "012867466185715520127:yihffaevwng";

        String urlString = "https://www.googleapis.com/customsearch/v1?key=" + key + "&cx=" + cx + "&fields=items(title,link)"+"&q="+ message;

        //String urlString = "https://www.googleapis.com/customsearch/v1?q=" + message + "&key=" + key + "&cx=" + cx + "&alt=json";
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(TAG, "ERROR converting String to URL " + e.toString());
        }
        Log.d(TAG, "Url = " + urlString);

        // start AsyncTask
        GoogleSearchAsyncTask searchTask = new GoogleSearchAsyncTask();
        searchTask.execute(url);
    }

    private class GoogleSearchAsyncTask extends AsyncTask<URL, Integer, String> {

        protected void onPreExecute() {
            Log.d(TAG, "AsyncTask - onPreExecute");
            // show progressbar
            mProgressBar2.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {

            URL url = urls[0];
            Log.d(TAG, "AsyncTask - doInBackground, url=" + url);

            // Http connection
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                Log.e(TAG, "Http connection ERROR " + e.toString());
            }

            try {
                responseCode = conn.getResponseCode();
                responseMessage = conn.getResponseMessage();
            } catch (IOException e) {
                Log.e(TAG, "Http getting response code ERROR " + e.toString());
            }

            Log.d(TAG, "Http response code =" + responseCode + " message=" + responseMessage);

            try {

                if (responseCode == 200) {

                    // response OK

                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = rd.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    rd.close();

                    conn.disconnect();

                    result = sb.toString();

                    Log.d(TAG, "result=" + result);

                    return result;

                } else {

                    // response problem

                    String errorMsg = "Http ERROR response " + responseMessage + "\n" + "Make sure to replace in code your own Google API key and Search Engine ID";
                    Log.e(TAG, errorMsg);
                    result = errorMsg;
                    return result;

                }
            } catch (IOException e) {
                Log.e(TAG, "Http Response ERROR " + e.toString());
            }

            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            Log.d(TAG, "AsyncTask - onProgressUpdate, progress=" + progress);

        }

        protected void onPostExecute(String result) {

            Log.d(TAG, "AsyncTask - onPostExecute, result=" + result);

            // hide progressbar
            mProgressBar2.setVisibility(View.GONE);
            pharsing(result);
            Log.d(TAG, "JSONResult is: " + result);

        }
    }

    //pharsing JSON into Array
    private void pharsing(String result) {
        if (result != null) {
            try {
                JSONObject jSONObject = new JSONObject(result);
                // Getting JSON Array node
                JSONArray itemsArray = jSONObject.getJSONArray("items");
                Log.d(TAG, "ItemArray is " + itemsArray.toString());
                for (int i = 0; i < itemsArray.length(); i++) {
                    //int j =0;

                    String url = itemsArray.getJSONObject(i).getString("link");
                    Log.d(TAG, "pharsing URL: " + url);
                    listUrl[i] = url;

                    String title = itemsArray.getJSONObject(i).getString("title");
                    Log.d(TAG, "pharsing: Title " + title);
                    listTitle[i] = title;

                }

            } catch (JSONException e) {
                e.printStackTrace();
                e.getMessage();
            }

        populateListview();
        }
    }

    //populate listView
    private void populateListview(){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listTitle);
        mListview.setAdapter(adapter);

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: You Clicked on " + position);

                Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(listUrl[position]));
                startActivity(implicit);

            }
        });
    }
}
