package io.github.isubham.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.isubham.myapplication.adapters.worker_adapter;
import io.github.isubham.myapplication.model.worker;
import io.github.isubham.myapplication.utility.RecyclerItemClickListener;
import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.volley_wrapper;
import io.github.isubham.myapplication.utility.s;


public class contracter_view_manpower extends volley_wrapper {

    @Override
    public Map makeParams() {

            Map<String, String> get_project_list_param = new HashMap<>();
            get_project_list_param.put("user_id", user_id);

            get_project_list_param.put("module", data_wrapper.QMODULE_USER_WORKER);
            get_project_list_param.put("query_type", data_wrapper.QTYPE_O);
            get_project_list_param.put("query", data_wrapper.Q_READ_USER_WORKER);

            Log.i("makeparain ctr home", get_project_list_param.toString());

            return get_project_list_param;

    }

    @Override
    public void handle_response(String response) {

        Log.i("handle_res ctr home", response.toString());

        /*

            {
                worker_id : {
                    worker_name : ,
                    worker_aadhar_id : ,
                    worker_type : ,
                },

                worker_id2 : {

                }

            }
         */


        try{

            JSONObject workers_details = new JSONObject(response);

            JSONArray workers_ids = workers_details.names();

            for(int i = 0; i < workers_ids.length(); i++) {

                String worker_id = (String)workers_ids.get(i);

                JSONObject worker_detail = workers_details.getJSONObject(
                        (String)worker_id);

                worker p = new worker();

                p.worker_name =        worker_detail.getString("worker_name");
                p.worker_id =          worker_id;
                p.worker_type=   worker_detail.getString("worker_type");
                p.worker_aadhar_id =    worker_detail.getString("worker_aadhar_id");
                workers.add(p);
                worker_adapter.notifyDataSetChanged();

            }
        }

        catch (JSONException e){
            e.printStackTrace();
        }

        //
    }

    @Override
    public void make_volley_request(StringRequest stringRequest) {
        Volley.newRequestQueue(this).add(stringRequest);
    }
/*
        CRUD Project

     */

    /*
        TODO : resolve this issue with SharedPreferences
        when user creates account use that momemt with returned user_id to be saved
        and again used.

     */

    @Override
    protected void onRestart() {
        super.onRestart();
        fill_data();
    }

    // TODO replace it with bundle user_id
    // contracter id of cona
    String user_id;


    RecyclerView worker_rv;
    worker_adapter worker_adapter;
    LinearLayoutManager linearLayoutManager;
    private List<worker> workers;

    private void init() {
        worker_rv = (RecyclerView) findViewById(R.id.c_v_m_worker_list);
        linearLayoutManager = (new LinearLayoutManager(this));
        workers = new ArrayList<>();

        worker_adapter = new worker_adapter(workers);
        worker_rv.setAdapter(worker_adapter);
        worker_rv.setLayoutManager(linearLayoutManager);


        // TODO : add touch listener
        worker_rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                startActivity(new Intent(
                                       contracter_view_manpower.this,
                                        contracter_project.class));
                            }
                        }));



    }


    public void contracter_add_manpower(View V) {
        startActivity(new Intent(contracter_view_manpower.this, contracter_add_worker.class));
    }

    Bundle bundle;
    String bundle_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contracter_view_manpower);
        init();

        bundle = getIntent().getExtras();

        if (bundle != null){

            bundle_string = bundle.getString(
                    "bundle_data_contracter_home_to_contracter_view_manpower"
            );
            try{
                // => set user_id
                user_id = (s.string_to_json(bundle_string)).getString("user_id");
                Toast.makeText(this, "state changed", Toast.LENGTH_SHORT).show();
            }catch (JSONException e){
                Log.e("contracter_view_worker", "json exception");
            }

        }



        fill_data();
    }


    // TODO : fetch data
    private void fill_data() {
        workers.clear();
        make_request();
    }
}
