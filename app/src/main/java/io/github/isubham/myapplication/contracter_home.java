package io.github.isubham.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

import io.github.isubham.myapplication.adapters.project_adapter;
import io.github.isubham.myapplication.model.project;
import io.github.isubham.myapplication.utility.RecyclerItemClickListener;
import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.volley_wrapper;
import io.github.isubham.myapplication.utility.s;


public class contracter_home extends volley_wrapper {

    @Override
    public Map makeParams() {

            Map<String, String> get_project_list_param = new HashMap<>();
            get_project_list_param.put("user_added_id", user_id);
            get_project_list_param.put("module", data_wrapper.QMODULE_PROJECT);
            get_project_list_param.put("query_type", data_wrapper.QTYPE_O);
            get_project_list_param.put("query", data_wrapper.Q_READ_PROJECTS);

            Log.i("makeparain ctr home", get_project_list_param.toString());

            return get_project_list_param;

    }


    public void contracter_view_manpower(View V){
        Intent contracter_view_manpower =
                new Intent(contracter_home.this, contracter_view_manpower.class);
        contracter_view_manpower.putExtra("bundle_data_contracter_home_to_contracter_view_manpower",
                user_details_string
                );
        startActivity(contracter_view_manpower);
    }


    public void contracter_add_manpower(View V) {
        Intent contracter_add_manpower =
                new Intent(contracter_home.this, contracter_add_worker.class);
        contracter_add_manpower.putExtra("bundle_data_contracter_home_to_contracter_add_manpower",
                user_details_string
                );
        startActivity(contracter_add_manpower);
    }

    @Override
    public void handle_response(String response) {

        Log.i("handle_res ctr home", response.toString());

        /*

            {
                p1 : {

                },

                p2 : {

                }

            }
         */


        try{

            JSONObject projects_details = new JSONObject(response);

            JSONArray project_ids = projects_details.names();

            for(int i = 0; i < project_ids.length(); i++) {

                String project_id = (String)project_ids.get(i);

                JSONObject project_detail = projects_details.getJSONObject(
                        (String)project_id);

                project p = new project();

                p.project_name =        project_detail.getString("project_name");
                p.project_id =          project_id;
                p.project_start_date=   project_detail.getString("project_start_date");
                p.project_end_date =    project_detail.getString("project_end_date");

                projects.add(p);
                project_adapter.notifyDataSetChanged();

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

    // TODO replace it with bundle user_id
    // contracter id of cona
    String user_id;



    RecyclerView project_rv;
    project_adapter project_adapter;
    LinearLayoutManager linearLayoutManager;
    private List<project> projects;

    private void init() {
        project_rv = (RecyclerView) findViewById(R.id.hc_project_list);
        linearLayoutManager = (new LinearLayoutManager(this));
        projects = new ArrayList<>();

        project_adapter = new project_adapter(projects);
        project_rv.setAdapter(project_adapter);
        project_rv.setLayoutManager(linearLayoutManager);


        // TODO : add touch listener
        project_rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent contracter_home_to_contracter_project =
                                        new Intent(
                                        contracter_home.this,
                                        contracter_project.class);


                                // TODO add bundle data
                                           // get project details
                                String project_id = ((TextView) view.findViewById(R.id.rl_project_id))
                                        .getText().toString();

                                String project_name = ((TextView) view.findViewById(R.id.rl_project_name))
                                        .getText().toString();

                                String project_start_date = ((TextView) view.findViewById(R.id.rl_project_start_date))
                                        .getText().toString();


                                String project_end_date = ((TextView) view.findViewById(R.id.rl_project_end_date))
                                        .getText().toString();



                                Log.i("info from admin_home ",
                                        "project_id:"+project_id+","+
                                        "project_name:"+project_name+","+
                                        "project_start_date:"+project_start_date+","+
                                        "project_end_date:"+project_end_date+","+
                                                "user_id:"+user_id
                                        );

                                Toast.makeText(contracter_home.this, "project id " + project_id, Toast.LENGTH_SHORT).show();

                                contracter_home_to_contracter_project
                                        .putExtra("bundle_data_ctr_home_to_ctr_project",
                                        user_details_string+","+
                                        "project_id:"+project_id+","+
                                        "project_name:"+project_name+","+
                                        "project_start_date:"+project_start_date+","+
                                        "project_end_date:"+project_end_date+","+
                                                "user_id:"+user_id
                                );

                                startActivity(contracter_home_to_contracter_project );
                            }
                        }));



    }



    TextView email, name, aadhar_id;
    Bundle user_details;
    String user_details_string;
    JSONObject user_details_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contracter_home);
        init();


        // => states
                // handling state
        user_details = getIntent().getExtras();

        if(user_details != null) {

             Toast.makeText(this, " user details parsed", Toast.LENGTH_SHORT).show();
            user_details_string = user_details.getString("bundle_data");

        }else{
            Toast.makeText(this, "null user details", Toast.LENGTH_SHORT).show();
        }


            name = ((TextView) findViewById(R.id.contracter_home_contracter_name));
            email = ((TextView) findViewById(R.id.contracter_home_contracter_email));
            aadhar_id = ((TextView) findViewById(R.id.contracter_home_contracter_user_aadhar));


        user_details_json = s.string_to_json(user_details_string);

        try {

            user_id = user_details_json.getString("user_id");

            name.setText(user_details_json.getString("name"));
            email.setText(user_details_json.getString("user_email"));
            aadhar_id.setText(user_details_json.getString("aadhar_id"));


        }catch (JSONException e) {
            Log.e("json ex", "inside handle bundle function");
        }




        // bind ui to state



        // => fetch data
        fill_data();
    }


    // TODO : fetch data
    private void fill_data() {
        projects.clear();
        make_request();

    }
}
