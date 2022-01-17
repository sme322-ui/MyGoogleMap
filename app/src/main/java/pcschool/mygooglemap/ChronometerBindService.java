package com.pcschool.mygooglemap;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class ChronometerBindService extends Service {
    private final IBinder myBinder = new MyBinder();
    private Handler handler = new Handler();

    private Runnable showTime = new Runnable(){

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            Log.i("mylog",new Date().toString());
            handler.postDelayed(this, 1000);
            MapsActivity.addMarker4();

        }
    };



    public class MyBinder extends Binder {
        public ChronometerBindService getService(){
            return ChronometerBindService.this;
        }
    }
    /**
     * Return the communication channel to the service.  May return null if
     * clients can not bind to the service.  The returned
     * {@link IBinder} is usually for a complex interface
     * that has been <a href="{@docRoot}guide/components/aidl.html">described using
     * aidl</a>.
     *
     * <p><em>Note that unlike other application components, calls on to the
     * IBinder interface returned here may not happen on the main thread
     * of the process</em>.  More information about the main thread can be found in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html">Processes and
     * Threads</a>.</p>
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return an IBinder through which clients can call on to the
     * service.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("mylog","onBind()");

        return myBinder;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("mylog","onCreate");
    }

    @Override
    public void onRebind(Intent intent){
        Log.d("mylog","OnRebind()");
        super.onRebind(intent);

    }
    @Override
    public int onStartCommand(Intent intent,int flag,int startId){
        Log.i("mylog","onStartCommand()");
        handler.post(showTime);
        return Service.START_NOT_STICKY;
    }
    @Override
    public boolean onUnbind(Intent intent){
        Log.d("mylog","onUnbind()");
        handler.removeCallbacks(showTime);
        return super.onUnbind(intent);
    }
    @Override
    public void onDestroy(){
        Log.i("mylog","onDestroy()");
        handler.removeCallbacks(showTime);
        super.onDestroy();
    }

}

