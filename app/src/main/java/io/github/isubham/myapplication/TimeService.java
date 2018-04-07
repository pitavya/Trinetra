package io.github.isubham.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by suraj on 7/4/18.
 */

public class TimeService extends Service {


    // MainActivity ma = new MainActivity();

    // List<Integer> workerList = new ArrayList<Integer>();

    List<Integer> workerList = new ArrayList<Integer>();
    // NotificationCompat.Builder notification;
    int count, min, hr;
    static String t;
    String nextTime;
    List<Integer> countFn = new ArrayList<Integer>();
    int nextMin, curMin, call;
    Random random;
    public static Runnable runnable;
    public int m;

    Bundle bundle;


     String user_id;

    String shift_attendence;
    @Override
    public  int onStartCommand(Intent intent, int flags, int startId) {

        user_id =
                intent.getStringExtra("bundle_data_supervisor_shift_to_supervisor_time");
        shift_attendence =
                intent.getStringExtra("bundle_data_supervisor_shift_to_supervisor_timer_service_shift_attendence");

        intanstiate();

        Log.i("shift_attendence", shift_attendence);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {




    }


    public void first_last() {
        m = 0;
        t = getCurrentTime();
        min = (t.charAt(3) - 48) * 10 + (t.charAt(4) - 48);
        hr = (t.charAt(0) - 48) * 10 + (t.charAt(1) - 48);

        min = min + 15;
        if (min > 59) {
            if (hr == 23)
                hr = 0;
            else
                hr = hr + 1;
            min = min - 60;
        }

        nextMin = hr * 60 + min;

        count =workerList.size();
        randomId();

    }


    // getting data from activity




    public void middle() {
        m = 1;
        random = new Random();

        int index, wid, c = 0, callTime;

        while (c < call) {
            index = random.nextInt(count);
            wid =workerList.get(index);
            callTime = random.nextInt(7) * 20;
            t = getCurrentTime();
            min = (t.charAt(3) - 48) * 10 + (t.charAt(4) - 48);
            hr = (t.charAt(0) - 48) * 10 + (t.charAt(1) - 48);
            min = min + callTime;
            while (min > 59) {
                hr = hr + 1;
                min = min - 60;
            }

            if (hr <= 9 && min <= 9)
                nextTime = "0" + hr + ":" + "0" + min;

            else if (min <= 9)
                nextTime = hr + ":" + "0" + min;
            else if (hr <= 9)
                nextTime = "0" + hr + ":" + min;
            else
                nextTime = hr + ":" + min;

            while (true) {
                if (nextTime.equals(getCurrentTime())) {
                    notifySupri(wid);
                    break;
                }
            }

            c++;
        }


        first_last();
    }

    public void randomId() {


        // flag checking
        Log.e("inside service", "timerservice supervisor id"
                + user_id +

                "shift_attendence" + shift_attendence

        );
        // => end of flag checking



        t = getCurrentTime();
        min = (t.charAt(3) - 48) * 10 + (t.charAt(4) - 48);
        hr = (t.charAt(0) - 48) * 10 + (t.charAt(1) - 48);
        curMin = hr * 60 + min;
        if (nextMin > curMin) {
            Log.d("vivz", "random id called");
            Random random = new Random();

            int index = random.nextInt(count);
            int wid = workerList.get(index);
            Log.d("vivz", wid + "called");

            if (countFn.contains(wid)) {
                randomId();
            }
            countFn.add(wid);
            notifySupri(wid);
            // sleep for 10 seconds after every call for first and last 15 minutes.


            if(countFn.size()==workerList.size()){
                try {
                    TimeUnit.MINUTES.sleep(nextMin-curMin);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                call = random.nextInt(3);
                middle();

            }

            else{
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                randomId();}
        }
        else {
            // decides how many call t l l l l
            call = random.nextInt(3);
            middle();
        }
    }

    public void notifySupri(int wid) {
        //notification.setSmallIcon(R.drawable.enigma);
//        notification.setTicker("verification notice");
//        notification.setWhen(System.currentTimeMillis());
        //       notification.setContentTitle("verification notice");
        //        notification.setPriority(Notification.PRIORITY_MAX);
//        notification.setContentText("call worker :" + wid);
        //       notification.setDefaults(Notification.DEFAULT_SOUND);

//        Intent intent = new Intent(this,Verification.class);
//        startActivity(intent);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        notification.setContentIntent(pendingIntent);
//        NotificationManager nm = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
//        nm.notify(uniqueID2, notification.build());
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
//        Notification notification = new Notification(internalSetBigContentTitle("call worket"));
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        notification.setContentIntent(pendingIntent);
//        notificationManager.notify(uniqueID2, notification);

        Intent intent = new Intent(this, supervisor_authenticate_worker.class);
        intent.putExtra("worker_id", Integer.toString(wid));

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );

        Notification n  = new Notification.Builder(this)
                //.setContentTitle("New mail from " + "test@gmail.com")
                // .setContentText("Subject")
                .setSmallIcon(R.drawable.fp)
                //       notification.setSmallIcon(R.drawable.enigma);
                .setTicker("verification notice")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("verification notice")
                .setPriority(Notification.PRIORITY_MAX)
                .setContentText("call worker :" + wid)
                .setDefaults(Notification.DEFAULT_SOUND).setContentIntent(pIntent).build();
        //.setAutoCancel(true).build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        notificationManager.notify(wid, n);
        // startActivity(intent);


    }


    String getCurrentTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String currentTime = dateFormat.format(System.currentTimeMillis());
        return currentTime;
    }

public void intanstiate(){

        Log.i("attendence_instantiate", shift_attendence);


    Thread thread = new Thread(){

        public void run(){


            // Toast.makeText(ma, "o hread", Toast.LENGTH_SHORT).show();
            Log.i("another thread", "another thread");
//
//                workerList.add(1);
//                workerList.add(2);
//                workerList.add(3);
//                workerList.add(4);
//                workerList.add(5);
//                workerList.add(6);
//                workerList.add(7);
//                workerList.add(8);
//                workerList.add(10);
//

            for (String worker_id : shift_attendence.split(" ")) {
                workerList.add(Integer.parseInt(worker_id));
            }



            Log.i("shift_attendence", shift_attendence);
            // fetch worker list from db
            first_last();
        }
    };

    thread.start();

}

}
