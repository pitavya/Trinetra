package io.github.isubham.myapplication.model;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by suraj on 3/3/18.
 */

public class project {
    public String project_id, project_name, project_start_date, project_end_date;

    public project() {

    }

    public project(String project_id, String project_name, String project_start_date, String project_end_date) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.project_start_date = project_start_date;
        this.project_end_date = project_end_date;
    }


    public project(String project_id, String other_details) {

        this.project_id = project_id;

        try{

            JSONObject e = new JSONObject(other_details);
            this.project_start_date =  e.getString("project_start_date");
            this.project_end_date =    e.getString("project_start_date");
            this.project_name =        e.getString("project_name");

        } catch (JSONException e) {
            // TODO : handle json exception
        }



    }


}
