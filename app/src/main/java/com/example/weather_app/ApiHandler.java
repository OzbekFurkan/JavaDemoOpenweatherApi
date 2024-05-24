package com.example.weather_app;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
public class ApiHandler extends AsyncTask<String, Void, JSONObject> {
    @Override
    protected JSONObject doInBackground(String... strings) {
        ApiConfiguration apiConfiguration = new ApiConfiguration();
        StringBuilder stringBuilder = new StringBuilder();
        URL uri;
        try {
            uri = new URL(apiConfiguration.mainUrl+"q="+strings[0]+apiConfiguration.appId);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) uri.openConnection();
            httpsURLConnection.connect();
            InputStream inputStream = httpsURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            int data = reader.read();
            while (data != -1) {
                stringBuilder.append((char) data);
                data = reader.read();
            }
            return new JSONObject(stringBuilder.toString());

        }catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
