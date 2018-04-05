package io.github.isubham.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class contracter_package extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contracter_package);
    }


//    TODO fetch day 5 back project report


//    TODO : intents to another activities
    public void contracter_assign_supervisor(View V) {
        startActivity(new Intent(contracter_package.this, contracter_assign_supervisor.class));
    }

    public void contracter_assign_manpower(View V) {
        startActivity(new Intent(contracter_package.this, contracter_assign_manpower.class));
    }

    public void contracter_view_report(View V) {
        Toast.makeText(this, "project report will be added soon", Toast.LENGTH_SHORT).show();
    }


}
