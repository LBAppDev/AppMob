package com.example.signupaactivity;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public abstract class Connection extends AppCompatActivity {
    private String host = "10.42.0.1";
    protected String page;

    public void connectt(String email, String password, String age, String address) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+host+"/"+page,

                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        if(response.trim().equals("exist")){
                            ResponseExist();
                        }else if (response.trim().equals("success")) {

                            ResponseMethod();

                        } else {

                            error1(response);

                        }

                    }

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

                return getStringMap(email, password, age, address);

            }

        };

// Add request to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    protected abstract void ResponseExist();

    protected abstract Map<String, String> getStringMap(String email, String password, String age, String address);

    protected abstract void error2(VolleyError error);

    public abstract  void ResponseMethod();
    protected abstract void error1(String response);
}


