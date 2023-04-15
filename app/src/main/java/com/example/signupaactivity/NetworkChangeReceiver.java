package com.example.signupaactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private boolean isConnected;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =
                connectivityManager.getActiveNetworkInfo();
        isConnected = networkInfo !=
                null && networkInfo.isConnected();

        // Display a toast whenever the connecitivty has changed
        if (isConnected)//+1
            Toast.makeText(context.getApplicationContext(),
                    "Connection restored", Toast.LENGTH_SHORT).show();
        else//+1
            Toast.makeText(context.getApplicationContext(),
                    "Connection lost", Toast.LENGTH_SHORT).show();
    }
     //on receive //2

    public boolean isConnected() {
        return isConnected;
    }

}
