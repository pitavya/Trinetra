package io.github.isubham.myapplication;


import android.content.Intent;
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
        }

    }



    public void create_account(View V) {
        make_request();
    }


    @Override
    public Map makeParams() {

        Map<String, String> user_data = new HashMap<String, String>();

        user_data.put(data_wrapper.Q_PARAM_USER_EMAIL,     s.text(email));
        user_data.put(data_wrapper.Q_PARAM_USER_PASSWORD,  s.salty(s.text(password)));
        user_data.put(data_wrapper.Q_PARAM_USER_USER_TYPE, account_type);
        user_data.put(data_wrapper.Q_PARAM_USER_AADHAR_ID, s.text(aadhar_id));
        user_data.put(data_wrapper.Q_PARAM_USER_NAME,      s.text(name));

        // flags for query
        user_data.put(data_wrapper.MODUL,     data_wrapper.QMODULE_USER);
        user_data.put(data_wrapper.QUERY,     data_wrapper.Q_CREATE_ACCOUNT);
        user_data.put(data_wrapper.QTYPE,     data_wrapper.QTYPE_I);

        Log.e("makeparams", user_data.toString());

        return user_data;

    }

    @Override
    public void handle_response(String response) {

        Log.i("handle response jsonobject", response);
        JSONObject jsonObject;

        try {

            jsonObject  = new JSONObject(response);
            

            String id = jsonObject.get("status").toString();
                
            switch (id) {
                case data_wrapper.RESPONSE_EMAIL_EXISTS:
                    show_dialog("aadhar exists");
                    break;

                case data_wrapper.RESPONSE_AADHAR_EXISTS:
                    show_dialog("aadhar exists");
                    break;

                case data_wrapper.RESPONSE_EMAIL_AND_PASSWORD_EXISTS:
                    show_dialog("email and aadhar exists");
                    break;

                default: {

                    startActivity(new Intent(create_account.this,sign_in.class));

                }

            }
            }catch (JSONException e) {
            Log.e("143", "JSON error on line 143");
        }
           
    }

    public void show_dialog(String message){
        Log.i("show_dialog", message);

    }

    @Override
    public void make_volley_request(StringRequest stringRequest) {

        Log.i("make volley request","volley request made");

        Volley.newRequestQueue(this).add(stringRequest);

    }


}
