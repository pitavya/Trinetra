package io.github.isubham.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.s;
import io.github.isubham.myapplication.utility.volley_wrapper;


public class admin_create_package extends volley_wrapper {

    // TODO : replace with actual email id
    // temporary admin user_id
    String user_id;
    String project_id;

    static int ui_flag = 0;

    // ui components
    static EditText package_name, package_start_date, package_end_date,
    package_location ;

    Bundle bundle;
    String bundle_data;
    JSONObject bundle_json_object;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_create_package);

        // => states
        bundle = getIntent().getExtras();

        if (bundle != null){
            bundle_data = bundle.getString("bundle_data_admin_project_to_admin_create_package");

            // get bundle user_id and project_id

            try{
                bundle_json_object = s.string_to_json(bundle_data);

                project_id = bundle_json_object.getString("project_id");
                user_id    =  bundle_json_object.getString("user_id");

                Toast.makeText(this, "user_id project_id set",
                        Toast.LENGTH_SHORT).show();


            }catch (JSONException e){
                Log.e("admin_create_package", "json exception");
            }
        }


        progressDialog = new ProgressDialog(admin_create_package.this);
        progressDialog.setTitle("Creating Package");

        // => end of states
        init();
    }

    private void init() {
        package_name = (EditText) findViewById(R.id.a_c_p_package_name);
        package_start_date = (EditText) findViewById(R.id.a_c_p_package_start_date);
        package_end_date = (EditText) findViewById(R.id.a_c_p_package_end_date);
        package_location = (EditText) findViewById(R.id.a_c_p_package_location);

    }


    public void select_package_start_date(View V){
        ui_flag = 1;
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(),getString(R.string.datepicker));

    }

    public void select_package_end_date(View V){
        ui_flag = 2;
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(),getString(R.string.datepicker));
    }

    @Override
    public Map makeParams() {
        Map<String, String> project_data = new HashMap<>();

        project_data.put("package_name",          s.text(package_name));
        project_data.put("package_start_date",    s.text(package_start_date));
        project_data.put("package_end_date",      s.text(package_end_date));
        project_data.put("package_location",      s.text(package_location));
        project_data.put("package_creater_id",               user_id);
        project_data.put("project_id",               project_id);

        project_data.put("module",         data_wrapper.QMODULE_PACKAGE);
        project_data.put("query_type",     data_wrapper.QTYPE_I);
        project_data.put("query",          data_wrapper.Q_CREATE_PACKAGE);

        Log.i("map_create_package", project_data.toString());

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
                Log.i("TODO ", "package not created");
                Toast.makeText(this, "package created", Toast.LENGTH_SHORT).show();
            }
            else {
                Log.i("TODO ", "package created");
                Toast.makeText(this, "package created", Toast.LENGTH_SHORT).show();
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
            package_start_date.setText(date);
        }
        else if(ui_flag == 2){
            package_end_date.setText(date);
        }
    }

}
    public void create_new_package(View V) {
        // TODO : create new project
        make_request();
        progressDialog.show();
    }
}
