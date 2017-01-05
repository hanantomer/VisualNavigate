package com.visualnavigate;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by hanan on 1/1/2017.
 */

public class RemoteLog extends AsyncTask {

    private static final String SERVER_URL = "http://40.76.76.10/RoadView/Handler.ashx";

    @Override
    protected Object doInBackground(Object[] objects) {

        HttpURLConnection connection = null;

        String logType = objects[0].toString();

        String message = objects[1].toString();

        try {
            String urlParameters = "?message=" + URLEncoder.encode(message, "UTF-8") + "&requestType=log&logType=" + logType;
            URL url = new URL(SERVER_URL + urlParameters);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            //connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");

            InputStream in = connection.getInputStream();
            in.close();

            connection.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }

        return null;
    }
}
