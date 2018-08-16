package io.github.isubham.myapplication;

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


public class supervisor_add_worker extends AppCompatActivity {


    // ui and data

    String worker_type;
    EditText worker_name, worker_aadhar_id,  worker_designation;

    /* TODO : get them from real db */
    String project_id  = "12";
    String user_id  = "12";


    public Map<String, String> build_worker_data(){
        Map<String, String> worker_data = new HashMap<>();

        worker_data.put("name", s.text(worker_name));
        worker_data.put("worker_type", worker_type);
        worker_data.put("job_designation", s.text(worker_designation));
        worker_data.put("aadhar_id", s.text(worker_aadhar_id));

        worker_data.put("project_id", project_id);
        worker_data.put("user_id", user_id);

        worker_data.put("module", data_wrapper.QMODULE_WORKER);
        worker_data.put("query_type",   data_wrapper.QTYPE_I);
        worker_data.put("query",    data_wrapper.Q_CREATE_WORKER);

        return worker_data;
    }


    public void handle_response(String response){

        // Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        //
                        JSONObject res ;
                        try{
                            res = new JSONObject(response);
                            String id = res.get("status").toString();

                            if (! id.equals("0")) {
                                Toast.makeText(supervisor_add_worker.this, "worker create id : " + id , Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(supervisor_add_worker.this, "Error ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            Log.e("ERROR 214 json", e.getMessage());
                        }
                        //


    }

    public void handle_error(){

    }



    public void create_new_worker(View V) {
            String url = data_wrapper.BASE_URL_LOCAL;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        handle_response(response);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(supervisor_add_worker.this, "Error in POST", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ){

            @Override
            protected Map<String, String> getParams() {

                return build_worker_data();

            }
        };

        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void get_worker_type(View V){

        switch (V.getId()){

            case R.id.s_a_w_worker_type_1:
                worker_type = data_wrapper.W_1;

            case R.id.s_a_w_worker_type_2:
                worker_type = data_wrapper.W_2;

            case R.id.s_a_w_worker_type_3:
                worker_type = data_wrapper.W_3;

            case R.id.s_a_w_worker_type_4:
                worker_type = data_wrapper.W_4;

            case R.id.s_a_w_worker_type_5:
                worker_type = data_wrapper.W_5;

            case R.id.s_a_w_worker_type_6:
                worker_type = data_wrapper.W_6;

        }

    }

    public void back_to(View V){
        // TODO :
    }

    public void init() {
        worker_aadhar_id= (EditText) findViewById(R.id.s_a_w_aadhar_id);
        worker_name = (EditText) findViewById(R.id.s_a_w_worker_name);
        worker_designation = (EditText) findViewById(R.id.s_a_w_worker_job_designation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_add_worker);

        init();
    }
}
