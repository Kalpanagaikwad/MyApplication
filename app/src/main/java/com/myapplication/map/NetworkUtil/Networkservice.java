package com.myapplication.map.NetworkUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;

import com.myapplication.map.MainActivity;

import androidx.annotation.Nullable;

public class Networkservice extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(perioud);
        return START_STICKY;
    }
    public static boolean isOnline(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf=cm.getActiveNetworkInfo();
        if (nf!=null&&nf.isConnectedOrConnecting())
            return true;
        else
            return false;
    }
    Handler handler=new Handler();
    private Runnable perioud =new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(perioud,1*1000- SystemClock.elapsedRealtime()%1000);

            Intent intent = new Intent();
            intent.setAction(MainActivity.BROADCAST);
            intent.putExtra("online_status",""+isOnline(Networkservice.this));
            sendBroadcast(intent);
        }
    };
}