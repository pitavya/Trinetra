package io.github.isubham.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import io.github.isubham.myapplication.model.package_;


import io.github.isubham.myapplication.adapters.package_adapter;
import io.github.isubham.myapplication.utility.RecyclerItemClickListener;
import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.volley_wrapper;
import io.github.isubham.myapplication.utility.s;

public class supervisor_project extends volley_wrapper {


    // TODO replace it with bundle user_id
    // supervisor id of supa
    String user_id, project_id_string;


    RecyclerView package_rv;
    package_adapter package_adapter;
    LinearLayoutManager linearLayoutManager;
    private List<package_> packages;

    private void init() {
        package_rv = (RecyclerView) findViewById(R.id.s_p_package_recycleview);
        linearLayoutManager = (new LinearLayoutManager(this));
        packages = new ArrayList<>();

        package_adapter = new package_adapter(packages);
        package_rv.setAdapter(package_adapter);
        package_rv.setLayoutManager(linearLayoutManager);


        // TODO : add touch listener
        package_rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Intent supervisor_project_to_supervisor_package =
                                        new Intent(
                                        supervisor_project.this,
                                        supervisor_package.class);

                                // => add package details
                                 String package_id = ((TextView)view.findViewById(R.id.rl_package_id))
                                        .getText().toString().trim();

                                 String package_name = ((TextView)view.findViewById(R.id.rl_package_name))
                                        .getText().toString().trim();

                                 String package_start_date = ((TextView)view.findViewById(R.id.rl_package_start_date))
                                        .getText().toString().trim();

                                 String package_end_date = ((TextView)view.findViewById(R.id.rl_package_end_date))
                                        .getText().toString().trim();


                                supervisor_project_to_supervisor_package.putExtra("bundle_data_sup_project_to_sup_package",
                                        bundle_string + ","+
                                        "package_id:"+package_id+","+
                                        "package_name:"+package_name+","+
                                        "package_start_date:"+package_start_date+","+
                                        "package_end_date:"+package_end_date
                                );

                                startActivity(supervisor_project_to_supervisor_package);

                            }
                        }));



    }



    Bundle bundle;
    String bundle_string;
    JSONObject bundle_jsonobject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_project);
        init();

        // => state

        bundle = getIntent().getExtras();

        if (bundle != null){


            // => get string and json and assign to state => user_id
            bundle_string = bundle.getString("bundle_data_sup_home_to_sup_project");

            try{

                user_id = s.string_to_json(bundle_string).getString("user_id");
                project_id_string = s.string_to_json(bundle_string).getString("project_id");

            }catch (JSONException e){
                Log.e("supervisor project", "json exception while parsing user_id");
            }
        }


        // => end of state

        fill_data();
    }

    @Override
    public Map makeParams() {

            Map<String, String> get_project_list_param = new HashMap<>();
            get_project_list_param.put("user_added_id", user_id);
            get_project_list_param.put("module", data_wrapper.QMODULE_PACKAGE);
            get_project_list_param.put("query_type", data_wrapper.QTYPE_O);
            get_project_list_param.put("query", "contracter_read_packages");

            Log.i("makeparain ctr home", get_project_list_param.toString());

            return get_project_list_param;

    }


    @Override
    public void handle_response(String response) {

        Log.i("handle_res ctr home", response.toString());

        /*

            {
                pk1 : {

                },

                pk2 : {

                }

            }
         */


        try{

            JSONObject packages_details = new JSONObject(response);

            JSONArray package_ids = packages_details.names();

            for(int i = 0; i < package_ids.length(); i++) {

                String package_id = (String)package_ids.get(i);

                JSONObject project_detail = packages_details.getJSONObject(
                        (String)package_id);

                package_ p = new package_();

                p.package_name =        project_detail.getString("package_name");
                p.package_id =          package_id;
                p.package_start_date=   project_detail.getString("package_start_date");
                p.package_end_date =    project_detail.getString("package_end_date");

                packages.add(p);
                package_adapter.notifyDataSetChanged();

            }
        }

        catch (JSONException e){
            e.printStackTrace();
        }

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

    // TODO : fetch data
    private void fill_data() {
        packages.clear();
        make_request();

    }
}
