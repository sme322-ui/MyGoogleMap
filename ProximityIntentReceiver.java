package com.pcschool.mygooglemap;


import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

import static com.pcschool.mygooglemap.BootBroadcastReceiver.ACTION;
import static com.pcschool.mygooglemap.MapsActivity.sendPost;

public class ProximityIntentReceiver extends BroadcastReceiver {
    protected static String a;

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent
     * broadcast.  During this time you can use the other methods on
     * BroadcastReceiver to view/modify the current result values.  This method
     * is always called within the main thread of its process, unless you
     * explicitly asked for it to be scheduled on a different thread using
     * . When it runs on the main
     * thread you should
     * never perform long-running operations in it (there is a timeout of
     * 10 seconds that the system allows before considering the receiver to
     * be blocked and a candidate to be killed). You cannot launch a popup dialog
     * in your implementation of onReceive().
     *
     * <p><b>If this BroadcastReceiver was launched through a &lt;receiver&gt; tag,
     * then the object is no longer alive after returning from this
     * function.</b> This means you should not perform any operations that
     * return a result to you asynchronously. If you need to perform any follow up
     * background work, schedule a {@link JobService} with
     * {@link JobScheduler}.
     * <p>
     * If you wish to interact with a service that is already running and previously
     * bound using ,
     * you can use {@link #peekService}.
     *
     * <p>The Intent filters used in {@link Context#registerReceiver}
     * and in application manifests are <em>not</em> guaranteed to be exclusive. They
     * are hints to the operating system about how to find suitable recipients. It is
     * possible for senders to force delivery to specific recipients, bypassing filter
     * resolution.  For this reason, {@link #onReceive(Context, Intent) onReceive()}
     * implementations should respond only to known actions, ignoring any unexpected
     * Intents that they may receive.
     *
     * @param context The Context in which the receiver is running.
     * @param intent  The Intent being received.
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        int i=5;
        Boolean entering = intent.getBooleanExtra(key, false);

        if(entering&&i==5){


            Toast.makeText(context, "已進入牧場"+a, Toast.LENGTH_LONG);
            System.out.println("CHECK IN");

            i--;

                Intent mainActivityIntent = new Intent(context, LoginActivity.class);  // 要啟動的Activity
                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mainActivityIntent);

            new Thread(){
                @Override
                public void run()
                {
                    //把网络访问的代码放在这里
                    try {
                        sendPost("http://localhost:8080/user/","4",true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }
        else{
            Toast.makeText(context, "已離開牧場", Toast.LENGTH_LONG);
            System.out.println("CHECK OUT");
            i++;


        }


    }
}
