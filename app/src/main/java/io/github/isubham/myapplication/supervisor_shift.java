package io.github.isubham.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import io.github.isubham.myapplication.utility.volley_wrapper;

import io.github.isubham.myapplication.utility.s;

public class supervisor_shift extends volley_wrapper {

    public void open_shift_auth_list(View V){
        startActivity(new Intent(supervisor_shift.this,
                supervisor_authentication.class));
    }

    // state =>
    // TODO stats from previous activities

    String shift_id, user_id  , package_id, project_id;
    // => TODO add script to get contracter_id


    create_shift_volley create_shift_volley;
    String shift_type = "1", shift_date = "11-03-2018", shift_attendence ;
    SortedSet<String> attendence_set;

    Bundle bundle;
    String bundle_string;
    JSONObject bundle_jsonobject;
    LinearLayout worker_checkbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_shift);
        worker_checkbox = (LinearLayout) findViewById(R.id.worker_checkbox);
        make_request();

        attendence_set = new TreeSet<>();
        // => bundle management
        bundle = getIntent().getExtras();
        if (bundle != null) {

            // => get string, json and assign states
            bundle_string = bundle.getString("bundle_data_sup_pkg_to_sup_shift");
            bundle_jsonobject = s.string_to_json(bundle_string);

            // => parsing state from model
            try {
                // assign states to user_id, project_id, package_id, contracter_id
                user_id = bundle_jsonobject.getString("user_id");
                project_id = bundle_jsonobject.getString("project_id");
                package_id = bundle_jsonobject.getString("package_id");
            } catch (JSONException e) {

            }

            // => end of bundle management
        }
    }


    // TODO get this from supervisor_shift

    // TODO shift_date => from system
    // TODO shift_type => from checkbox

    // list of worker => checkbox of workername in worker_checkbox
    public void add_checkbox_of_worker(JSONObject workers){
        /*
        * {
        *   w_id : {
        *
        *   },
        *
        *   w_id2 : {
        *
        *   }
        *
        * */
        // TODO iterate in response from worker in pacakge under contracter

        try{


            JSONArray worker_ids = workers.names();

            for(int i = 0; i < worker_ids.length(); i++) {

                String worker_id = (String)worker_ids.get(i);

                JSONObject worker_detail = workers.getJSONObject(worker_id);

                CheckBox checkBox = new CheckBox(this);
                checkBox.setId(Integer.parseInt(worker_id));
                checkBox.setText(worker_detail.getString("worker_name"));
                worker_checkbox.addView(checkBox);

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_to_attendence_if_checked(v);
                    }
                });

                /*
                {

                    @Override
                    public void onClick(View v) {

                    }
                });

                */
                /* TODO convert into recycleview because worker name can have
                    replications
                worker p = new worker();
                p.=  project_detail.getString("project_name");
                p.=  project_id;
                p.=  project_detail.getString("project_start_date");
                p.=  project_detail.getString("project_end_date");
                   */
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


    public void add_to_attendence_if_checked(View v){

        Toast.makeText(supervisor_shift.this, v.getId() + "added",
                Toast.LENGTH_SHORT).show();
        boolean checked = ((CheckBox) v).isChecked();
        if(checked){
            attendence_set.add(Integer.toString(v.getId()));
        }
        else{
             attendence_set.remove(Integer.toString(v.getId()));
        }

    }


    public void get_shift_type(View V){
        switch (V.getId()){
            case R.id.shift_type_1 : shift_type = "1";break;
            case R.id.shift_type_2 : shift_type = "2";break;
            case R.id.shift_type_3 : shift_type = "3";break;
            default: shift_type = "1";
        }

    }
    @Override
    public Map makeParams() {


        // query params to fetch worker list
        Map new_attendence = new HashMap<String, String>();
        // contracter id
        new_attendence.put("module", "worker_package");
        new_attendence.put("query", "read_workers_package_per_contracter");
        new_attendence.put("query_type", "o");
        new_attendence.put("user_added_id", user_id);
        new_attendence.put("package_id", package_id);

        return new_attendence;


    }

    @Override
    public void handle_response(String response) {
        try{
            Log.i("handle_response ", response);
            JSONObject worker_list = new JSONObject(response);
            // create checklist
            add_checkbox_of_worker(worker_list);

        }catch (JSONException e){
            Log.e("JSON Ex", "exception in json of the worker list");
        }
    }

    public class create_shift_volley extends volley_wrapper{

             @Override
            public Map makeParams() {

                //
                StringBuilder sorted_attendence_string = new StringBuilder();
                for(String i : attendence_set){
                    sorted_attendence_string.append(i + " ");
                }
                String final_sorted_string = sorted_attendence_string.toString();

                Log.e("attendence", final_sorted_string);

                Map new_shift = new HashMap<String, String>();

                new_shift.put("shift_date", shift_date);
                new_shift.put("shift_attendence", final_sorted_string);
                // user_id of supervisor
                new_shift.put("user_id", user_id);
                new_shift.put("project_id", project_id);
                new_shift.put("package_id", package_id);
                new_shift.put("shift_type", shift_type);


                new_shift.put("module", "shift");
                new_shift.put("query", "create_shift");
                new_shift.put("query_type", "i");


                Log.e("make param", new_shift.toString());

                return new_shift;
            }

            @Override
            public void handle_response(String response) {
                // if attendence is fetched or not
                try{
                    JSONObject status_response =
                            new JSONObject(response);

                    String status = status_response.getString("status");
                    if(status == "-1") {
                         // Toast.makeText(getApplicationContext(), "FAILED TO CREATE SHIFT", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        // Toast.makeText(getApplicationContext(), "ATTENDENCE ADDED", Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    Log.e("attendence_in_shift", "error on handle response");
                }
            }
            public void make_volley_request(StringRequest stringRequest) {
                Volley.newRequestQueue(supervisor_shift.this).add(stringRequest);
            }

    }

    public void create_shift(View V){

        create_shift_volley = new create_shift_volley();
        create_shift_volley.make_request();

    }











}
