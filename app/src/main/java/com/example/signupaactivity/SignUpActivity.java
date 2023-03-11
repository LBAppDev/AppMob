package com.example.signupaactivity;

import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends Connection {
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText ageEditText;
    private EditText addressEditText;

    private String email;
    private String password;
    private String age;
    private String address;
    private Button signUpButton;
    
    private String localhost = "10.42.0.1";

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
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 email = emailEditText.getText().toString();
                 password = passwordEditText.getText().toString();
                 age = ageEditText.getText().toString();
                address = addressEditText.getText().toString();
                connectt(email, password, age, address);
            }
        });
    }


    protected void ResponseExist(){
        Toast.makeText(SignUpActivity.this, "email already used !", Toast.
                LENGTH_SHORT).show();
    }

    public void ResponseMethod() {
        Toast.makeText(SignUpActivity.this, "Sign Up Success", Toast.
                LENGTH_SHORT).show();
    }

    @Override
    protected void error1(String response) {
        Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).
                show();
    }

    protected void error2(VolleyError error) {
        Toast.makeText(SignUpActivity.this, "Error occurred: " + error.
                getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Map<String, String> getStringMap(String email, String password, String age, String address) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("age", age);
        params.put("address", address);
        return params;
    }
}