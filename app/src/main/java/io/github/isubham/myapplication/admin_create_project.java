package io.github.isubham.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import io.github.isubham.myapplication.utility.s;
import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.volley_wrapper;

public class admin_create_project extends volley_wrapper {

    // TODO : replace with actual email id
    // temporary admin user_id
    String user_id = "14";
    static int ui_flag = 0;

    ProgressDialog progressDialog;

    // ui components
    static EditText project_name, project_start_date, project_end_date,
    project_location ;


    Bundle bundle_data;
    String bundle_data_string;
    JSONObject bundle_jsonobject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_create_project);


        // state

        // get user_id
        bundle_data = getIntent().getExtras();

        if(bundle_data != null){
            bundle_data_string = bundle_data
                    .getString("bundle_data_admin_home_to_admin_create_project");

            // get json
            bundle_jsonobject = s.string_to_json(bundle_data_string);

            // set user_id
            try{
                user_id = bundle_jsonobject.getString("user_id");
                Toast.makeText(this, "fetched user_id "+user_id, Toast.LENGTH_SHORT).show();
            }
            catch (JSONException e){
                Log.e("json_ex", "inside getting user_id");
            }
        }else{
            Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show();
        }

        // end of state
        init();
    }

    private void init() {
        project_name = (EditText) findViewById(R.id.a_c_p_project_name);
        project_start_date = (EditText) findViewById(R.id.a_c_p_project_start_date);
        project_end_date = (EditText) findViewById(R.id.a_c_p_project_end_date);
        project_location = (EditText) findViewById(R.id.a_c_p_project_location);

        progressDialog.setTitle("Creating Project");

    }



    public void select_project_start_date(View V){
        ui_flag = 1;
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(),getString(R.string.datepicker));

    }
    public void select_project_end_date(View V){
        ui_flag = 2;
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(),getString(R.string.datepicker));
    }

    @Override
    public Map makeParams() {
        Map<String, String> project_data = new HashMap<>();

        project_data.put("project_name",          s.text(project_name));
        project_data.put("project_start_date",    s.text(project_start_date));
        project_data.put("project_end_date",      s.text(project_end_date));
        project_data.put("user_id",               user_id);
        project_data.put("project_location",               user_id);
        project_data.put("module",         data_wrapper.QMODULE_PROJECT);
        project_data.put("query_type",     data_wrapper.QTYPE_I);
        project_data.put("query",          data_wrapper.Q_CREATE_PROJECT);

        Log.i("map_create_project", project_data.toString());

        return project_data;

    }

    @Override
    public void handle_response(String response) {

        JSONObject user_details;

        progressDialog.hide();

        Log.i("handle_response ", "admin_create_project" +response);

        try {
            user_details = new JSONObject(response);

            // if project not created
            if (user_details.getString("status") == "-1") {
                Log.i("TODO ", "project not created");
                Toast.makeText(this, "Project not Created", Toast.LENGTH_SHORT).show();
            }
            else {
                Log.i("TODO ", "project created");
                Toast.makeText(this, "Project Created", Toast.LENGTH_SHORT).show();
            }

        }
        catch (JSONException e){
            Log.e("JSONException", "inside handle response");
        }
    }

    @Override
    public void make_volley_request(StringRequest stringRequest) {

        Volley.newRequestQueue(this).add(stringRequest);
    }

    public static class DatePickerFragment extends DialogFragment
implements DatePickerDialog.OnDateSetListener
{
    public String dateTime;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = day + "/" + month + "/" + year;
        // Do something with the date chosen by the user
        if (ui_flag == 1){
            project_start_date.setText(date);
        }
        else if(ui_flag == 2){
            project_end_date.setText(date);
        }
    }

}
    public void create_new_project(View V) {
        // TODO : create new project
        make_request();
        progressDialog.show();
    }
}
