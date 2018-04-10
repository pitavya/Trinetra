package io.github.isubham.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mantra.mfs100.FingerData;
import com.mantra.mfs100.MFS100;
import com.mantra.mfs100.MFS100Event;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.github.isubham.myapplication.utility.data_wrapper;
import io.github.isubham.myapplication.utility.s;
import io.github.isubham.myapplication.utility.volley_wrapper;


public class contracter_add_worker extends volley_wrapper implements MFS100Event {
    @Override
    public Map makeParams() {

        Map<String, String> worker_data = new HashMap<>();

        byte[] byteArr = fingerData.ISOTemplate();
        // print the byte[] elements


        worker_data.put("worker_name", s.text(worker_name));
        worker_data.put("worker_type", worker_type);
        worker_data.put("worker_aadhar_id", s.text(worker_aadhar_id));
        worker_data.put("worker_fingerprint", Base64.encodeToString(byteArr, Base64.DEFAULT));

        worker_data.put("user_id", user_id);

        worker_data.put("module", data_wrapper.QMODULE_WORKER);
        worker_data.put("query_type",   data_wrapper.QTYPE_I);
        worker_data.put("query",    data_wrapper.Q_CREATE_WORKER);

        return worker_data;

    }

    ProgressDialog progressDialog;

    @Override
    public void make_volley_request(StringRequest stringRequest) {
        Volley.newRequestQueue(this).add(stringRequest);
    }

// ui and data

    // TODO add fingerprint
    String worker_type;
    EditText worker_name, worker_aadhar_id;
           // worker_fingerprint;


    /* TODO : get them from real db */
    String user_id;


    public void handle_response(String response){

        progressDialog.hide();
        Log.i("ctr_add_worker", response);
        JSONObject res ;
        try{
            res = new JSONObject(response);

            String return_status = (String)res.get("status");

            Log.i("return_Status", return_status);
            switch (return_status) {

                case data_wrapper.RETURN_WORKER_ADDED : {

                    Toast.makeText(this, "manpower added", Toast.LENGTH_SHORT).show();
                    Log.i("status", "manpower added");

                }break;

                case data_wrapper.RETURN_WORKER_ALREADY_PRESENT : {
                    Toast.makeText(this, "manpower already present", Toast.LENGTH_SHORT).show();
                    Log.i("status", "manpower already added");

                }
                break;

            }

            // TODO clear the fields
            resetEditText(worker_aadhar_id, worker_name //worker_fingerprint
                     );

        } catch (JSONException e)
        {
            Log.e("ERROR 214 json", e.getMessage());
        }


    }

    public void resetEditText(EditText ...ets){
        for (EditText e : ets) {
            e.setText("");
        }
    }


    public void create_new_worker(View V) {
        make_request();
    }

    public void get_worker_type(View V){

        switch (V.getId()){

            case R.id.c_a_w_worker_type_1:
                worker_type = data_wrapper.W_TYPE_PERM_SEMI_SKILLED;

            case R.id.c_a_w_worker_type_2:
                worker_type = data_wrapper.W_TYPE_PERM_SEMI_SKILLED;

            case R.id.c_a_w_worker_type_3:
                worker_type = data_wrapper.W_TYPE_PERM_SEMI_SKILLED;

            case R.id.c_a_w_worker_type_4:
                worker_type = data_wrapper.W_TYPE_PERM_SEMI_SKILLED;

            case R.id.c_a_w_worker_type_5:
                worker_type = data_wrapper.W_TYPE_PERM_SEMI_SKILLED;

            case R.id.c_a_w_worker_type_6:
                worker_type = data_wrapper.W_TYPE_PERM_SEMI_SKILLED;

        }

    }

    public void back_to(View V){
        // TODO :
        startActivity(new Intent(contracter_add_worker.this, contracter_home.class));
    }

