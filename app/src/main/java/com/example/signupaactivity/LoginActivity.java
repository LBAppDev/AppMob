package com.example.signupaactivity;
 import android.content.Intent;
 import android.os.Bundle;
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

 import java.util.HashMap;
 import java.util.Map;

public class LoginActivity extends Connection {

    private EditText emailEditText;

    private EditText passwordEditText;

    private Button loginButton;

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

                connectt(email, password, "", "");

            }

        });

    }


    public void ResponseMethod() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

        startActivity(intent);

        finish();
    }

    protected void error1(String response) {
        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).
                show();
    }

    protected void error2(VolleyError error) {
        Toast.makeText(LoginActivity.this, "Error occurred: " + error.
                getMessage(), Toast.LENGTH_SHORT).show();
    }

    protected Map<String, String> getStringMap(String email, String password, String age, String address) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        return params;
    }

    protected void ResponseExist(){

    }

}