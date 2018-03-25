package io.github.isubham.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.s;
import io.github.isubham.myapplication.utility.volley_wrapper;

public class add_user_project extends volley_wrapper {

    // ui and data

    EditText user_email;

    /* TODO : get them from real db */
    String project_id  = "12";
    String user_id  = data_wrapper.TEMP_ADMIN_ID;

    public void init() {
        user_email = (EditText) findViewById(R.id.a_u_p_email);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_project);
        init();
    }


    @Override
    public void handle_response(JSONObject response){


        // flag 1, 0 for added user or not

        // Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        try{
            String id = response.get("status").toString();

            if (! id.equals("0")) {
                Toast.makeText(add_user_project.this,
                        "Add user user_project: " + id , Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(add_user_project.this,
                        "Error adding user ", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e)
        {
            Log.e("ERROR 214 json", e.getMessage());
        }

    }



    public void add_user(View V) {
        make_request();
    }

    public void back_to(View V){
        // TODO :
    }


    @Override
    public Map makeParams() {

        Map<String, String> user_data = new HashMap<>();


        user_data.put("email", s.text(user_email));
        user_data.put("project_id", project_id);
        user_data.put("user_id", user_id); // @INFO : admin or supervisor user_id
        // TODO : check for status before adding in db

        user_data.put("module", data_wrapper.QMODULE_USER);
        user_data.put("query_type",   data_wrapper.QTYPE_I);
        user_data.put("query",    data_wrapper.Q_ADD_USER);

        return user_data;
    }

    @Override
    public void make_volley_request(StringRequest stringRequest) {
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void handle_error_response(VolleyError error) {

    }

    @Override
    public void handle_jsonexception_error(JSONException e) {

    }
}
