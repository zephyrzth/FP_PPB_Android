package com.example.sejamu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText etNama, etEmail, etAlamat;
    TextView tvLatitude, tvLongitude;
    Button btnCari, btnDaftar, btnBatal;

    private ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrasi);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        etNama = (EditText) findViewById(R.id.etNama);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etAlamat = (EditText) findViewById(R.id.etAlamat);
        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        btnCari = (Button) findViewById(R.id.btnCari);
        btnDaftar = (Button) findViewById(R.id.btDaftar);
        btnBatal = (Button) findViewById(R.id.btBatal);

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder geocoder = new Geocoder(RegistrationActivity.this);

                try {
                    List<Address> daftar = geocoder.getFromLocationName(etAlamat.getText().toString(), 1);
                    Log.d("DEBUG_TAG", "Hasil: " + daftar.toString());
                    Address alamat = daftar.get(0);
                    String strAlamat = alamat.getAddressLine(0);
                    Double latitudeCari = alamat.getLatitude();
                    Double longitudeCari = alamat.getLongitude();
                    LatLng lokasi = new LatLng(latitudeCari, longitudeCari);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasi, 15.0f));
                    tvLatitude.setText(String.valueOf(latitudeCari));
                    tvLongitude.setText(String.valueOf(longitudeCari));
                } catch (Exception ie) {
                    Toast.makeText(RegistrationActivity.this, "Alamat tidak ditemukan", Toast.LENGTH_LONG).show();
                    Log.d("DEBUG_TAG", "ERROR: " + ie.getMessage());
                }
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignUp();
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUp();
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ITS = new LatLng(-7.28,112.79);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ITS, 10));

        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                LatLng latLng = googleMap.getCameraPosition().target;
                final TextView tvLatitude = (TextView) findViewById(R.id.tvLatitude);
                final TextView tvLongitude = (TextView) findViewById(R.id.tvLongitude);
                tvLatitude.setText(String.valueOf(latLng.latitude));
                tvLongitude.setText(String.valueOf(latLng.longitude));
            }
        });
    }

    public void performSignUp() {

        String name = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String alamat = etAlamat.getText().toString();
        String latitude = tvLatitude.getText().toString();
        String longitude = tvLongitude.getText().toString();

        // Here you can call you API
        Call<PostPutDelUser> postUserCall = mApiInterface.register(name, email, latitude, longitude);
        postUserCall.enqueue(new Callback<PostPutDelUser>() {
            @Override
            public void onResponse(Call<PostPutDelUser> call, Response<PostPutDelUser> response) {
                if (response.isSuccessful()) {
                    PostPutDelUser result = response.body();
                    Toast.makeText(RegistrationActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();

                } else {
                    try {
                        String responseBodyString = response.errorBody().string();
                        Log.d(MainActivity.DEBUG_TAG, responseBodyString);
                        JSONObject jsonObject = new JSONObject(responseBodyString);

                        Toast.makeText(RegistrationActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.d(MainActivity.DEBUG_TAG, "Error Body JSON: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<PostPutDelUser> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Terjadi kesalahan", Toast.LENGTH_LONG).show();
                Log.d(MainActivity.DEBUG_TAG, "Error LoginActivity: " + t.getMessage());
            }
        });

    }

    public void goToSignUp() {
        // Open your SignUp Activity if the user wants to signup
        // Visit this article to get SignupActivity code https://handyopinion.com/signup-activity-in-android-studio-kotlin-java/
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}