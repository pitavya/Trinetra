package io.github.isubham.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import io.github.isubham.myapplication.utility.s;
public class admin_package extends AppCompatActivity {


    // => ui components
    TextView package_name, package_start_date, package_end_date, package_id;

    // => end of ui components

    // => states
    Bundle bundle;
    String bundle_data;
    JSONObject bundle_json_object;


    ProgressDialog progressDialog;

    String package_name_string, package_start_date_string, package_end_date_string,
    package_id_string;

    // => end of states
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_package);


        // states =>

        // => states
        bundle = getIntent().getExtras();

        if (bundle != null){
            bundle_data = bundle.getString("bundle_data_admin_project_to_admin_package");

            // get bundle user_id and project_id

            try{
                bundle_json_object = s.string_to_json(bundle_data);

                package_id_string = bundle_json_object
                        .getString("package_id");

                package_name_string = bundle_json_object
                        .getString("package_name");

                package_start_date_string = bundle_json_object
                        .getString("package_start_date");

                package_end_date_string = bundle_json_object
                        .getString("project_end_date");


                Toast.makeText(this, "states fetched",
                        Toast.LENGTH_SHORT).show();

                package_id_string = bundle_json_object
                        .getString("package_id");

            }catch (JSONException e){
                Log.e("admin_create_package", "json exception");
            }
        }


        // end of states =>

        // referencing ui
        package_id = (TextView) findViewById(R.id.a_pkg_package_id);
        package_start_date = (TextView) findViewById(R.id.a_pkg_package_start_date);
        package_end_date = (TextView) findViewById(R.id.a_pkg_package_end_date);
        package_name = (TextView) findViewById(R.id.a_pkg_package_name);

        // => binding state with ui
        package_id.setText(package_id_string);
        package_name.setText(package_name_string);
        package_start_date.setText(package_start_date_string);
        package_end_date.setText(package_end_date_string);



        progressDialog = new ProgressDialog(admin_package.this);
        progressDialog.setTitle("Fetching Shifts");


    }

    public void admin_add_contracter(View V) {

        Intent to_add_contracter = new Intent(admin_package.this,
                admin_add_contracter_package.class);

        to_add_contracter.putExtra("bundle_date_admin_package_to_admin_add_contracter_package",
                bundle_data +",package_id:"+package_id_string
                );

        startActivity(to_add_contracter);
    }
}
