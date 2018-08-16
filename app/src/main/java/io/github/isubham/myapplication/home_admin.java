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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.github.isubham.myapplication.adapters.project_adapter;
import io.github.isubham.myapplication.model.project;
import io.github.isubham.myapplication.utility.RecyclerItemClickListener;
import io.github.isubham.myapplication.utility.data_wrapper;

public class home_admin extends AppCompatActivity {

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

    String user_id = data_wrapper.TEMP_ADMIN_ID;

    public void to_add_new_project(View V){

        startActivity(new Intent(home_admin.this, admin_create_project.class));

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


        // TODO : add touch listener
        project_rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(home_admin.this,
                                        ((TextView)view.findViewById(R.id.rl_project_id)).getText(), Toast.LENGTH_SHORT).show();
                            }
                        }));



    }


    public Map<String, String> build_project_map() {

                Map<String, String> get_project_list_param = new HashMap<>();
                get_project_list_param.put("user_id", user_id);

                get_project_list_param.put("module", data_wrapper.QMODULE_PROJECT);
                get_project_list_param.put("query_type", data_wrapper.QTYPE_O);
                get_project_list_param.put("query", data_wrapper.Q_READ_PROJECTS);

                return get_project_list_param;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_admin);
        init();
        fill_data();
    }


    public void effect_response(JSONArray response) {

                        try{

                            // Toast.makeText(home_admin.this, response.toString(), Toast.LENGTH_SHORT).show();


                        for(int i = 0; i < response.length(); i++) {

                            JSONObject jsonObject = response.getJSONObject(i);
                            project p = new project();

                            p.project_name = jsonObject.getString("project_name");
                            p.project_id = jsonObject.getString("project_id");
                            p.project_start_date= jsonObject.getString("project_start_date");
                            p.project_end_date = jsonObject.getString("project_end_date");

                            projects.add(p);

                            project_adapter.notifyDataSetChanged();

                        }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
    }



    private void fill_data(){

        // TODO : fetch data
        projects.clear();

        String url = data_wrapper.BASE_URL_LOCAL;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            effect_response(new JSONArray(response));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return build_project_map();
            }
        };


        Volley.newRequestQueue(this).add(stringRequest);

    }


}
