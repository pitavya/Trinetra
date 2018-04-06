package io.github.isubham.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

public class admin_add_contracter_package extends volley_wrapper {

    EditText new_contracter_email;

    // TODO get this user_id from other activities
    String package_id ;
    String project_id ;
    String user_id ;

    @Override
    public Map makeParams() {
                // make a map

        String new_contracter_email_string = s.text(new_contracter_email);

        Map<String, String> new_admin_params = new HashMap<>();

        new_admin_params.put("user_adder_id", user_id);
        new_admin_params.put("user_added_email", new_contracter_email_string);
        new_admin_params.put("package_id", package_id);
        new_admin_params.put("project_id", project_id);

        new_admin_params.put("module", data_wrapper.QMODULE_USER_PACKAGE);
        new_admin_params.put("query_type", data_wrapper.QTYPE_I);
        new_admin_params.put("query", data_wrapper.Q_ADD_USER_PACKAGE);

        return new_admin_params;
    }

    @Override
    public void handle_response(String response) {
        // TODO : handle response
        Log.e("handle_res add ctr", response);

        try{

            JSONObject is_admin_added = new JSONObject(response);

            switch ((String)is_admin_added.get("status")){

                case data_wrapper.RETURN_EMAIL_NOT_IN_USER: {
                    Toast.makeText(this, "NO CONTRACTER WITH GIVEN EMAIL", Toast.LENGTH_SHORT).show();
                }break;

                case data_wrapper.RETURN_USER_ALREADY_IN_PACKAGE: {
                    Toast.makeText(this, "CONTRACTER ALREADY IN PROJECT", Toast.LENGTH_SHORT).show();
                }break;

                case data_wrapper.RETURN_USER_ADDED_IN_PACKAGE: {
                    Toast.makeText(this, "CONTRACTER ADDED", Toast.LENGTH_SHORT).show();
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

    Bundle bundle;
    String bundle_data;
    JSONObject bundle_json_object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_contracter_package);
        new_contracter_email = (EditText) findViewById(R.id.new_contracter_email);


         bundle = getIntent().getExtras();

        if (bundle != null) {

            bundle_data = bundle.getString("bundle_date_admin_package_to_admin_add_contracter_package");

            Log.e("bundle_data", bundle_data);
            // get bundle user_id and project_id
            // => get data from bundle


            //
            bundle_json_object = s.string_to_json(bundle_data);

            try{

            // set in state variables
            package_id = bundle_json_object.getString("package_id");
            project_id = bundle_json_object.getString("project_id");
            user_id = bundle_json_object.getString("user_id");

                Toast.makeText(this, "states set package_id = "
                        +package_id+" project_id = "
                        +project_id+" user_id = "
                        +user_id
                        ,
                        Toast.LENGTH_SHORT).show();
                // =>

            }catch (JSONException e){
                Log.e("admin_add_contracter","json excpetion");
            }
            //
        }else{
            Toast.makeText(this, "no data in bundle", Toast.LENGTH_SHORT).show();
        }

    }

    // TODO add method to add a new contracter to the package
    public void add_new_contracter_in_project(View V){
        make_request();
    }
}
