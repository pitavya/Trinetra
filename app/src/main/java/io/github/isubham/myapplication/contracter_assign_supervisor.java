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


import io.github.isubham.myapplication.utility.s;
import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.volley_wrapper;

public class contracter_assign_supervisor extends volley_wrapper {

    EditText new_supervisor_email;

    // States =>
    // TODO get this user_id from other activities
    String project_id = "5";
    String package_id = "5";
    String user_id = "14";


    @Override
    public Map makeParams() {
        // make a map

        String new_admin = s.text(new_supervisor_email);

        Map<String, String> new_admin_params = new HashMap<>();

        new_admin_params.put("user_added_email", new_admin);

        new_admin_params.put("user_adder_id", user_id);
        new_admin_params.put("project_id", project_id);
        new_admin_params.put("package_id", package_id);

        new_admin_params.put("module", data_wrapper.QMODULE_USER_PACKAGE);
        new_admin_params.put("query_type", data_wrapper.QTYPE_I);
        new_admin_params.put("query", data_wrapper.Q_ADD_USER_PACKAGE);

        return new_admin_params;
    }

    @Override
    public void handle_response(String response) {
        // TODO : handle response
        Log.e("handle_res add admin", response);

        try{

            JSONObject is_admin_added = new JSONObject(response);

            switch ((String)is_admin_added.get("status")){

                case data_wrapper.RETURN_USER_ALREADY_IN_PACKAGE : {
                    Toast.makeText(this, "SUPERVISOR ALREADY IN PACKAGE", Toast.LENGTH_SHORT).show();
                }break;

                case data_wrapper.RETURN_EMAIL_NOT_IN_USER: {
                    Toast.makeText(this, "NO SUPERVISOR WITH GIVEN EMAIL", Toast.LENGTH_SHORT).show();
                }break;

                case data_wrapper.RETURN_USER_ADDED_IN_PACKAGE : {
                    Toast.makeText(this, "SUPERVISOR ADDED", Toast.LENGTH_SHORT).show();
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
    String bundle_string;
    JSONObject bundle_jsonobject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contracter_assign_supervisor);

        new_supervisor_email = findViewById(R.id.new_supervisor_email);

        // => bundle => model => state
        bundle = getIntent().getExtras();

        if (bundle != null){

            bundle_string = bundle.getString("bundle_data_ctr_package_to_ctr_assign_supervisor");
            bundle_jsonobject = s.string_to_json(bundle_string);

            try{
                project_id = bundle_jsonobject.getString("project_id");
                user_id = bundle_jsonobject.getString("user_id");
                package_id = bundle_jsonobject.getString("package_id");

                Toast.makeText(this, "states confirmed " +
                        "project_id " + project_id+
                        "package_id " + package_id+
                        "user_id  "+ user_id,
                        Toast.LENGTH_SHORT).show();

            }catch (JSONException e){
                Log.e("contracter_assign_s", "json exception");
            }
        }

    }


    public void add_new_supervisor_in_package(View V){
        make_request();
    }
}
