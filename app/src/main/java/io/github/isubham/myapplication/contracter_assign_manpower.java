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
import io.github.isubham.myapplication.utility.volley_wrapper;
import io.github.isubham.myapplication.utility.s;

public class contracter_assign_manpower extends volley_wrapper {

    EditText new_worker_aadhar_edittext;

    // States =>
    // TODO get this user_id from other activities
    String project_id = "5";
    String package_id = "5";
    String user_id = "17";


    @Override
    public Map makeParams() {
        // make a map
        String new_worker_aadhar = s.text(new_worker_aadhar_edittext);
        Map<String, String> new_worker = new HashMap<>();

        new_worker.put("user_id", user_id);
        new_worker.put("worker_aadhar_id", new_worker_aadhar);
        new_worker.put("package_id", package_id);

        new_worker.put("module", "worker_package");
        new_worker.put("query_type", data_wrapper.QTYPE_I);
        new_worker.put("query", "create_worker_package");

        return new_worker;
    }

    @Override
    public void handle_response(String response) {
        // TODO : handle response
        Log.e("handle_res add admin", response);

        try{

            JSONObject is_admin_added = new JSONObject(response);

            switch ((String)is_admin_added.get("status")){

                case data_wrapper.RETURN_WORKER_ADDED_TO_PACKAGE : {
                    Toast.makeText(this, "WORKER ADDED IN PACKAGE", Toast.LENGTH_SHORT).show();
                }break;

                case data_wrapper.RETURN_WORKER_NOT_AVAIlABLE: {
                    Toast.makeText(this, "NO WORKER WITH GIVEN AADHAR", Toast.LENGTH_SHORT).show();
                }break;

                case data_wrapper.RETURN_WORKER_ALREADY_PRESENT_IN_PACKAGE : {
                    Toast.makeText(this, "WORKER ALREADY IN PACKAGE",
                            Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.contracter_assign_manpower);

        new_worker_aadhar_edittext = findViewById(R.id.new_worker_aadhar_id);


            // => bundle => model => state
        bundle = getIntent().getExtras();

        if (bundle != null){

            bundle_string = bundle.getString("bundle_data_ctr_package_to_ctr_assign_manpower");
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


    public void add_new_worker_in_package(View V){
        make_request();
    }
}
