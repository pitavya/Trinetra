package io.github.isubham.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.isubham.myapplication.adapters.auth_adapter;
import io.github.isubham.myapplication.model.auth;
import io.github.isubham.myapplication.utility.RecyclerItemClickListener;
import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.volley_wrapper;


public class supervisor_authentication extends volley_wrapper {

    String shift_id = "7";
    @Override
    public Map makeParams() {

            Map<String, String> get_auth_param = new HashMap<>();

            get_auth_param.put("shift_id", shift_id);
            get_auth_param.put("module", data_wrapper.QMODULE_AUTHENTICATION);
            get_auth_param.put("query_type", data_wrapper.QTYPE_O);
            get_auth_param.put("query", "read_auth_per_shift");

            Log.i("supervisor auth panel", get_auth_param .toString());

            return get_auth_param ;

    }

    @Override
    public void handle_response(String response) {

        Log.i("handle_res ctr home", response.toString());

        /*

            {
                auth_id : {
                    worker_aadhar_id : ,
                    worker_type : ,
                },

                auth_id2 : {

                }

            }
         */


        try{

            JSONObject auths_details = new JSONObject(response);

            JSONArray auths_ids = auths_details.names();

            for(int i = 0; i < auths_ids.length(); i++) {

                String auth_id = (String)auths_ids.get(i);

                JSONObject auth_detail = auths_details.getJSONObject(
                        (String)auth_id);

                auth p = new auth();

                p.authentication_id =  auth_id;
                p.gen_worker_id =          auth_detail.getString("gen_worker_id");
                p.status=   auth_detail.getString("auth_status");
                auths.add(p);
                auth_adapter.notifyDataSetChanged();

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
    String user_id = "17";


    RecyclerView auth_rv;
    auth_adapter auth_adapter;
    LinearLayoutManager linearLayoutManager;
    private List<auth> auths;

    private void init() {
        auth_rv = (RecyclerView) findViewById(R.id.supervisor_auth_list);
        linearLayoutManager = (new LinearLayoutManager(this));
        auths = new ArrayList<>();

        auth_adapter = new auth_adapter(auths);
        auth_rv.setAdapter(auth_adapter);
        auth_rv.setLayoutManager(linearLayoutManager);


        // TODO : add touch listener
        auth_rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                // TODO
                                startActivity(new Intent(
                                       supervisor_authentication.this,
                                        supervisor_authenticate_worker.class));
                            }
                        }));

    }


    public void contracter_add_manpower(View V) {
        // startActivity(new Intent(supervisor_authentication.this, supervisor_worker_auth.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_authentication);
        init();
        fill_data();
    }


    // TODO : fetch data
    private void fill_data() {
        auths.clear();
        make_request();
    }
}
