package com.example.signupaactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static ConnectivityChangeListener connListener;
    private boolean isConnected;
    private static NetworkChangeReceiver instance;

    private NetworkChangeReceiver() {
        // private constructor to prevent instantiation from outside
    }

    public static NetworkChangeReceiver getInstance(ConnectivityChangeListener listener) {
        connListener = listener;
        if (instance == null) {
            instance = new NetworkChangeReceiver();
        }
        return instance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =
                connectivityManager.getActiveNetworkInfo();
        isConnected = networkInfo !=
                null && networkInfo.isConnected();

        connListener.onConnectivityChange(isConnected);
    }

    public boolean isConnected() {
        return isConnected;
    }

}

