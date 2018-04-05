package io.github.isubham.myapplication.utility;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by suraj on 22/3/18.
 */

public abstract class volley_wrapper extends AppCompatActivity{

    public abstract Map makeParams();

    public abstract void  handle_response(String response);

    public abstract void make_volley_request(StringRequest stringRequest);

    public void handle_error_response(VolleyError error){
        Log.e("volley_wrapper", "handel_error_response method");
    }

    public void handle_jsonexception_error(JSONException e)
    {
        Log.e("volley_wrapper", "handel_json_exception method");
    }

    public void make_request(){

        String url = data_wrapper.BASE_URL_LOCAL;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                            Log.e("Resource volley_wrapper", response.toString());
                            handle_response(response);

                        }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handle_error_response(error);
                    }
                }
        ){

            @Override
            protected Map<String, String> getParams() {

                return makeParams();

            }
        };

        make_volley_request(stringRequest);

        // TODO :
        // Volley.newRequestQueue(this).add(stringRequest);

    }

}


