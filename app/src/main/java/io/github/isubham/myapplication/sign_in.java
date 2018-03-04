package io.github.isubham.myapplication;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import io.github.isubham.myapplication.utility.*;


public class sign_in extends AppCompatActivity {

    // ui
    EditText email, password;

    public void init() {
        email = (EditText) findViewById(R.id.si_email);
        password = (EditText) findViewById(R.id.si_password);
    }


    public Map<String, String> build_user_data() {

        Map<String, String> user_data = new HashMap<String, String>();

        user_data.put("email", s.text(email));
        user_data.put("password", s.salty(s.text(password)));

        // flags for query
        user_data.put("module", data_wrapper.QMODULE_USER);
        user_data.put("query_type", data_wrapper.QTYPE_O);
        user_data.put("query", data_wrapper.Q_SIGN_IN);

        return user_data;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        init();
    }

    public void signin(View V) {
        String url = data_wrapper.BASE_URL_TEST;

        ((TextView)findViewById(R.id.si_status)).setText(build_user_data().toString());

    StringRequest stringRequest = new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    JSONObject res;
                    try {
                        res = new JSONObject(response);

                        if (res.length() > 0) {
                                /* TODO : go to sign in */
                            ((TextView)findViewById(R.id.si_status)).setText("Signed in : " + res.toString());
                        } else {
                                /* TODO : go to sign in */
                            ((TextView)findViewById(R.id.si_status)).setText("Error Signed in : ");
                        }
                    } catch (JSONException e) {
                        Log.e("ERROR 214 json", e.getMessage());
                    }


                }
            },

            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(sign_in.this, "Error in POST", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }
    ) {

        @Override
        protected Map<String, String> getParams() {

            return build_user_data();

        }
    };

    Volley.newRequestQueue(this).add(stringRequest);

    }

}

