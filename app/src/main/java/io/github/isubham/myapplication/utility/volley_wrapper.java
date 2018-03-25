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

    public abstract void  handle_response(JSONObject jsonObject);

    public abstract  void make_volley_request(StringRequest stringRequest);

    public abstract void handle_error_response(VolleyError error);

    public abstract  void handle_jsonexception_error(JSONException e);

    public void make_request(){

        String url = data_wrapper.BASE_URL_TEST;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject res ;
                        try{
                            res = new JSONObject(response);

                            Log.e("Resource in volley_wrapper", res.toString());
                            handle_response(res);

                        } catch (JSONException e)
                        {
                            handle_jsonexception_error(e);
                        }

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



        /*
        HashMap<String, String> data = build_user_data();

        for (String i: data.keySet()) {
            Toast.makeText(this, i + " v : " + data.get(i), Toast.LENGTH_SHORT).show();
        }


        TextView log = (TextView)findViewById(R.id.ca_log);
        log.setText(build_user_data().toString());



        ////////////////////////////////////////////////////////////////
        // fetch a String
        ////////////////////////////////////////////////////////////////

        String url = "http://ip.jsontest.com";
        StringRequest stringRequest  = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // handle result
                        Toast.makeText(create_account.this, "Hello" + response,
                                Toast.LENGTH_SHORT).show();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(create_account.this, "Error Occured",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Volley.newRequestQueue(this).add(stringRequest);

        */

        ////////////////////////////////////////////////////////////////
        // fetch json
        ////////////////////////////////////////////////////////////////

        /*

        String url = "http://httpbin.org/get?site=code&network=tutsplus";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String res = response.toString();
                        Toast.makeText(create_account.this, res,
                                Toast.LENGTH_SHORT).show();

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        Volley.newRequestQueue(this).add(jsonObjectRequest);

        */

        ////////////////////////////////////////////////////////////////
        // fetch a image
        ////////////////////////////////////////////////////////////////

        /*

        final ImageView my_image = (ImageView) findViewById(R.id.my_image);

        String url = // "https://avatars0.githubusercontent.com/u/15142714?s=460&v=4";
                "http://www.clker.com/cliparts/3/m/v/Y/E/V/small-red-apple-md.png";

        ImageRequest imageRequest = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        my_image.setImageBitmap(response);
                    }
                },

                0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888,

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(create_account.this, "Error",
                                Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        );

        Volley.newRequestQueue(this).add(imageRequest);

        */

