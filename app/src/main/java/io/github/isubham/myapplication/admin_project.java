package io.github.isubham.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

import io.github.isubham.myapplication.adapters.shift_adapter;
import io.github.isubham.myapplication.model.shift;
import io.github.isubham.myapplication.utility.RecyclerItemClickListener;
import io.github.isubham.myapplication.utility.data_wrapper;

public class admin_project extends AppCompatActivity {
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
    RecyclerView shift_rv;
    shift_adapter shift_adapter;
    LinearLayoutManager linearLayoutManager;
    private List<shift> shifts;

    // TODO : take it from intent
    String project_id;


    private void init() {
        shift_rv = (RecyclerView) findViewById(R.id.a_p_shift_recycleview);
        linearLayoutManager = (new LinearLayoutManager(this));
        shifts = new ArrayList<>();

        shift_adapter = new shift_adapter(shifts);
        shift_rv.setAdapter(shift_adapter);
        shift_rv.setLayoutManager(linearLayoutManager);


        // TODO : add real data
        // fill_data();


        shifts.add(new shift("1", "done some changes", "12 march 2013", "11", "lala"));

        shifts.add(new shift("1", "done some changes", "12 march 2013", "11", "lala"));

        shifts.add(new shift("1", "done some changes", "12 march 2013", "11", "lala"));

        shifts.add(new shift("1", "done some changes", "12 march 2013", "11", "lala"));

        shifts.add(new shift("1", "done some changes", "12 march 2013", "11", "lala"));

        shifts.add(new shift("1", "done some changes", "12 march 2013", "11", "lala"));

        shifts.add(new shift("1", "done some changes", "12 march 2013", "11", "lala"));

        shifts.add(new shift("1", "done some changes", "12 march 2013", "11", "lala"));

        shifts.add(new shift("1", "done some changes", "12 march 2013", "11", "lala"));

        shifts.add(new shift("1", "done some changes", "12 march 2013", "11", "lala"));

        shifts.add(new shift("1", "done some changes", "12 march 2013", "11", "lala"));




        // TODO : add touch listener
        shift_rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(admin_project.this,
                                        ((TextView)view.findViewById(R.id.rl_s_shift_id)).getText(), Toast.LENGTH_SHORT).show();
                            }
                        }));



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fill_data();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_project);
        init();
    }

    public void effect_response(JSONArray response) {

                        try{

                            // Toast.makeText(home_admin.this, response.toString(), Toast.LENGTH_SHORT).show();

                        for(int i = 0; i < response.length(); i++) {

                            JSONObject jsonObject = response.getJSONObject(i);
                            shift p = new shift();

                            p.shift_id = jsonObject.getString("shift_id");
                            p.shift_description = jsonObject.getString("shift_description");
                            p.shift_datetime= jsonObject.getString("shift_datetime");
                            p.contractor_id = jsonObject.getString("contracter_id");

                            shifts.add(p);

                            shift_adapter.notifyDataSetChanged();

                        }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
    }




    private void fill_data(){

        // TODO : fetch data
        shifts.clear();

        String url = data_wrapper.BASE_URL_TEST;

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
                return build_shift_map();
            }
        };


        Volley.newRequestQueue(this).add(stringRequest);

    }

    public Map build_shift_map(){

        Map <String, String> shift_map  = new HashMap<>();

        shift_map.put("project_id", data_wrapper.TEMP_PROJECT_ID);

        shift_map.put("module", data_wrapper.QMODULE_SHIFT);
        shift_map.put("query_type", data_wrapper.QTYPE_O);
        shift_map.put("query", data_wrapper.Q_READ_SHIFT);

        return shift_map;

    }

}
