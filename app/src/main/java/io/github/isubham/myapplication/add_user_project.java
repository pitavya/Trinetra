package io.github.isubham.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.s;

public class add_user_project extends AppCompatActivity {

    // ui and data

    EditText user_email;

    /* TODO : get them from real db */
    String project_id  = "12";
    String user_id  = data_wrapper.TEMP_ADMIN_ID;


    public Map<String, String> build_user_data(){
        Map<String, String> user_data = new HashMap<>();


        user_data.put("email", s.text(user_email));
        user_data.put("project_id", project_id);
        user_data.put("user_id", user_id); // @INFO : admin or supervisor user_id
        // TODO : check for status before addin in db

        user_data.put("module", data_wrapper.QMODULE_USER);
        user_data.put("query_type",   data_wrapper.QTYPE_I);
        user_data.put("query",    data_wrapper.Q_ADD_USER);

        return user_data;
    }


    public void handle_response(String response){


        // flag 1, 0 for added user or not

        // Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        //
                        JSONObject res ;
                        try{
                            res = new JSONObject(response);
                            String id = res.get("status").toString();

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
                        //


    }

    public void handle_error(){

    }



    public void add_user(View V) {

        String url = data_wrapper.BASE_URL_TEST;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        handle_response(response);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(add_user_project.this, "Error in POST", Toast.LENGTH_SHORT).show();
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

    }

    public void back_to(View V){
        // TODO :
    }

    public void init() {
        user_email = (EditText) findViewById(R.id.a_u_p_email);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_project);
        init();
    }
}