    public void init() {
        progressDialog = new ProgressDialog(contracter_add_worker.this);
        progressDialog.setTitle("Creating worker");
        worker_aadhar_id=  findViewById(R.id.c_a_w_aadhar_id);
        worker_name =  findViewById(R.id.c_a_w_worker_name);
        // worker_fingerprint = findViewById(R.id.c_a_w_worker_fingerprint_image_view);
    }

    Bundle bundle;
    String bundle_string;



    // manish ui components variables


    TextView lblMessage;
    TextView txtEventLog;
    ImageView imgFinger;
    EditText editText1;
    Button Capture,Verify;
    FingerData lastCapFingerData;
    // User[] user = new User[5];
    MFS100 mfs100 = null;
    int f =-1;
    int timeout = 10000;





    // end of manish ui componennts variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contracter_add_worker);

        // => state

        bundle = getIntent().getExtras();

        if (bundle != null){

            bundle_string = bundle.getString(
                    "bundle_data_contracter_home_to_contracter_add_manpower"
            );
            try{
                // => set user_id
                user_id = (s.string_to_json(bundle_string)).getString("user_id");
                Toast.makeText(this, "state changed", Toast.LENGTH_SHORT).show();
            }catch (JSONException e){
                Log.e("contracter_add_worker", "json exception");
            }

        }


        // manish ui compoennt

        // editText1 = (EditText) findViewById(R.id.editText);
        imgFinger = (ImageView) findViewById(R.id.c_a_w_worker_fingerprint_image_view);
        txtEventLog = (TextView) findViewById(R.id.text_event_log);
        lblMessage = (TextView)  findViewById(R.id.lbl_message);





        // end of manish ui compoenents

