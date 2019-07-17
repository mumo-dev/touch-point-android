package com.example.touchpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.touchpoint.api.ApiClient;
import com.example.touchpoint.models.LoginResponse;
import com.example.touchpoint.models.User;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailField, passwordField;
    ProgressDialog progressDialog;

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Sign in");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in ...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
//        progressDialog.show();

        emailField = findViewById(R.id.ed_email);
        passwordField = findViewById(R.id.ed_password);

        Button loginButton = findViewById(R.id.btn_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    return;
                }

                progressDialog.show();

                User user = new User(email, password);
                ApiClient.getClient()
                        .loginUser(user)
                        .enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                progressDialog.dismiss();
                                if (response.isSuccessful()) {
                                    if (response.code() == 200) {
                                        LoginResponse response1 = response.body();
                                        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("access_token", response1.getAccess_token());
                                        editor.putString("username", response1.getUser().getName());
                                        editor.putInt("id", response1.getUser().getId());
                                        editor.apply();

                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    }


                                } else if (response.code() == 401) {
                                    Toast.makeText(LoginActivity.this,
                                            "Check your credentials and try again", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(LoginActivity.this,
                                            "An error occurred. try again", Toast.LENGTH_LONG).show();
                                }
                                Log.d(TAG, "Login response: " + response.code());
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                progressDialog.dismiss();
                                Log.e(TAG, "Login  failuer: ", t);
                                Toast.makeText(LoginActivity.this,
                                        "An error occurred. try again", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });


    }
}
