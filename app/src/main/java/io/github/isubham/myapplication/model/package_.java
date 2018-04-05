package io.github.isubham.myapplication.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by suraj on 3/3/18.
 */

public class package_ {
    public String package_id, package_name, package_start_date, package_end_date;

    public package_() {

    }

    public package_(String package_id, String package_name,
                    String package_start_date, String package_end_date) {
        this.package_id = package_id;
        this.package_name = package_name;
        this.package_start_date = package_start_date;
        this.package_end_date = package_end_date;
    }


    public package_(String package_id, String other_details) {

        this.package_id = package_id;

        try{

            JSONObject e = new JSONObject(other_details);
            this.package_start_date =  e.getString("project_start_date");
            this.package_end_date =    e.getString("project_start_date");
            this.package_name =        e.getString("project_name");

        } catch (JSONException e) {
            // TODO : handle json exception
        }



    }


}
