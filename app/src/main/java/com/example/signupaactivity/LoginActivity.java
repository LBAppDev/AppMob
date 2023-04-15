package com.example.signupaactivity;
 import android.content.ComponentName;
 import android.content.Context;
 import android.content.Intent;
 import android.content.ServiceConnection;
 import android.content.SharedPreferences;
 import android.os.Bundle;
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

public class LoginActivity extends Connection {

    private EditText emailEditText;

    private EditText passwordEditText;

    private Button loginButton;
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
    private String status;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    page="login.php";
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.login_email);

        passwordEditText = findViewById(R.id.login_password);

        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                String email = emailEditText.getText().toString();

                String password = passwordEditText.getText().toString();

                try {//+1
                    connectt(email, password, "", "", "", "" );
                } catch (SocketException e) {//+1
                    throw new RuntimeException(e);
                } catch (IOException e) {//+1
                    throw new RuntimeException(e);
                }

            }//3

        });

    }


    public void ResponseMethod(JSONObject jsonObject) throws JSONException {

        JSONObject userObject = jsonObject.getJSONObject("user_info");
                String family_name = userObject.getString("family_name");
                String first_name = userObject.getString("first_name");
                String email = userObject.getString("email");
                int age = userObject.getInt("age");
                String address = userObject.getString("address");
// Save user information to shared preferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                LoginActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("family_name", family_name);
                editor.putString("first_name", first_name);
                editor.putString("email", email);
                editor.putInt("age",age);
                editor.putString("address", address);
                editor.apply();
        
                status = "success";
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

        startActivity(intent);

        finish();
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

    @Override
    public void ResponseMethod() {

    }

    protected void error1(JSONObject jsonObject) throws JSONException {
                String errorMessage = jsonObject.getString("message");
        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).
                show();
        status = "error";
    }

    protected void error2(VolleyError error) {
        Toast.makeText(LoginActivity.this, "Error occurred: " + error.
                getMessage(), Toast.LENGTH_SHORT).show();
        status = "error";
    }

    protected Map<String, String> getStringMap(String email, String password, String age, String address, String firstName, String familyName) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        return params;
    }

    protected void ResponseExist(){

    }

    public String getLastStatus() {
        return this.status;
    }

}