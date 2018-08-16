package io.github.isubham.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import io.github.isubham.myapplication.utility.s;
import io.github.isubham.myapplication.utility.volley_wrapper;

public class admin_view_manpower extends volley_wrapper {
    ProgressDialog progressDialog;

    JSONObject worker_gist;

    String manpower_string;
String user_id;


    RecyclerView worker_rv;
    worker_adapter worker_adapter;
    LinearLayoutManager linearLayoutManager;
    private List<worker> workers;
    String bundle_string;

    private void init() {
        worker_rv = (RecyclerView) findViewById(R.id.a_v_m_worker_list);
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

                            }
                        }));



    }

    @Override
    public Map makeParams() {

        Map<String, String> worker_detail_inside_package =
                new HashMap<>();


        worker_detail_inside_package.put("package_id", package_id_string);

        // TODO get from spinner the list of contracters
        worker_detail_inside_package.put("user_id", "43");
        worker_detail_inside_package.put("query", "read_worker_per_package_per_contracter");
        worker_detail_inside_package.put("query_type", "o");
        worker_detail_inside_package.put("module", "worker_package");

        Log.e("make_params", worker_detail_inside_package.toString());
        return worker_detail_inside_package;

    }

    @Override
    public void handle_response(String response) {


        progressDialog.hide();

        Log.i("admin_project response", response);


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

    }

    @Override
    public void make_volley_request(StringRequest stringRequest) {
        Volley.newRequestQueue(admin_view_manpower.this).add(stringRequest);
    }


    public void fetch_contracter_list(){

        volley_wrapper fetch_contracter = new volley_wrapper() {
            @Override
            public Map makeParams() {
                return null;
            }

            @Override
            public void handle_response(String response) {

            }

            @Override
            public void make_volley_request(StringRequest stringRequest) {

            }
        };

    }


    public void fill_spinner(){
        /*
         List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("item1");
        spinnerArray.add("item2");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner1);
        sItems.setAdapter(adapter);
        also to find out what is selected you could do something like this

        String selected = sItems.getSelectedItem().toString();
        if (selected.equals("what ever the option was")) {
        }
        */
    }

    Bundle bundle;
    String bundle_data, package_id_string;
    JSONObject bundle_json_object;
    TextView admin_manpower_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_manpower);


        progressDialog = new ProgressDialog(admin_view_manpower.this);

        progressDialog.setTitle("fetching workers");

        progressDialog.show();

        // get bundle to get package id


        // => states
        bundle = getIntent().getExtras();

        if (bundle != null){
            bundle_data = bundle.getString("admin_package_to_admin_view_manpower");

            // get bundle user_id and project_id

            try{
                bundle_json_object = s.string_to_json(bundle_data);

                Toast.makeText(this, "states fetched",
                        Toast.LENGTH_SHORT).show();

                package_id_string = bundle_json_object
                        .getString("package_id");

            }catch (JSONException e){
                Log.e("admin_view_manpower", "json exception");
            }
        }



        init();
        make_request();

    }




   /*
      { worker_id => {worker_id, worker_type, user_id} } =>
   *
   * {
   *    ctr_name : {
   *        type 1 : 4,
   *        type 2 : 3
   *    },
   *
   *    ..
   *    ..
   *  }
   *
   * */
//    fetch workers based on package

    // classify based on contracter

//    classify and count based on skill type



}
