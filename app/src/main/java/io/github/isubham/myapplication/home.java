package io.github.isubham.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class home extends AppCompatActivity {


    public void open_create_account(View V){

        startActivity(new Intent(home.this, create_account.class));

    }


    public void open_sign_in(View V){

        startActivity(new Intent(home.this, sign_in.class));

    }



    public void open_contractor_panel(View V){

        // TODO : startActivity(new Intent(home.this, home_contractor.class));
        Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();
    }

    public void open_admin_panel(View V){

        startActivity(new Intent(home.this, admin_home.class));

    }

    public void open_admin_create_project(View V) {

        startActivity(new Intent(home.this, admin_create_project.class));

    }


    public void open_admin_project(View V) {

        startActivity(new Intent(home.this, admin_project.class));

    }


    public void open_add_worker_supervisor(View V) {

        startActivity(new Intent(home.this, contracter_add_worker.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }
}
