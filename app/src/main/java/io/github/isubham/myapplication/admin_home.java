package io.github.isubham.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.isubham.myapplication.adapters.project_adapter;
import io.github.isubham.myapplication.model.project;
import io.github.isubham.myapplication.utility.RecyclerItemClickListener;
import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.s;
import io.github.isubham.myapplication.utility.volley_wrapper;

public class admin_home extends volley_wrapper {

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
    String user_id = "";

    public void to_add_new_project(View V){

        Intent to_add_new_project = new Intent(admin_home.this, admin_create_project.class);
        to_add_new_project.putExtra("bundle_data_admin_home_to_admin_create_project", user_details_string);
        startActivity(to_add_new_project);

    }


    RecyclerView project_rv;
    project_adapter project_adapter;
    LinearLayoutManager linearLayoutManager;
    private List<project> projects;

    private void init() {
        project_rv = (RecyclerView) findViewById(R.id.ha_project_list);
        linearLayoutManager = (new LinearLayoutManager(this));
        projects = new ArrayList<>();

        project_adapter = new project_adapter(projects);
        project_rv.setAdapter(project_adapter);
        project_rv.setLayoutManager(linearLayoutManager);

        values = new ArrayList<>();
        textViews = new ArrayList<>();


        // TODO : add touch listener
        project_rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(admin_home.this,
                                        ((TextView)view.findViewById(R.id.rl_project_id)).getText(),
                                        Toast.LENGTH_SHORT).show();
                                // TODO : add proper intent data
                                Intent to_admin_project = new Intent(admin_home.this,
                                        admin_project.class);

                                // get project details
                                String project_id = ((TextView) view.findViewById(R.id.rl_project_id))
                                        .getText().toString();

                                String project_name = ((TextView) view.findViewById(R.id.rl_project_name))
                                        .getText().toString();

                                String project_start_date = ((TextView) view.findViewById(R.id.rl_project_start_date))
                                        .getText().toString();


                                String project_end_date = ((TextView) view.findViewById(R.id.rl_project_end_date))
                                        .getText().toString();


                               try{

                                Log.i("info from admin_home ",
                                        "project_id:"+project_id+","+
                                        "project_name:"+project_name+","+
                                        "project_start_date:"+project_start_date+","+
                                        "project_end_date:"+project_end_date+","+
                                                "user_id:"+user_details_json.getString("user_id")
                                        );


                                to_admin_project.putExtra("bundle_data_admin_home_to_admin_project",
                                        user_details_string + ","+
                                        "project_id:"+project_id+","+
                                        "project_name:"+project_name+","+
                                        "project_start_date:"+project_start_date+","+
                                        "project_end_date:"+project_end_date+","+
                                                "user_id:"+user_details_json.getString("user_id")
                                );

                               }catch (JSONException e){
                                   Log.e("json ex", "inside click in admin home");
                               }

                               startActivity(to_admin_project);
                            }
                        }));



    }

    ArrayList<String> values;
    ArrayList<TextView> textViews;


    TextView email, name, aadhar_id;

    public void handle_bundle_data(String bundle_data) {

    }

    Bundle user_details;
    String user_details_string;
    JSONObject user_details_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
        init();


        // handling state
        user_details = getIntent().getExtras();

        if(user_details != null) {

             Toast.makeText(this, " user details parsed", Toast.LENGTH_SHORT).show();
            user_details_string = user_details.getString("bundle_data");

        }else{
            Toast.makeText(this, "null user details", Toast.LENGTH_SHORT).show();
        }


            name = ((TextView) findViewById(R.id.admin_home_admin_name));
            email = ((TextView) findViewById(R.id.admin_home_admin_email));
            aadhar_id = ((TextView) findViewById(R.id.admin_home_admin_aadhar));

        user_details_json = s.string_to_json(user_details_string);

        try {

            name.setText(user_details_json.getString("name"));
            email.setText(user_details_json.getString("user_email"));
            aadhar_id.setText(user_details_json.getString("aadhar_id"));

            user_id = user_details_json.getString("user_id");


        }catch (JSONException e) {
            Log.e("json ex", "inside handle bundle function");
        }

        handle_bundle_data(user_details_string);
        // end of handling state
        fill_data();
    }


    // TODO : fetch data
    private void fill_data() {
        projects.clear();
        make_request();

    }
}
