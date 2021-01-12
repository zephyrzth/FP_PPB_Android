package com.example.sejamu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    final int MIN_PASSWORD_LENGTH = 6;
    private ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        viewInitializations();
    }

    void viewInitializations() {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btMasuk);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSignUp();
            }
        });

        // To show back button in actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Checking if the input in form is valid
    boolean validateInput() {

        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Please Enter Email");
            return false;
        }
        if (etPassword.getText().toString().equals("")) {
            etPassword.setError("Please Enter Password");
            return false;
        }

        // checking the proper email format
        if (!isEmailValid(etEmail.getText().toString())) {
            etEmail.setError("Please Enter Valid Email");
            return false;
        }

        // checking minimum password Length
        if (etPassword.getText().length() < MIN_PASSWORD_LENGTH) {
            etPassword.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters");
            return false;
        }

        return true;
    }

    boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Hook Click Event

    public void performSignUp () {
        if (validateInput()) {
            // Input is valid, here send data to your server

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            // Here you can call you API
            Call<PostPutDelUser> postUserCall = mApiInterface.login(email, password);
            postUserCall.enqueue(new Callback<PostPutDelUser>() {
                @Override
                public void onResponse(Call<PostPutDelUser> call, Response<PostPutDelUser> response) {
                    if (response.isSuccessful()) {
                        PostPutDelUser result = response.body();

                        UserItem.MY_TOKEN = result.dataUser.getToken();
                        Log.d(MainActivity.DEBUG_TAG, result.getDataItem().getToken());
                    } else {
                        try {
                            String responseBodyString = response.errorBody().string();
                            Log.d(MainActivity.DEBUG_TAG, responseBodyString);
                            JSONObject jsonObject = new JSONObject(responseBodyString);

                            Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.d(MainActivity.DEBUG_TAG, "Error Body JSON: " + e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<PostPutDelUser> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Terjadi kesalahan", Toast.LENGTH_LONG).show();
                    Log.d(MainActivity.DEBUG_TAG, "Error LoginActivity: " + t.getMessage());
                }
            });

        }
    }

//    public void goToSignup(View v) {
//        // Open your SignUp Activity if the user wants to signup
//        // Visit this article to get SignupActivity code https://handyopinion.com/signup-activity-in-android-studio-kotlin-java/
//        Intent intent = new Intent(this, SignupActivity.class);
//        startActivity(intent);
//    }
}
