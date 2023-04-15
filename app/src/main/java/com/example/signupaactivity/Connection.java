package com.example.signupaactivity;

import android.os.StrictMode;
import android.widget.Toast;


import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public abstract class Connection extends AppCompatActivity {
    private String host;
    protected String page;

    public void connectt(String email, String password, String age, String address, String firstName, String familyName) throws IOException {

        host = "/10.42.0.1";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http:/"+host+"/"+page,

                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        String status;
                        try {//+1
                            jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");
                        } catch (JSONException e) {//+1
                            throw new RuntimeException(e);
                        }

                        if(status.equals("exist")){//+1
                            ResponseExist();
                        }else if (status.equals("success")) {//+1

                            try {//+2
                                ResponseMethod(jsonObject);
                            } catch (JSONException e) {//+2
                                throw new RuntimeException(e);
                            }

                        } else {//+1

                            try {//+2
                                error1(jsonObject);
                            } catch (JSONException e) {//+2
                                throw new RuntimeException(e);
                            }

                        }

                    } // on response 13

                },
                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {

// Error occurred, show error message

                        error2(error);

                    }

                }) {

            @Override

            protected Map<String, String> getParams() throws AuthFailureError {

// Set POST parameters

                return getStringMap(email, password, age, address, firstName, familyName);

            }

        };

// Add request to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    protected abstract void ResponseExist();

    protected abstract Map<String, String> getStringMap(String email, String password, String age, String address, String firstName, String familyName);

    protected abstract void error2(VolleyError error);

    public abstract  void ResponseMethod(JSONObject jsonObject) throws JSONException;
    public abstract  void ResponseMethod();
    protected abstract void error1(JSONObject jsonObject) throws JSONException;
}


