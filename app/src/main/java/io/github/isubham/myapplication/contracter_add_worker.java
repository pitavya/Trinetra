package io.github.isubham.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.s;
import io.github.isubham.myapplication.utility.volley_wrapper;


public class contracter_add_worker extends volley_wrapper {
    @Override
    public Map makeParams() {

        Map<String, String> worker_data = new HashMap<>();

        worker_data.put("worker_name", s.text(worker_name));
        worker_data.put("worker_type", worker_type);
        worker_data.put("worker_aadhar_id", s.text(worker_aadhar_id));
        worker_data.put("worker_fingerprint", s.text(worker_fingerprint));

        worker_data.put("user_id", user_id);

        worker_data.put("module", data_wrapper.QMODULE_WORKER);
        worker_data.put("query_type",   data_wrapper.QTYPE_I);
        worker_data.put("query",    data_wrapper.Q_CREATE_WORKER);

        return worker_data;

    }

    @Override
    public void make_volley_request(StringRequest stringRequest) {
        Volley.newRequestQueue(this).add(stringRequest);
    }

// ui and data

    // TODO add fingerprint
    String worker_type;
    EditText worker_name, worker_aadhar_id, worker_fingerprint;


    /* TODO : get them from real db */
    String user_id;


    public void handle_response(String response){

        Log.i("ctr_add_worker", response);
        JSONObject res ;
        try{
            res = new JSONObject(response);

            String return_status = (String)res.get("status");

            Log.i("return_Status", return_status);
            switch (return_status) {

                case data_wrapper.RETURN_WORKER_ADDED : {

                    Toast.makeText(this, "manpower added", Toast.LENGTH_SHORT).show();
                    Log.i("status", "manpower added");

                }break;

                case data_wrapper.RETURN_WORKER_ALREADY_PRESENT : {
                    Toast.makeText(this, "manpower already present", Toast.LENGTH_SHORT).show();
                    Log.i("status", "manpower already added");

                }
                break;

            }

            // TODO clear the fields
            resetEditText(worker_aadhar_id, worker_name, worker_fingerprint);

        } catch (JSONException e)
        {
            Log.e("ERROR 214 json", e.getMessage());
        }


    }

    public void resetEditText(EditText ...ets){
        for (EditText e : ets) {
            e.setText("");
        }
    }


    public void create_new_worker(View V) {
        make_request();
    }

    public void get_worker_type(View V){

        switch (V.getId()){

            case R.id.c_a_w_worker_type_1:
                worker_type = data_wrapper.W_TYPE_PERM_SEMI_SKILLED;

            case R.id.c_a_w_worker_type_2:
                worker_type = data_wrapper.W_TYPE_PERM_SEMI_SKILLED;

            case R.id.c_a_w_worker_type_3:
                worker_type = data_wrapper.W_TYPE_PERM_SEMI_SKILLED;

            case R.id.c_a_w_worker_type_4:
                worker_type = data_wrapper.W_TYPE_PERM_SEMI_SKILLED;

            case R.id.c_a_w_worker_type_5:
                worker_type = data_wrapper.W_TYPE_PERM_SEMI_SKILLED;

            case R.id.c_a_w_worker_type_6:
                worker_type = data_wrapper.W_TYPE_PERM_SEMI_SKILLED;

        }

    }

    public void back_to(View V){
        // TODO :
        startActivity(new Intent(contracter_add_worker.this, contracter_home.class));
    }

    public void init() {
        worker_aadhar_id=  findViewById(R.id.c_a_w_aadhar_id);
        worker_name =  findViewById(R.id.c_a_w_worker_name);
        worker_fingerprint = findViewById(R.id.c_a_w_worker_fingerprint);
    }

    Bundle bundle;
    String bundle_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contracter_add_worker);

        // => state

        bundle = getIntent().getExtras();

        if (bundle != null){

            bundle_string = bundle.getString(
                    "bundle_data_contracter_home_to_contracter_add_manpower"
            );
            try{
                // => set user_id
                user_id = (s.string_to_json(bundle_string)).getString("user_id");
                Toast.makeText(this, "state changed", Toast.LENGTH_SHORT).show();
            }catch (JSONException e){
                Log.e("contracter_add_worker", "json exception");
            }

        }

        init();
    }
}
