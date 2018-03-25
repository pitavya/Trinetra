package io.github.isubham.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import io.github.isubham.myapplication.utility.*;


public class sign_in extends volley_wrapper
{

    // ui
    EditText email, password;
    TextView si_status;

    public void init() {
        email =  findViewById(R.id.si_email);
        password =  findViewById(R.id.si_password);
        si_status =  findViewById(R.id.si_status);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        init();
    }

    public void sign_in(View V) {

        Log.i("sign_in", makeParams().toString());
        make_request();

    }

    @Override
    public Map makeParams() {


        Map<String, String> user_data = new HashMap<String, String>();

        user_data.put("email", s.text(email));
        user_data.put("password", s.salty(s.text(password)));

        // flags for query
        user_data.put("module", data_wrapper.QMODULE_USER);
        user_data.put("query_type", data_wrapper.QTYPE_O);
        user_data.put("query", data_wrapper.Q_SIGN_IN);

        Log.i("makeparams in sign_in", user_data.toString());

        return user_data;

    }

    @Override
    public void handle_response(JSONObject jsonObject) {

        Log.i("handle_response sign_in", jsonObject.toString());

        // if auth not found
        try {
            if (jsonObject.has("status")) {
                // give wrong credential info
                si_status.setText("Email or password don't match");
            }

            // else goto user_panel depending on the user_type

            else {
                String user_type =
                (jsonObject.getJSONObject(email.getText().toString())).getString("user_type");
                if (user_type == data_wrapper.AC_ADMIN)
                        Toast.makeText(this, "goto admin", Toast.LENGTH_SHORT).show();
                        // TODO : goto admin

                else if(user_type == data_wrapper.AC_CONTRACTOR)
                        Toast.makeText(this, "goto contracter", Toast.LENGTH_SHORT).show();
                        // TODO : goto contracter

                else{
                    Toast.makeText(this, "goto supervisor", Toast.LENGTH_SHORT).show();
                    // TODO : goto supervisor
                }
                }

       }catch (JSONException e){
            Log.e("handle_response sign_in", e.getMessage());
        }

    }

    @Override
    public void make_volley_request(StringRequest stringRequest) {

        Volley.newRequestQueue(this).add(stringRequest);

        Log.i("volley_request sign_in", stringRequest.toString());

    }

    @Override
    public void handle_error_response(VolleyError error) {

        Log.i("handle_error_response", error.toString());

    }

    @Override
    public void handle_jsonexception_error(JSONException e) {

        Log.i("handle_jsonexception", e.toString());

    }
}

