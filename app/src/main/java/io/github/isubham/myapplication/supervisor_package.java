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

import io.github.isubham.myapplication.adapters.package_adapter;
import io.github.isubham.myapplication.adapters.shift_adapter;
import io.github.isubham.myapplication.model.package_;
import io.github.isubham.myapplication.model.shift;
import io.github.isubham.myapplication.utility.RecyclerItemClickListener;
import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.volley_wrapper;

public class supervisor_package extends volley_wrapper {


    // TODO replace it with bundle user_id
    // supervisor id of supa
    String user_id = "20", shift_id = "7", package_id = "5";

    RecyclerView shift_rv;
    shift_adapter shift_adapter;
    LinearLayoutManager linearLayoutManager;
    private List<shift> shifts;

    private void init() {
        shift_rv = (RecyclerView) findViewById(R.id.s_pkg_shift_list);
        linearLayoutManager = (new LinearLayoutManager(this));
        shifts = new ArrayList<>();

        shift_adapter = new shift_adapter(shifts);
        shift_rv.setAdapter(shift_adapter);
        shift_rv.setLayoutManager(linearLayoutManager);


        // TODO : add touch listener
        shift_rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                // TODO add aditional data
                                startActivity(new Intent(
                                        supervisor_package.this,
                                        supervisor_shift.class));
                            }
                        }));



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_package);
        init();
        fill_data();
    }

    @Override
    public Map makeParams() {

            Map<String, String> get_auth_list_param = new HashMap<>();

            get_auth_list_param.put("package_id", package_id);
            get_auth_list_param.put("user_id", user_id);

            get_auth_list_param.put("query", "read_shift_by_package_by_supervisor");
            get_auth_list_param.put("module", "shift");
            get_auth_list_param.put("query_type", "o");



        Log.i("makeparain ctr home", get_auth_list_param .toString());

            return get_auth_list_param;

    }


    @Override
    public void handle_response(String response) {

        Log.i("handle_res ctr home", response.toString());

        /*

            {
                shift_id : {

                },

                shift_id2 : {

                }

            }
         */


        try{

            JSONObject shifts_details = new JSONObject(response);

            JSONArray shift_ids = shifts_details.names();

            for(int i = 0; i < shift_ids.length(); i++) {

                String shift_id = (String)shift_ids.get(i);

                JSONObject shift_detail = shifts_details.getJSONObject(
                        (String)shift_id);

                shift shift = new shift();

                shift.shift_id =     shift_id;
                shift.shift_datetime = shift_detail.getString("shift_date");
                shift.shift_type=   shift_detail.getString("shift_type");

                shifts.add(shift);
                shift_adapter.notifyDataSetChanged();

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
        shifts.clear();
        make_request();
    }
}
