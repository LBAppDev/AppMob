package com.example.signupaactivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends Connection {
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText ageEditText;
    private EditText addressEditText;
    private EditText firstNameText;
    private EditText familyNameText;

    private String email;
    private String password;
    private String age;
    private String address;
    private String firstName;
    private String familyName;
    private Button signUpButton;
    
    private String localhost = "10.42.0.1";
    NetworkService networkService;
    boolean mBound = false;
    boolean connected = true;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page="signup.php";
        setContentView(R.layout.activity_sign_up);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        ageEditText = findViewById(R.id.age);
        addressEditText = findViewById(R.id.address);
        signUpButton = findViewById(R.id.signup_button);
        firstNameText = findViewById(R.id.firstName);
        familyNameText = findViewById(R.id.familyName);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 email = emailEditText.getText().toString();
                 password = passwordEditText.getText().toString();
                 age = ageEditText.getText().toString();
                address = addressEditText.getText().toString();
                firstName = firstNameText.getText().toString();
                familyName = familyNameText.getText().toString();

                try {//+1
                    connectt(email, password, age, address, firstName, familyName);
                } catch (IOException e) {//+1
                    throw new RuntimeException(e);
                }
            } // on click 2
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

    protected void ResponseExist(){
        Toast.makeText(SignUpActivity.this, "email already used !", Toast.
                LENGTH_SHORT).show();
    }

    public void ResponseMethod(JSONObject jsonObject) throws JSONException {
        Toast.makeText(SignUpActivity.this, "Sign Up Success", Toast.
                LENGTH_SHORT).show();
        JSONObject userObject = jsonObject.getJSONObject("user_info");
        String family_name = userObject.getString("family_name");
        String first_name = userObject.getString("first_name");
        String email = userObject.getString("email");
        int age = Integer.parseInt(userObject.getString("age"));
        String address = userObject.getString("address");
// Save user information to shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                SignUpActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("family_name", family_name);
        editor.putString("first_name", first_name);
        editor.putString("email", email);
        editor.putInt("age",age);
        editor.putString("address", address);
        editor.apply();
        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);

        startActivity(intent);
        finish();

    }



    @Override
    public void ResponseMethod() {

        Toast.makeText(SignUpActivity.this, "Sign Up Success", Toast.
                LENGTH_SHORT).show();

    }

    @Override
    protected void error1(JSONObject jsonObject) throws JSONException {
        String errorMessage = jsonObject.getString("message");
        Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_SHORT).
                show();
    }

    protected void error2(VolleyError error) {
        Toast.makeText(SignUpActivity.this, "Error occurred: " + error.
                getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Map<String, String> getStringMap(String email, String password, String age, String address, String firstName, String familyName) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("age", age);
        params.put("address", address);
        params.put("first_name", firstName);
        params.put("family_name", familyName);
        return params;
    }
}