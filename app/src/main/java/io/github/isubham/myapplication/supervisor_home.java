package io.github.isubham.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class supervisor_home extends volley_wrapper {

    @Override
    public Map makeParams() {

            Map<String, String> get_project_list_param = new HashMap<>();
            get_project_list_param.put("user_added_id", user_id);
            get_project_list_param.put("module", data_wrapper.QMODULE_PROJECT);
            get_project_list_param.put("query_type", data_wrapper.QTYPE_O);
            get_project_list_param.put("query", data_wrapper.Q_READ_PROJECTS);

            return get_project_list_param;

    }

    @Override
    public void handle_response(String response) {

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
    // contracter id of supa
    String user_id = "20";

    RecyclerView project_rv;
    project_adapter project_adapter;
    LinearLayoutManager linearLayoutManager;
    private List<project> projects;

    private void init() {
        project_rv = (RecyclerView) findViewById(R.id.hs_project_list);
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

                                // TODO handle click behaviour
                                startActivity(new Intent(supervisor_home.this,
                                        supervisor_project.class));
                            }
                        }));



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_home);
        init();
        fill_data();
    }


    // TODO : fetch data
    private void fill_data() {
        projects.clear();
        make_request();

    }
}
