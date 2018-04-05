package io.github.isubham.myapplication.utility;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by suraj on 2/3/18.
 */

public class s {

    // @reference https://dzone.com/articles/storing-passwords-java-web
    public static String salty(String s) {

        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(s.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
        }

        return hash.toString();
    }

    // text
    public static String text(TextView V) {

        return V.getText().toString().trim();

    }

    public static JSONObject string_to_json(String json_string){
        // get keys
        JSONObject jsonObject = new JSONObject();


        String key_val[] = json_string.split(",");

        for(String k_v : key_val){
            String key_val_array[] = k_v.split(":");
            try{

                jsonObject.put(key_val_array[0], key_val_array[1]);

            }catch (JSONException e) {
                Log.e("string_to_JSON", "json exception");
            }
        }

        return jsonObject;
    }

    // { a : b, c : d }, e : f => a:b,c:d,e:f
    public static String json_to_string(JSONObject json_object, String key_root,
                                        String val_root){
        // get keys
        JSONArray json_keys = json_object.names();
        String json_string = "";
        try{

        // convert to i => json(i), ..
        for(int i = 0; i < json_keys.length(); i++){
            json_string += json_keys.get(i) + ":" +
                    json_object.getString((String)json_keys.get(i)) + ",";
        }
        json_string += key_root + ":" + val_root;
        // return string

        }catch (JSONException e){
            Log.e("S utility", "JSON error");
        }
        return json_string;
    }

    // { a : b, c : d } => a:b,c:d
    public static String json_to_string(JSONObject json_object){
        // get keys
        JSONArray json_keys = json_object.names();
        String json_string = "";
        try{

        // convert to i => json(i), ..
        for(int i = 0; i < json_keys.length(); i++){
            json_string = json_keys.get(i) + ":" +
                    json_object.getString((String)json_keys.get(i)) + ",";
        }
        // return string

        }catch (JSONException e){
            Log.e("S utility", "JSON error");
        }
        return json_string;
    }



    public static String text(EditText V) {

        return V.getText().toString().trim();

    }



}
