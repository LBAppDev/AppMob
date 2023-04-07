package com.example.signupaactivity;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;

public class NetworkService extends Service {
    private NetworkChangeReceiver networkReceiver;
    private final IBinder binder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        networkReceiver = new NetworkChangeReceiver();
        registerReceiver(networkReceiver, filter);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (networkReceiver != null) {
            unregisterReceiver(networkReceiver);
            networkReceiver = null;
        }
        super.onDestroy();
    }

    public NetworkChangeReceiver getNetworkReceiver(){
        return this.networkReceiver;
    }

    public class LocalBinder extends Binder {
        NetworkService getService() {
            // Return this instance of LocalService so
            // clients can call public methods.
            return NetworkService.this;
        }
    }
}
