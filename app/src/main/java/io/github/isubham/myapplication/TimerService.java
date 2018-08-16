package io.github.isubham.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by suraj on 7/4/18.
 */

public class TimerService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    TimerService(int time){
        this.NOTIFY_INTERVAL = time;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (timer != null){
            timer.cancel();
        }else{
            timer = new Timer();
        }
        timer.scheduleAtFixedRate(new timer_task(), 0, NOTIFY_INTERVAL);

    }


    // from blog
    public  long NOTIFY_INTERVAL = 10 * 1000;

    private Handler handler = new Handler();

    private Timer timer;

    class timer_task extends TimerTask {
        @Override
        public void run() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    notifySupri();
                }
            });
        }
    }

    // end of from blog




    public void notifySupri() {
        Intent intent = new Intent(this, supervisor_authenticate_worker.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification n = new Notification.Builder(this)
                //.setContentTitle("New mail from " + "test@gmail.com")
                // .setContentText("Subject")
                .setSmallIcon(R.drawable.fp)
                //.notification.setSmallIcon(R.drawable.enigma);
                .setTicker("verification notice")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("verification notice")
                .setPriority(Notification.PRIORITY_MAX)
                .setContentText("call worker :" /*+ wid*/)

                .setDefaults(Notification.DEFAULT_SOUND).setContentIntent(pIntent).build();
        //.setAutoCancel(true).build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }



    // subham implementation
    long unix_time;
    long next_min;
    // first last
    public void f_l() {
        unix_time = System.currentTimeMillis() / 1000L;

        next_min = unix_time + (10 // min
                                *60 // sec
                                *1000 //sec
                    );



    }
    // end of subham implementation

}