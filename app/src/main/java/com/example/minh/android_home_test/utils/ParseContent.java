package com.example.minh.android_home_test.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

import com.example.minh.android_home_test.MainActivity;
import com.example.minh.android_home_test.model.Item;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ParseContent {

    private MainActivity activity;
    private SwipeRefreshLayout lyRefesh;
    private static final int jsoncode = 1;

    public ParseContent(MainActivity activity,SwipeRefreshLayout lyRefesh) {
        this.activity = activity;
        this.lyRefesh = lyRefesh;
    }

    @SuppressLint("StaticFieldLeak")
    public void parseJson() throws MalformedURLException {
        final URL url = new URL("https://gist.githubusercontent.com/talenguyen/38b790795722e7d7b1b5db051c5786e5/raw/63380022f5f0c9a100f51a1e30887ca494c3326e/keywords.json");
        if (!Utils.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "Please check out your internet!", Toast.LENGTH_LONG).show();
        } else {
            Utils.showSimpleProgressDialog(activity);
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    StringBuilder response = new StringBuilder();
                    try {

                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line = "";
                        while (line != null) {
                            line = bufferedReader.readLine();
                            response.append(line);
                        }
                    } catch (Exception e) {
                        Log.e("Minh", "doInBackground: " + response);
                    }
                    return response.toString();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    try {
                        onTaskCompleted(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }
    }


    private void onTaskCompleted(String response) throws JSONException {
        switch (ParseContent.jsoncode) {
            case jsoncode:
                Utils.removeSimpleProgressDialog();
                getAll(response);
                break;
        }
    }

    private void getAll(String response) throws JSONException {
        List<Item> itemList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);
        Item item;
        for (int i = 0; i < jsonArray.length(); i++) {
            item = new Item(jsonArray.get(i).toString());
            itemList.add(item);
        }
        activity.initAdapterData(itemList);
        lyRefesh.setRefreshing(false);
    }

}
