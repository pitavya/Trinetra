package io.github.isubham.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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

public class admin_create_project extends AppCompatActivity {

    // TODO : replace with actual email id
    // temporary admin user_id
    String user_id = "31";
    static int ui_flag = 0;

    // ui components
    static EditText project_name, project_start_date, project_end_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_create_project);
        init();
    }

    private void init() {
        project_name = (EditText) findViewById(R.id.a_c_p_project_name);
        project_start_date = (EditText) findViewById(R.id.a_c_p_project_start_date);
        project_end_date = (EditText) findViewById(R.id.a_c_p_project_end_date);
    }


    public Map<String, String> build_user_data() {

        Map<String, String> user_data = new HashMap<>();

        user_data.put("project_name",          s.text(project_name));
        user_data.put("project_start_date",    s.text(project_start_date));
        user_data.put("project_end_date",      s.text(project_end_date));
        user_data.put("user_id",               user_id);

        // flags for query
        user_data.put("module",         data_wrapper.QMODULE_PROJECT);
        user_data.put("query_type",     data_wrapper.QTYPE_I);
        user_data.put("query",          data_wrapper.Q_CREATE_PROJECT);

        return user_data;

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


        String url = data_wrapper.BASE_URL_TEST;


        //

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject res ;
                        try{
                            res = new JSONObject(response);
                            String project_id = res.get("status").toString();

                            Toast.makeText(admin_create_project.this,
                                    "project created project_id " + project_id , Toast.LENGTH_SHORT).show();

                        } catch (JSONException e)
                        {
                            Log.e("ERROR 86 json", e.getMessage());
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(admin_create_project.this, "Error in POST", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ){

            @Override
            protected Map<String, String> getParams() {

                return build_user_data();

            }
        };

        Volley.newRequestQueue(this).add(stringRequest);

        //


    }
}
