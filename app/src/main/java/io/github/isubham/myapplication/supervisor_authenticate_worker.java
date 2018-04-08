package io.github.isubham.myapplication;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mantra.mfs100.FingerData;
import com.mantra.mfs100.MFS100;
import com.mantra.mfs100.MFS100Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import io.github.isubham.myapplication.utility.volley_wrapper;

public class supervisor_authenticate_worker extends volley_wrapper implements MFS100Event {



    String auth_flag = "0";

    TextView lblMessage;
    TextView txtEventLog;
    ImageView imgFinger;
   FingerData lastCapFingerData;
    // User[] user = new User[5];
    MFS100 mfs100 = null;
    int f =-1;
    int timeout = 10000;


      @Override
        public Map makeParams() {

            Map<String, String> get_worker_details_param =
                    new HashMap<>();

            get_worker_details_param.put("worker_id", worker_id_string);

            get_worker_details_param.put("module", "worker");
            get_worker_details_param.put("query", "read_worker");
            get_worker_details_param.put("query_type", "o");

            Log.i("makeParams", get_worker_details_param.toString());
            return get_worker_details_param;

        }

        @Override
        public void handle_response(String response) {
          progressDialog.hide();

          Log.i("handle_response", response);

            try {
                // inflate the view
                JSONObject worker_details = new JSONObject(response);

                worker_details = worker_details.getJSONObject(worker_id_string);

                worker_name.setText(worker_details.getString("worker_name"));
                worker_id_text.setText(worker_id_string);
                worker_aadhar.setText(worker_details.getString("worker_aadhar_id"));
                worker_type.setText(worker_details.getString("worker_type"));

                // set byte array string here
                fingerprint_byteArray = worker_details.getString("worker_fingerprint");


            }catch (JSONException e){
                Log.i("json ex", "in handle response");
            }


        }

        @Override
        public void make_volley_request(StringRequest stringRequest) {
            Volley.newRequestQueue(supervisor_authenticate_worker.this)
                    .add(stringRequest);
        }
    // TODO fetch worker details provided the worker_id

    String user_id, project_id, package_id;
    Bundle bundle;
    String fingerprint_byteArray;
    TextView worker_name, worker_id_text, worker_type, worker_aadhar;
    String worker_id_string;
    ProgressDialog progressDialog, progressDialog_create_auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_authenticate_worker);

        progressDialog_create_auth = new ProgressDialog(supervisor_authenticate_worker.this);
        progressDialog_create_auth.setTitle("Submiting Authentication");

        progressDialog = new ProgressDialog(supervisor_authenticate_worker.this);
        progressDialog.setTitle("Fetching worker data");
        progressDialog.show();

        bundle = getIntent().getExtras();
        Toast.makeText(this, bundle.getString("worker_id"), Toast.LENGTH_SHORT).show();



        imgFinger = (ImageView) findViewById(R.id.s_a_w_worker_fingerprint_image_view);
        txtEventLog = (TextView) findViewById(R.id.s_a_w_text_event_log);
        lblMessage = (TextView)  findViewById(R.id.s_a_w_lbl_message);



        worker_id_string = bundle.getString("worker_id");

        user_id = bundle.getString("user_id");
        project_id = bundle.getString("project_id");
        package_id = bundle.getString("package_id");

        // reference ui components
        worker_name = (TextView) findViewById(R.id.s_a_w_worker_name);
        worker_id_text = (TextView) findViewById(R.id.s_a_w_worker_id);
        worker_aadhar = (TextView) findViewById(R.id.s_a_w_worker_aadhar_id);
        worker_type = (TextView) findViewById(R.id.s_a_w_worker_type);

        make_request();

    }

    byte[] conver_string_to_byte(String byte_array_string) {
        byte_array_string = byte_array_string.replace("[", "");
        byte_array_string = byte_array_string.replace("]", "");

        byte[] final_parsed_fingerprint = new byte[2000];
        int counter = 0;
        for(String i : byte_array_string.split(", ")){

            final_parsed_fingerprint[counter] = Byte.valueOf(i);
        }

        return final_parsed_fingerprint;
    }


    // for retrying the fingerprint
    public void scan(View V) {

        InitScanner();

        FingerData fingerData = null;
        fingerData = StartSyncCapture();

        // string to byte array
        byte[] real_byte_array =
                conver_string_to_byte(fingerprint_byteArray);

        Log.e("worker_fingerprint", fingerprint_byteArray);

        int ret = mfs100.MatchISO(fingerData.ISOTemplate(),real_byte_array);

        Log.i("scanned fingerprint", fingerData.ISOTemplate().toString());

        if (ret < 0){
            Toast.makeText(this, "Error Scanning", Toast.LENGTH_SHORT).show();
            auth_flag = "0";
        }

        else if(ret >= 1200){
            Toast.makeText(this, "fingerprint not matched", Toast.LENGTH_SHORT).show();
            auth_flag = "1";
        }

        else{
            Toast.makeText(this, "fingerprint did Not matched", Toast.LENGTH_SHORT).show();
            auth_flag = "1";
        }



    }

    // for final submission
    public void submit(View V) {

        progressDialog_create_auth.show();
        volley_wrapper submit_attendence = new volley_wrapper() {
            @Override
            public Map makeParams() {
                Map<String, String> auth_param = new HashMap<>();

                auth_param.put("query", "create_authentication");
                auth_param.put("module", "authentication");
                auth_param.put("query_type", "i");


                auth_param.put("auth_date", "8-04-2018"/*TODO add system date*/);
                auth_param.put("auth_status", auth_flag);
                auth_param.put("user_id", user_id);
                auth_param.put("project_id", project_id);
                auth_param.put("package_id", package_id);
                auth_param.put("gen_worker_id", worker_id_string);

                return  auth_param;
            }

            @Override
            public void handle_response(String response) {
                progressDialog_create_auth.hide();

                // { status : {+ve, -1}

                try{

                    JSONObject status_response = new JSONObject(response);


                    if (status_response.getString("status") == "-1"){
                        // not inserted
                        Toast.makeText(this, "Failed , Try Again", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(this, "Authentication Submitted", Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    Log.e("json ex", "json exception in " +
                            "supervisor_authenticate worker");
                }

            }

            @Override
            public void make_volley_request(StringRequest stringRequest) {
                Volley.newRequestQueue(supervisor_authenticate_worker.this)
                        .add(stringRequest);
            }
        };

        submit_attendence.make_request();

    }

    // TODO verify button should calculate status and store data in auth table

    @Override
    protected void onStart() {
        if (mfs100 == null) {
            mfs100 = new MFS100(this);
            mfs100.SetApplicationContext(supervisor_authenticate_worker.this);
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
                supervisor_authenticate_worker.this.runOnUiThread(new Runnable() {
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
