package io.github.isubham.myapplication;

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

public class contracter_package extends AppCompatActivity {

    Bundle bundle;
    String bundle_string;
    JSONObject bundle_jsonobject;

    // => state

    TextView package_name, package_start_date, package_end_date, package_id;

    String package_name_string, package_start_date_string, package_end_date_string, package_id_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contracter_package);

        // ui components
        package_name = (TextView) findViewById(R.id.contracter_package_package_name);
        package_id = (TextView) findViewById(R.id.contracter_package_package_id);
        package_start_date = (TextView) findViewById(R.id.contracter_package_package_start_date);
        package_end_date = (TextView) findViewById(R.id.contracter_package_package_end_date);



        // model
        bundle = getIntent().getExtras();
        if (bundle != null){

            // => get string, json and assign states

            bundle_string = bundle.getString("bundle_data_ctr_project_to_ctr_package");

            bundle_jsonobject = s.string_to_json(bundle_string);

            // => parsing state from model
            try{
                package_id_string =
                        bundle_jsonobject.getString("package_id");

                 package_name_string =
                        bundle_jsonobject.getString("package_name");
                 package_start_date_string =
                        bundle_jsonobject.getString("package_start_date");
                 package_end_date_string =
                        bundle_jsonobject.getString("package_end_date");

            }catch (JSONException e){
                Log.e("contracter_package", "json exception in the project");
            }


            // bind state to ui
            package_id.setText(package_id_string);
            package_name.setText(package_name_string);
            package_start_date.setText(package_start_date_string);
            package_end_date.setText(package_end_date_string);


        }
    }


    // project_id, package_details

//    TODO fetch day 5 back project report


//    TODO : intents to another activities
    public void contracter_assign_supervisor(View V) {

        Intent contracter_package_to_contracter_assign_supervisor =
                new Intent(contracter_package.this, contracter_assign_supervisor.class);

        contracter_package_to_contracter_assign_supervisor.putExtra("bundle_data_ctr_package_to_ctr_assign_supervisor",
                bundle_string+","+
                "package_id:"+package_id_string
                );
        startActivity(contracter_package_to_contracter_assign_supervisor);

    }

    public void contracter_assign_manpower(View V) {

        Intent contracter_package_to_contracter_assign_manpower =
                new Intent(contracter_package.this, contracter_assign_manpower.class);

        contracter_package_to_contracter_assign_manpower.putExtra("bundle_data_ctr_package_to_ctr_assign_manpower",
                bundle_string+","+
                "package_id:"+package_id_string
                );

        startActivity(contracter_package_to_contracter_assign_manpower);

    }

    public void contracter_view_report(View V) {
        Toast.makeText(this, "project report will be added soon", Toast.LENGTH_SHORT).show();
    }


}
