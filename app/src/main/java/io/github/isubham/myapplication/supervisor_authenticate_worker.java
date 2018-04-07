package io.github.isubham.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.isubham.myapplication.utility.volley_wrapper;

public class supervisor_authenticate_worker extends volley_wrapper {


      @Override
        public Map makeParams() {

            Map<String, String> get_worker_details_param =
                    new HashMap<>();

            get_worker_details_param.put("worker_id", worker_id_string);

            get_worker_details_param.put("module", "worker");
            get_worker_details_param.put("query", "read_worker");
            get_worker_details_param.put("query_type", "o");

            Log.i("makeParams", get_worker_details_param.toString());
            return get_worker_details_param;

        }

        @Override
        public void handle_response(String response) {

          Log.i("handle_response", response);

            try {
                // inflate the view
                JSONObject worker_details = new JSONObject(response);

                worker_details = worker_details.getJSONObject(worker_id_string);

                worker_name.setText(worker_details.getString("worker_name"));
                worker_id_text.setText(worker_id_string);
                worker_aadhar.setText(worker_details.getString("worker_aadhar_id"));
                worker_type.setText(worker_details.getString("worker_type"));

                // set byte array string here
                fingerprint_byteArray = worker_details.getString("worker_fingerprint");


            }catch (JSONException e){
                Log.i("json ex", "in handle response");
            }


        }

        @Override
        public void make_volley_request(StringRequest stringRequest) {
            Volley.newRequestQueue(supervisor_authenticate_worker.this)
                    .add(stringRequest);
        }
    // TODO fetch worker details provided the worker_id
    
    Bundle bundle;
    String fingerprint_byteArray;


    TextView worker_name, worker_id_text, worker_type, worker_aadhar;

    String worker_id_string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_authenticate_worker);

        bundle = getIntent().getExtras();
        Toast.makeText(this, bundle.getString("worker_id"), Toast.LENGTH_SHORT).show();

        worker_id_string = bundle.getString("worker_id");

        // reference ui components
        worker_name = (TextView) findViewById(R.id.s_a_w_worker_name);
        worker_id_text = (TextView) findViewById(R.id.s_a_w_worker_id);
        worker_aadhar = (TextView) findViewById(R.id.s_a_w_worker_aadhar_id);
        worker_type = (TextView) findViewById(R.id.s_a_w_worker_type);

        make_request();

    }



    // for retrying the fingerprint
    public void scan(View V) {
        Log.i("worker_fingerprint", fingerprint_byteArray);
    }

    // for final submission
    public void submit(View V) {

    }

    // TODO verify button should calculate status and store data in auth table

}
