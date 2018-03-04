package io.github.isubham.myapplication.utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by suraj on 2/3/18.
 */

public class network {

    public static JSONObject get_json_from_url(String strings)
            throws IOException, JSONException, MalformedURLException {

        // opening connection
        HttpURLConnection httpURLConnection = null;

        URL url = new URL(strings);

        httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setReadTimeout(10000 /* milliseconds */);
        httpURLConnection.setConnectTimeout(15000 /* milliseconds */);


        httpURLConnection.connect();

        // requesting url
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line + '\n');
        }

        br.close();

        JSONObject res;

        res = new JSONObject(sb.toString());

        return res;
    }
}
