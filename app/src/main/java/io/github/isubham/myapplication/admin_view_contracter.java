package io.github.isubham.myapplication;

import android.app.ProgressDialog;
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


import io.github.isubham.myapplication.adapters.user_adapter;
import io.github.isubham.myapplication.adapters.worker_adapter;
import io.github.isubham.myapplication.model.user;
import io.github.isubham.myapplication.model.worker;
import io.github.isubham.myapplication.utility.RecyclerItemClickListener;
import io.github.isubham.myapplication.utility.s;
import io.github.isubham.myapplication.utility.volley_wrapper;

public class admin_view_contracter extends volley_wrapper{
    ProgressDialog progressDialog;

    String user_id, project_id;


    String bundle_string;


            @Override
            public Map makeParams() {
                Map contract_map_param = new HashMap();

                contract_map_param.put("user_adder_id", user_id);
                contract_map_param.put("package_id", package_id_string);

                contract_map_param.put("module", "user_project");
                contract_map_param.put("query", "admin_view_contracter");
                contract_map_param.put("query_type", "o");

                return contract_map_param;

            }

            @Override
            public void handle_response(String response) {
                progressDialog.hide();

                try{
                    JSONObject users_details = new JSONObject(response);

                    JSONArray users_ids = users_details.names();

                    for(int i = 0; i < users_ids.length(); i++){

                        String user_id = (String) users_ids.get(i);

                        JSONObject user_detail = users_details.getJSONObject((String)user_id);

                        user user = new user();

                        user.name = user_detail.getString("name");
                        user.email = user_detail.getString("email");
                        user.user_id = user_id;
                        user.user_type = user_detail.getString("user_type");
                        users.add(user);
                        user_adapter.notifyDataSetChanged();
                    }


                }catch (JSONException e) {
                    Log.e("json ex", "json exception in the user detail");
                }


            }

            @Override
            public void make_volley_request(StringRequest stringRequest) {
                Volley.newRequestQueue(admin_view_contracter.this)
                        .add(stringRequest);
            }


    Bundle bundle;
    String bundle_data, package_id_string;
    JSONObject bundle_json_object;


    RecyclerView user_rv;
    user_adapter user_adapter;
    LinearLayoutManager linearLayoutManager;
    private List<user> users;


    public void init() {
        user_rv = findViewById(R.id.a_v_c_contracter_list);
        linearLayoutManager = (new LinearLayoutManager(this));
        users = new ArrayList<>();
        user_adapter = new user_adapter(users);

        user_rv.setAdapter(user_adapter);
        user_rv.setLayoutManager(linearLayoutManager);

        user_rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }
                        }
                ));
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_contracter);


        progressDialog = new ProgressDialog(admin_view_contracter.this);

        progressDialog.setTitle("fetching workers");

        progressDialog.show();

        // get bundle to get package id


        // => states
        bundle = getIntent().getExtras();

        if (bundle != null){
            bundle_data = bundle.getString("admin_package_to_admin_view_contracter");

            // get bundle user_id and project_id

            try{
                bundle_json_object = s.string_to_json(bundle_data);

                Toast.makeText(this, "states fetched",
                        Toast.LENGTH_SHORT).show();

                package_id_string = bundle_json_object
                        .getString("package_id");

                project_id = bundle_json_object.getString("project_id");

                user_id = bundle_json_object.getString("user_id");

                Log.e("text", package_id_string + project_id + user_id);

            }catch (JSONException e){
                Log.e("admin_view_contracter", "json exception");
            }
        }


        init();
        make_request();

    }

}
