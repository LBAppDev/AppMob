package com.example.signupaactivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button logOut;
    NetworkService networkService;
    boolean mBound = false;
    boolean connected = true;
    private ItemAdapter adapter;
    public static List<Bitmap> bitmaps;

    /** Defines callbacks for service
     binding, passed to bindService(). */
    private ServiceConnection
            connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to NetworkService, cast
            // the IBinder and get LocalService instance.
            NetworkService.LocalBinder binder
                    = (NetworkService.LocalBinder) service;
            networkService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String firstName = preferences.getString("first_name", "");
                String familyName = preferences.getString("family_name", "");
        TextView welcomeTextView = findViewById(R.id.welcome_textview);
        welcomeTextView.setText(getString(R.string.hello)+ " " + firstName + " " + familyName);
        logOut = findViewById(R.id.logOUt);
        logOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                        HomeActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("email", "");
                editor.apply();
                Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);

                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 1. To Start the service
        Intent intent = new Intent(this, NetworkService.class);
        startService(intent);
        // Connect this activity to the service
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

       loadFoodMenu();
        logOut = findViewById(R.id.logOUt);
        logOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                        HomeActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("email", "");
                editor.apply();
                Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);

                startActivity(intent);
            }
        });

    }
    private void loadFoodMenu() {



        List<Item> food_items = new ArrayList<>();


        makeVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                for(int i = 0; i<response.length(); i++){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String description = jsonObject.getString("description");
                        String image = jsonObject.getString("image");
                        double price = jsonObject.getDouble("price");
                        food_items.add(new Item(name, description, image, price));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setContentView(R.layout.activity_home);
                                recyclerView = findViewById(R.id.recycler_view);
                                recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                                ItemAdapter adapter = new ItemAdapter(food_items);
                                recyclerView.setAdapter(adapter);
                                logOut = findViewById(R.id.logOUt);
                                logOut.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View view) {
                                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                                                HomeActivity.this);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("email", "");
                                        editor.apply();
                                        Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);

                                        startActivity(intent);
                                    }
                                });
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 2. To disconnect this activity from service
        unbindService(connection);
        // Stop the service
        Intent intent = new Intent(this, NetworkService.class);
        stopService(intent);
    }

    private void LogOut(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                HomeActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", "");
        editor.apply();
        Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);

        startActivity(intent);
    }

    public void makeVolleyRequest(final VolleyCallback callback){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http:/"+HTTP.host+"/load.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }

        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonArrayRequest);
    }

}