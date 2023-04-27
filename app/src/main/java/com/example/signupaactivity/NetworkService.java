package com.example.signupaactivity;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NetworkService extends Service {
    private NetworkChangeReceiver networkReceiver;
    public AppCompatActivity activity;
    private final IBinder binder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        networkReceiver = NetworkChangeReceiver.getInstance(new ConnectivityChangeListener() {
            @Override
            public void onConnectivityChange(boolean isConnected) {
                if(isConnected)
                    Toast.makeText(NetworkService.this, "connection restored", Toast.LENGTH_SHORT).
                            show();
                else
                    Toast.makeText(NetworkService.this, "connection lost", Toast.LENGTH_SHORT).
                            show();
            }
        });

        registerReceiver(networkReceiver, filter);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (networkReceiver != null) {//+2
            unregisterReceiver(networkReceiver);
            networkReceiver = null;
        }
        super.onDestroy();
    }
// on destroy 2
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
