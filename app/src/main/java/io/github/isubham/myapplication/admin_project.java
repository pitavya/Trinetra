package io.github.isubham.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.isubham.myapplication.adapters.package_adapter;
import io.github.isubham.myapplication.adapters.shift_adapter;
import io.github.isubham.myapplication.model.package_;
import io.github.isubham.myapplication.model.shift;
import io.github.isubham.myapplication.utility.RecyclerItemClickListener;
import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.s;
import io.github.isubham.myapplication.utility.volley_wrapper;

public class admin_project extends volley_wrapper {
/*
*
* @info
*
* admin project panel
* - project details
* - shifts list
* - shift click
*   - attendence
*   - auth
*
* */


    @Override
    public Map makeParams() {

        Map <String, String> shift_map  = new HashMap<>();

        // TODO add from bundle
        try{

            shift_map.put("project_id", bundle_data_json.getString("project_id"));
        }catch (JSONException e){
            Log.e("json ex", "json excpetion in make params of admin project");
        }

        shift_map.put("module", data_wrapper.QMODULE_PACKAGE);
        shift_map.put("query_type", data_wrapper.QTYPE_O);
        shift_map.put("query", data_wrapper.Q_READ_PACKAGES);

        return shift_map;

    }



    public void admin_create_package(View V){
        Intent to_create_package = new Intent(admin_project.this,
                admin_create_package.class);

        to_create_package.putExtra("bundle_data_admin_project_to_admin_create_package",
                bundle_data_string+",project_id:"+project_id_string
        );

        startActivity(to_create_package);
    }


    public void admin_add_admin_project(View V){
        Intent to_add_admin_project = new Intent(admin_project.this,
                admin_add_admin_project.class);

        to_add_admin_project.putExtra("bundle_data_admin_project_to_admin_add_admin",
                bundle_data_string+",project_id:"+project_id_string
        );

        startActivity(to_add_admin_project);
    }

    @Override
    public void handle_response(String response) {

        progressDialog.hide();

        Log.i("admin_project response", response);


        //
        try{

            JSONObject packages_details = new JSONObject(response);

            JSONArray package_ids = packages_details.names();

        for(int i = 0; i < package_ids.length(); i++) {

            String package_id = (String) package_ids.get(i);

            JSONObject packages_detail = packages_details.getJSONObject(
                        (String)package_id);

            package_ package_ob = new package_();

            package_ob.package_id = package_id;
            package_ob.package_start_date = packages_detail.getString("package_start_date");
            package_ob.package_end_date = packages_detail.getString("package_end_date");
            package_ob.package_name = packages_detail.getString("package_name");

            packages.add(package_ob);
            package_adapter.notifyDataSetChanged();

        }
        } catch (JSONException e){
            e.printStackTrace();
        }
        //
    }

    @Override
    public void make_volley_request(StringRequest stringRequest) {
        Volley.newRequestQueue(this).add(stringRequest);
    }

    RecyclerView package_rv;
    package_adapter package_adapter;
    LinearLayoutManager linearLayoutManager;
    private List<package_> packages;

    // TODO : take it from intent
    // String project_id = "5";


    private void init() {
        package_rv = findViewById(R.id.a_p_package_recycleview);
        linearLayoutManager = (new LinearLayoutManager(this));
        packages = new ArrayList<>();

        package_adapter = new package_adapter(packages);
        package_rv.setAdapter(package_adapter);
        package_rv.setLayoutManager(linearLayoutManager);


        fill_data();


        // TODO : add touch listener
        package_rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {


                                    // go to admin package panel
                                // get variables
                                Intent to_admin_package = new Intent(admin_project.this,
                                        admin_package.class);

                                // get package id, name, start_date, end_date
                                String package_id = ((TextView)view.findViewById(R.id.rl_package_id))
                                        .getText().toString().trim();

                                 String package_name = ((TextView)view.findViewById(R.id.rl_package_name))
                                        .getText().toString().trim();

                                 String package_start_date = ((TextView)view.findViewById(R.id.rl_package_start_date))
                                        .getText().toString().trim();

                                 String package_end_date = ((TextView)view.findViewById(R.id.rl_package_end_date))
                                        .getText().toString().trim();


                                to_admin_package.putExtra("bundle_data_admin_project_to_admin_package",
                                        bundle_data_string + ","+
                                        "package_id:"+package_id+","+
                                        "package_name:"+package_name+","+
                                        "package_start_date:"+package_start_date+","+
                                        "package_end_date:"+package_end_date
                                );

                                startActivity(to_admin_package);

                            }
                        }));



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fill_data();
    }

    Bundle bundle_data;
    String bundle_data_string;
    JSONObject bundle_data_json;

    TextView project_name, project_start_date, project_end_date, project_id;

    String project_id_string;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_project);

        // handle state
        bundle_data = getIntent().getExtras();

        //
        if(bundle_data != null) {

            bundle_data_string = bundle_data.getString("bundle_data_admin_home_to_admin_project");
            Log.e("bundle_data_json_project", bundle_data_string);
            // Toast.makeText(this, "bundle not empty", Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(this, "null user details", Toast.LENGTH_SHORT).show();
        }
        //

        bundle_data_json = s.string_to_json(bundle_data_string);

        project_name = (TextView) findViewById(R.id.admin_project_project_name);
        project_start_date = (TextView) findViewById(R.id.admin_project_project_start_date);
        project_end_date = (TextView) findViewById(R.id.admin_project_project_end_date);
        project_id = (TextView) findViewById(R.id.admin_project_project_id);

        //
        try{

            project_name.setText(bundle_data_json.getString("project_name"));
            project_start_date.setText(bundle_data_json.getString("project_start_date"));
            project_end_date.setText(bundle_data_json.getString("project_end_date"));
            project_id.setText(bundle_data_json.getString("project_id"));

            // setting project_id string
            project_id_string = bundle_data_json.getString("project_id");

        }catch (JSONException e) {
            Log.e("json ex", "json exception in oncreate of admin project");
        }
        //
        // end of handle state


        progressDialog = new ProgressDialog(admin_project.this);
        progressDialog.setTitle("Fetching Packages");

        init();


    }

    private void fill_data(){

        // TODO : fetch data
        packages.clear();
        make_request();
        progressDialog.show();


    }
}
