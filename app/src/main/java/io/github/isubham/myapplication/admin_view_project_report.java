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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import io.github.isubham.myapplication.utility.s;
import io.github.isubham.myapplication.utility.volley_wrapper;

public class admin_view_project_report extends volley_wrapper {

//    datwise attendence and authentication details

    String package_id_string, shift_date_string;
    static Button admin_view_report_pick_date;

    ProgressDialog progressDialog;
    TextView project_report_string;
    Bundle bundle;
    String bundle_data;
    JSONObject bundle_json_object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_project_report);

        progressDialog = new ProgressDialog(admin_view_project_report.this);
        progressDialog.setTitle("Fetching Report");
        admin_view_report_pick_date = (Button)
                findViewById(R.id.admin_view_report_pick_date );

        project_report_string = (TextView) findViewById(R.id.project_report_string);

        // => states
        bundle = getIntent().getExtras();

        if (bundle != null){
            bundle_data = bundle.getString("admin_package_to_admin_project_report");

            // get bundle user_id and project_id

            try{
                bundle_json_object = s.string_to_json(bundle_data);

                Toast.makeText(this, "states fetched",
                        Toast.LENGTH_SHORT).show();

                package_id_string = bundle_json_object
                        .getString("package_id");

            }catch (JSONException e){
                Log.e("admin_view_contracter", "json exception");
            }
        }


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

        String year_string,
                month_string = String.valueOf(month),
                day_string   = String.valueOf(day);
        if (month < 10 ) {
            month_string = "0" + (month + 1);
        }
        if (day < 10 ) {
            day_string = "0" + day;
        }

        String date ;
        date = year + "-" + month_string + "-" + day_string;
        // Do something with the date chosen by the user
        picked_date  = date;
        admin_view_report_pick_date.setText(picked_date);

    }

}

static String picked_date;

    public void fetch_report(View V){
        make_request();
    }


    public void pick_date(View V){
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(),getString(R.string.datepicker));
    }

    @Override
    public Map makeParams() {
        Map<String, String> project_report_param = new HashMap<>();

        project_report_param.put("package_id", package_id_string);
        project_report_param.put("shift_date", picked_date);

        project_report_param.put("module", "shift");
        project_report_param.put("query", "project_report_per_day_per_package");
        project_report_param.put("query_type", "o");

        Log.e("makeparams", project_report_param.toString());

        return project_report_param;
    }

    @Override
    public void handle_response(String response) {

        progressDialog.hide();


        /*

        {
            "status" : {
                "attendence" : {

                },

                "auth" : {
                    "auth_id" : {

                           "auth_status" : "0"

                     },
                    "auth_id" : {

                           "auth_status" : "0"

                     },
        }


         */

        JSONObject project_report, attendence, authentication;


        total_auth = 0;
        verified_auth = 0;
        try{
            project_report = new JSONObject(response);
            authentication = (project_report.getJSONObject("status")).getJSONObject("auth");
            JSONArray auth_keys = authentication.names();
            for(int i =0; i < auth_keys.length(); i++){
                JSONObject auth = authentication
                        .getJSONObject((String)auth_keys.get(i));

                Log.e("auth", auth.toString());
                total_auth += 1;
                String auth_status = auth.getString("auth_status");
                Log.e("auth_Status", auth_status);

                if(auth_status.equals("1"))
                    verified_auth += 1;

            }


            project_report_string.setText(verified_auth + "/" + total_auth);


        }catch (JSONException e){
            Log.e("json ex", "exception in handle_Response");
        }

    }

    int total_auth;
    int verified_auth;
    @Override
    public void make_volley_request(StringRequest stringRequest) {
        Volley.newRequestQueue(admin_view_project_report.this).add(stringRequest);
    }
}
