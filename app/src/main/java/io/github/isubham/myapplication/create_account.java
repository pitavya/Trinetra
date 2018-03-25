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
import io.github.isubham.myapplication.utility.volley_wrapper;


public class create_account extends volley_wrapper {

    // variables
    EditText email, password, aadhar_id, name;
    String account_type;


    void init() {

        email = (EditText) findViewById(R.id.ca_email);
        password = (EditText) findViewById(R.id.ca_password);
        aadhar_id = (EditText) findViewById(R.id.ca_aadhar_id);
        name = (EditText) findViewById(R.id.ca_name);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        init();
    }


    public void get_account_type(View V) {
        switch (V.getId()) {
            case R.id.account_type_admin:
                account_type = data_wrapper.AC_ADMIN;break;
            case R.id.account_type_contractor:
                account_type = data_wrapper.AC_CONTRACTOR;break;
            case R.id.account_type_supervisor:
                account_type = data_wrapper.AC_SUPERVISOR;break;
            default:account_type = data_wrapper.AC_ADMIN;
        }

    }



    public void create_account(View V) {
        make_request();
    }


    @Override
    public Map makeParams() {

        Map<String, String> user_data = new HashMap<String, String>();

        user_data.put("email",          s.text(email));
        user_data.put("password",       s.salty(s.text(password)));
        user_data.put("user_type",      account_type);
        user_data.put("aadhar_id",      s.text(aadhar_id));
        user_data.put("name",           s.text(name));

        // flags for query
        user_data.put("module",         data_wrapper.QMODULE_USER);
        user_data.put("query_type",     data_wrapper.QTYPE_I);
        user_data.put("query",          data_wrapper.Q_CREATE_ACCOUNT);

        return user_data;

    }

    @Override
    public void handle_response(JSONObject jsonObject) {
        try {

            String id = jsonObject.get("status").toString();

            if (!id.equals("0")) {
                /* TODO : go to sign in */
                Toast.makeText(create_account.this, "Account create id : " + id, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(create_account.this, "Email or Aadhar already exists", Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            handle_jsonexception_error(e);
        }
    }

    @Override
    public void make_volley_request(StringRequest stringRequest) {

        Volley.newRequestQueue(this).add(stringRequest);

    }

    @Override
    public void handle_error_response(VolleyError error) {

    }

    @Override
    public void handle_jsonexception_error(JSONException e) {

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