        init();



    }

    FingerData fingerData = null;

    public void scan(View V){
        //
        InitScanner();

        fingerData = StartSyncCapture();

        if (fingerData != null) {
            Toast.makeText(this, "fingerprint not empty", Toast.LENGTH_SHORT).show();
        }else{
             Toast.makeText(this, "fingerprint empty", Toast.LENGTH_SHORT).show();
        }

    }

    public void submit(View V){
        // TODO add to worker fingerprint add store it in db
        // Toast.makeText(this, "finger_print_iso"+ fingerData.ISOTemplate().toString(), Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, "egkjbege", Toast.LENGTH_SHORT).show();
        make_request();
        progressDialog.show();
    }

    @Override
    protected void onStart() {
        if (mfs100 == null) {
            mfs100 = new MFS100(this);
            mfs100.SetApplicationContext(contracter_add_worker.this);
        } else {
            InitScanner();

        }
        super.onStart();

    }
    protected void onDestroy() {
        if (mfs100 != null) {
            mfs100.Dispose();
        }
        super.onDestroy();
    }
    protected void onStop() {
        UnInitScanner();
        super.onStop();
    }


    // mantra methods

    @Override
    public void OnDeviceAttached(int vid, int pid, boolean hasPermission) {
        int ret = 0;
        if (!hasPermission) {
            SetTextonuiThread("Permission denied");
            return;
        }
        if (vid == 1204 || pid == 11279) {
            if (pid == 34323) {
                ret = mfs100.LoadFirmware();
                if (ret != 0) {
                    SetTextonuiThread(mfs100.GetErrorMsg(ret));

                } else {
                    SetTextonuiThread("Loadfirmware success");
                }
            } else if (pid == 4101) {
                ret = mfs100.Init();
                if (ret != 0) {
                    SetTextonuiThread(mfs100.GetErrorMsg(ret));
                } else {
                    SetTextonuiThread("Init success");
                    Toast.makeText(this, "Device connected", Toast.LENGTH_LONG).show();
                    String info = "Serial: "
                            + mfs100.GetDeviceInfo().SerialNo() + " Make: "
                            + mfs100.GetDeviceInfo().Make() + " Model: "
                            + mfs100.GetDeviceInfo().Model();
                    SetLogOnUIThread(info);
                }
            }
        }
    }

    @Override
    public void OnDeviceDetached() {
        UnInitScanner();
        SetTextonuiThread("Device removed");
    }



    @Override
    public void OnHostCheckFailed(String s) {
        try {
            SetLogOnUIThread(s);
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        } catch (Exception ignored) {
        }

    }


    // end of mantra methods
    public void SetTextonuiThread(final String str) {
        lblMessage.post(new Runnable() {
            public void run() {
                lblMessage.setText(str, TextView.BufferType.EDITABLE);
            }
        });
    }
    public void SetLogOnUIThread(final String str) {
        txtEventLog.post(new Runnable() {
            public void run() {

                txtEventLog.setText(txtEventLog.getText().toString() + "\n"
                        + str, TextView.BufferType.EDITABLE);
            }
        });
    }
    public void DisplayFinger(final Bitmap bitmap) {
        imgFinger.post(new Runnable() {
            @Override
            public void run() {
                imgFinger.setImageBitmap(bitmap);
            }
        });
    }
    public void InitScanner() {
        try {
            int ret = mfs100.Init();
            if (ret != 0) {
                SetTextonuiThread(mfs100.GetErrorMsg(ret));
            } else {
                SetTextonuiThread("Init success");
                String info = "Serial: " + mfs100.GetDeviceInfo().SerialNo()
                        + " Make: " + mfs100.GetDeviceInfo().Make()
                        + " Model: " + mfs100.GetDeviceInfo().Model()
                        + "\nCertificate: " + mfs100.GetCertification();
                SetLogOnUIThread(info);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Init failed, unhandled exception",
                    Toast.LENGTH_LONG).show();
            SetTextonuiThread("Init failed, unhandled exception");
        }
    }
    public void UnInitScanner() {
        try {
            int ret = mfs100.UnInit();
            if (ret != 0) {
                SetTextonuiThread(mfs100.GetErrorMsg(ret));
            } else {
                SetLogOnUIThread("Uninit Success");
                SetTextonuiThread("Uninit Success");
                lastCapFingerData = null;
            }
        } catch (Exception e) {
            Log.e("UnInitScanner.EX", e.toString());
        }
    }

    public FingerData StartSyncCapture() {



        //f++;
        //user[f].aadhaarnumber  = editText1.getText().toString();
        SetTextonuiThread("");
        try {
            FingerData fingerData = new FingerData();
            int ret = mfs100.AutoCapture(fingerData, timeout,true);
            Log.e("StartSyncCapture.RET", "" + ret);
            if (ret != 0) {
                SetTextonuiThread(mfs100.GetErrorMsg(ret));
            } else {
                lastCapFingerData = fingerData;
                //user[f++].fingerprintdata = fingerData.ISOTemplate();
                final Bitmap bitmap = BitmapFactory.decodeByteArray(fingerData.FingerImage(), 0,
                        fingerData.FingerImage().length);
                contracter_add_worker.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imgFinger.setImageBitmap(bitmap);
                    }
                });

                SetTextonuiThread("Capture Success");
                String log = "\nQuality: " + fingerData.Quality()
                        + "\nNFIQ: " + fingerData.Nfiq()
                        + "\nWSQ Compress Ratio: "
                        + fingerData.WSQCompressRatio()
                        + "\nImage Dimensions (inch): "
                        + fingerData.InWidth() + "\" X "
                        + fingerData.InHeight() + "\""
                        + "\nImage Area (inch): " + fingerData.InArea()
                        + "\"" + "\nResolution (dpi/ppi): "
                        + fingerData.Resolution() + "\nGray Scale: "
                        + fingerData.GrayScale() + "\nBits Per Pixal: "
                        + fingerData.Bpp() + "\nWSQ Info: "
                        + fingerData.WSQInfo();
                SetLogOnUIThread(log);
                //SetData2(fingerData);
            }
        } catch (Exception ex) {
            SetTextonuiThread("Error");
        }





        return  lastCapFingerData;
    }


}
