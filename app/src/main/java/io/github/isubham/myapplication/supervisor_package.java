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

import io.github.isubham.myapplication.adapters.package_adapter;
import io.github.isubham.myapplication.adapters.shift_adapter;
import io.github.isubham.myapplication.model.package_;
import io.github.isubham.myapplication.model.shift;
import io.github.isubham.myapplication.utility.RecyclerItemClickListener;
import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.volley_wrapper;
import io.github.isubham.myapplication.utility.s;


public class supervisor_package extends volley_wrapper {


    // TODO replace it with bundle user_id
    // supervisor id of supa
    String user_id, project_id;

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
                                Intent sup_pkg_to_sup_auth =
                                        new Intent(supervisor_package.this, supervisor_authentication.class);

                                TextView shift_id = view.findViewById(R.id.rl_shift_shift_id);
                                String shift_id_string = shift_id.getText().toString();
                                sup_pkg_to_sup_auth.putExtra("bundle_data_sup_pkg_to_sup_auth",

                                        bundle_string+","+
                                                "shift_id:"+shift_id_string

                                        );
                                startActivity(sup_pkg_to_sup_auth);
                            }
                        }));



    }

    TextView package_name, package_start_date, package_end_date, package_id;
    String package_name_string, package_start_date_string, package_end_date_string, package_id_string;

    Bundle bundle;
    String bundle_string;
    JSONObject bundle_jsonobject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_package);

        package_name = (TextView) findViewById(R.id.supervisor_package_package_name);
        package_id = (TextView) findViewById(R.id.supervisor_package_package_id);
        package_start_date = (TextView) findViewById(R.id.supervisor_package_package_start_date);
        package_end_date = (TextView) findViewById(R.id.supervisor_package_package_end_date);


        // model
        bundle = getIntent().getExtras();
        if (bundle != null) {

            // => get string, json and assign states

            bundle_string = bundle.getString("bundle_data_sup_project_to_sup_package");
            bundle_jsonobject = s.string_to_json(bundle_string);

            // => parsing state from model
            try {
                package_id_string =
                        bundle_jsonobject.getString("package_id");
                package_name_string =
                        bundle_jsonobject.getString("package_name");
                package_start_date_string =
                        bundle_jsonobject.getString("package_start_date");

                package_end_date_string =
                        bundle_jsonobject.getString("package_end_date");

                user_id  = bundle_jsonobject.getString("user_id");

                project_id  = bundle_jsonobject.getString("project_id");

            } catch (JSONException e) {
                Log.e("contracter_package", "json exception in the project");
            }

            // bind state to ui
            package_id.setText(package_id_string);
            package_name.setText(package_name_string);
            package_start_date.setText(package_start_date_string);
            package_end_date.setText(package_end_date_string);

            init();
            fill_data();
        }
    }


    // start shift button click event
    public void start_supervisor_shift(View V){
        // pass intent with user_id, project_id, package_id

        Intent supervisor_pacakge_to_supervisor_shift = new Intent(supervisor_package.this,
                supervisor_shift.class);

        // add data
        supervisor_pacakge_to_supervisor_shift.putExtra("bundle_data_sup_pkg_to_sup_shift",
                bundle_string
                );
    }

    @Override
    public Map makeParams() {

            Map<String, String> get_auth_list_param = new HashMap<>();

            get_auth_list_param.put("package_id", package_id_string);
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
