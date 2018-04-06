package io.github.isubham.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.s;
import io.github.isubham.myapplication.utility.volley_wrapper;

public class admin_add_admin_project extends volley_wrapper {

    EditText new_admin_email;

    // TODO get this user_id from other activities
    String project_id ;
    String user_id;


    @Override
    public Map makeParams() {
        // make a map

        String new_admin = s.text(new_admin_email);

        Map<String, String> new_admin_params = new HashMap<>();

        new_admin_params.put("user_adder_id", user_id);
        new_admin_params.put("user_added_email", new_admin);
        new_admin_params.put("project_id", project_id);

        new_admin_params.put("module", data_wrapper.QMODULE_USER_PROJECT);
        new_admin_params.put("query_type", data_wrapper.QTYPE_I);
        new_admin_params.put("query", data_wrapper.Q_ADD_USER_PROJECT);

        return new_admin_params;
    }

    @Override
    public void handle_response(String response) {
        // TODO : handle response
        Log.e("handle_res add admin", response);

        try{

            JSONObject is_admin_added = new JSONObject(response);

            switch ((String)is_admin_added.get("status")){

                case data_wrapper.RETURN_USER_ALREADY_IN_PROJECT : {
                    Toast.makeText(this, "ADMIN ALREADY IN PROJECT", Toast.LENGTH_SHORT).show();
                }break;

                case data_wrapper.RETURN_EMAIL_NOT_IN_USER: {
                    Toast.makeText(this, "NO ADMIN WITH GIVEN EMAIL", Toast.LENGTH_SHORT).show();
                }break;

                default : {
                    Toast.makeText(this, "ADMIN ADDED", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (JSONException e){
            Log.e("JSONEX", "JSON exception in admin add admin project handling repsonse");
        }
    }

    @Override
    public void make_volley_request(StringRequest stringRequest) {
        Volley.newRequestQueue(this).add(stringRequest);
    }

    Bundle bundle_data;
    String bundle_data_string;
    JSONObject bundle_data_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_admin_project);

        new_admin_email = findViewById(R.id.new_admin_email);
        // => states


        // handle state
        bundle_data = getIntent().getExtras();

        //
        if(bundle_data != null) {

            bundle_data_string = bundle_data
                    .getString("bundle_data_admin_project_to_admin_add_admin");

            Log.e("bundle_json_project", bundle_data_string);
            // Toast.makeText(this, "bundle not empty", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "null user details", Toast.LENGTH_SHORT).show();
        }

        //

        bundle_data_json = s.string_to_json(bundle_data_string);

        try{
            user_id = bundle_data_json.getString("user_id");
            project_id = bundle_data_json.getString("project_id");

        }catch (JSONException e){
            Log.e("admin_add_admin_project",
                     "json exception while setting project_id and user_id");
        }


        // => end of states

        // bundle_data_admin_project_to_admin_add_admin
    }


    public void add_new_admin_in_project(View V){
        make_request();
    }
}
