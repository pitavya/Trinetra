package io.github.isubham.myapplication;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

public class create_account extends AppCompatActivity {

    // variables
    TextView fetched;
    EditText url_editText;

    void init() {
        fetched = (TextView) findViewById(R.id.fetch_id);
        url_editText = (EditText) findViewById(R.id.url_editText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        init();
    }

    class fetch_url extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            fetched.setText("fetching url");
        }

        @Override
        protected void onProgressUpdate(String... v) {

            fetched.setText("onProgressUpdate" + v[0]);
        }

        @Override
        protected String doInBackground(String... strings)  {

            JSONObject res = new JSONObject();
            try {
                res = get_json_from_url(url_editText.getText().toString().trim());
            }catch (IOException e){
                e.printStackTrace();
            }catch (JSONException e){
                e.printStackTrace();
            }

            Iterator<?> keys = res.keys();

            StringBuilder temp = new StringBuilder();

            while(keys.hasNext()) {

                try{
                    String key = (String)keys.next();
                    temp.append(
                            '\t' + key + " "
                            + res.get(key) + " "
                            + "\n"
                    );
                } catch (JSONException e){
                    e.printStackTrace();
                }

            }

            return temp.toString();
        }


        @Override
        protected void onPostExecute(String s) {
            fetched.setText(s);
        }
    }

    void handle_exception(int i){
        Log.e("Exception " , new String(" " + i));
    }

    public static JSONObject  get_json_from_url(String strings)
            throws IOException, JSONException, MalformedURLException{

        // opening connection
        HttpURLConnection httpURLConnection = null;

        URL url = new URL(strings);

        httpURLConnection = (HttpURLConnection)url.openConnection();

        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setReadTimeout(10000 /* milliseconds */);
        httpURLConnection.setConnectTimeout(15000 /* milliseconds */);


        httpURLConnection.connect();

        // requesting url
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        StringBuilder sb = new StringBuilder();
        String line;

        while((line = br.readLine()) != null) {
            sb.append(line + '\n');
        }

        br.close();

        JSONObject res;

        res = new JSONObject(sb.toString());

        return res;
    }

    public void search_word(View V) {
        new fetch_url().execute();
    }
}
