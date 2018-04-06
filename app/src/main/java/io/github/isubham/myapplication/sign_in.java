package io.github.isubham.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
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

        user_data.put(data_wrapper.Q_PARAM_USER_EMAIL, s.text(email));
        user_data.put(data_wrapper.Q_PARAM_USER_PASSWORD, s.salty(s.text(password)));

        user_data.put(data_wrapper.MODUL, data_wrapper.QMODULE_USER);
        user_data.put(data_wrapper.QTYPE, data_wrapper.QTYPE_O);
        user_data.put(data_wrapper.QUERY, data_wrapper.Q_SIGN_IN);

        Log.i("makeparams in sign_in", user_data.toString());

        return user_data;

    }


    public void to_create_account(View V){
        startActivity(new Intent(sign_in.this,
                create_account.class));
    }

    @Override
    public void handle_response(String response) {


        JSONObject user_details;

        Log.i("handle_response sign_in", response);

        try {
            user_details = new JSONObject(response);

            // if auth not found
            if (user_details.has("status")) {
                // give wrong credential info
                si_status.setText("Email or password don't match");
            }

            // else goto user_panel depending on the user_type
            // TODO add details to bundle for next activity
            else {
                JSONObject user_detail = user_details.getJSONObject(email.getText().toString());
                String user_type = user_detail.getString("user_type");
                Log.e("user_type", user_type);

                Intent intent;
                switch (user_type) {
                    case data_wrapper.AC_ADMIN:
                        intent = new Intent(sign_in.this, admin_home.class);
                        break;

                    case data_wrapper.AC_CONTRACTOR:
                        intent = new Intent(sign_in.this, contracter_home.class);
                        break;

                    case data_wrapper.AC_SUPERVISOR:
                         intent = new Intent(sign_in.this, supervisor_home.class);
                        break;

                    default:
                          intent = new Intent(sign_in.this, create_account.class);

                }

                intent.putExtra("bundle_data", s.json_to_string(user_detail,
                                "user_email", email.getText().toString()));
                startActivity(intent);
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

}

