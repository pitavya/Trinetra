package io.github.isubham.myapplication;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.github.isubham.myapplication.utility.s;
import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.network;


public class create_account extends AppCompatActivity {

    // variables
    EditText email, password, aadhar_id, name;
    String account_type;


    void init() {

        email = (EditText) findViewById(R.id.ca_email);
        password = (EditText) findViewById(R.id.ca_password);
        aadhar_id = (EditText) findViewById(R.id.ca_aadhar_id);
        name = (EditText) findViewById(R.id.ca_name);

    }

    public void get_account_type(View V) {
        switch (V.getId()) {
            case R.id.account_type_admin:
                account_type = data_wrapper.AC_ADMIN;
            case R.id.account_type_contractor:
                account_type = data_wrapper.AC_CONTRACTOR;
            case R.id.account_type_supervisor:
                account_type = data_wrapper.AC_SUPERVISOR;
        }

    }

    public Map<String, String> build_user_data() {

        Map<String, String> user_data = new HashMap<String, String>();

        user_data.put("email",          s.text(email));
        user_data.put("password",       s.salty(s.text(password)));
        user_data.put("user_type",      account_type);
        user_data.put("aadhar_id",      s.text(aadhar_id));
        user_data.put("name",           s.text(name));

        // flags for query
        user_data.put("module", data_wrapper.QMODULE_USER);
        user_data.put("query_type",   data_wrapper.QTYPE_I);
        user_data.put("query",    data_wrapper.Q_CREATE_ACCOUNT);

        return user_data;

    }

    public void create_account(View V) {

        /*
        HashMap<String, String> data = build_user_data();

        for (String i: data.keySet()) {
            Toast.makeText(this, i + " v : " + data.get(i), Toast.LENGTH_SHORT).show();
        }
        */


        TextView log = (TextView)findViewById(R.id.ca_log);
        log.setText(build_user_data().toString());


        /*

        ////////////////////////////////////////////////////////////////
        // fetch a String
        ////////////////////////////////////////////////////////////////

        String url = "http://ip.jsontest.com";
        StringRequest stringRequest  = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // handle result
                        Toast.makeText(create_account.this, "Hello" + response,
                                Toast.LENGTH_SHORT).show();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(create_account.this, "Error Occured",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Volley.newRequestQueue(this).add(stringRequest);

        */

        ////////////////////////////////////////////////////////////////
        // fetch json
        ////////////////////////////////////////////////////////////////

        /*

        String url = "http://httpbin.org/get?site=code&network=tutsplus";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String res = response.toString();
                        Toast.makeText(create_account.this, res,
                                Toast.LENGTH_SHORT).show();

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        Volley.newRequestQueue(this).add(jsonObjectRequest);

        */

        ////////////////////////////////////////////////////////////////
        // fetch a image
        ////////////////////////////////////////////////////////////////

        /*

        final ImageView my_image = (ImageView) findViewById(R.id.my_image);

        String url = // "https://avatars0.githubusercontent.com/u/15142714?s=460&v=4";
                "http://www.clker.com/cliparts/3/m/v/Y/E/V/small-red-apple-md.png";

        ImageRequest imageRequest = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        my_image.setImageBitmap(response);
                    }
                },

                0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888,

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(create_account.this, "Error",
                                Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        );

        Volley.newRequestQueue(this).add(imageRequest);

        */

        // making a GET/POST request with data

        String url = data_wrapper.BASE_URL_TEST;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject res ;
                        try{
                            res = new JSONObject(response);
                            String id = res.get("status").toString();

                            if (! id.equals("0")) {
                                /* TODO : go to sign in */
                                Toast.makeText(create_account.this, "Account create id : " + id , Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(create_account.this, "Error ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            Log.e("ERROR 214 json", e.getMessage());
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(create_account.this, "Error in POST", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ){

            @Override
            protected Map<String, String> getParams() {

                return build_user_data();

            }
        };

        Volley.newRequestQueue(this).add(stringRequest);



    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        init();
    }


    class fetch_url extends AsyncTask<String, String, String> {

        public void make_url() {

            try{

             String url = URLEncoder.encode("http:localhost.com/subham/trinetra/design.php",
                     "utf-8");
            } catch (UnsupportedEncodingException e) {
                Log.e("line 93", "invalid encoding ");
            }
        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(String... v) {

        }

        @Override
        protected String doInBackground(String... strings)  {

            JSONObject res = new JSONObject();
            try {
                res = network.get_json_from_url("www.google.com");
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
        }
    }


}
