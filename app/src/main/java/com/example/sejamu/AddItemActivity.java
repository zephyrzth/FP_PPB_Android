package com.example.sejamu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_WRITE = 223;
    public static final int MY_CAMERA_CODE = 222;

    private ApiInterface mApiInterface;

    private String nmFile;
    private ImageView iv;
    private Bitmap bm;
    private Button buttonUpload, buttonAmbil;
    private ImageButton btCloseAdd;
    private EditText editTextJudul, editTextHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        askPermission();
        initWidgets();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MY_CAMERA_CODE) {
            prosesKamera();
        }
    }

    private void initWidgets() {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        iv = (ImageView) findViewById(R.id.imageViewTambah);
        editTextJudul = (EditText) findViewById(R.id.editTextJudul);
        editTextHarga = (EditText) findViewById(R.id.editTextHarga);
        buttonUpload = (Button) findViewById(R.id.buttonSimpanTambah);
        buttonUpload.setEnabled(false);
        buttonUpload.setOnClickListener(operasi);
        buttonAmbil = (Button) findViewById(R.id.buttonAmbilTambah);
        buttonAmbil.setOnClickListener(operasi);
        btCloseAdd = (ImageButton) findViewById(R.id.btCloseAdd);
        btCloseAdd.setOnClickListener(operasi);
    }

    private View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonAmbilTambah:
                    hideKeyboard(view);
                    ambilGambar();
                    break;
                case R.id.buttonSimpanTambah:
                    hideKeyboard(view);
                    uploadGambar();
                    break;
                case R.id.btCloseAdd:
                    finish();
                    break;
            }
        }
    };

    private void hideKeyboard(View view) {
        InputMethodManager a = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        a.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean validateNullText(EditText t) {
        if (t.getText().toString().trim().matches("")) {
            t.setError("Field required!");
            return false;
        }
        return true;
    }

    private void askPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE);
            }
        }
    }

    private void ambilGambar() {
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Hasil");
        imagesFolder.mkdirs();

        Date d = new Date();
        CharSequence s  = DateFormat.format("dd-MM-yyyy_hh-mm-ss", d.getTime());
        nmFile = imagesFolder + File.separator +  s.toString() + ".jpg";
        File image = new File(nmFile);
        Uri uriSavedImage = FileProvider.getUriForFile(AddItemActivity.this, "com.example.sejamu.provider", image);

        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(it, MY_CAMERA_CODE);
    }

    private void prosesKamera() {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            bm = BitmapFactory.decodeFile(nmFile,options);
            try {
                ExifInterface exif = new ExifInterface(nmFile);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                }
                else if (orientation == 3) {
                    matrix.postRotate(180);
                }
                else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true); // rotating bitmap
                iv.setImageBitmap(bm);
            }
            catch (Exception e) {
                Log.d(MainActivity.DEBUG_TAG, e.getMessage());
            }

            buttonUpload.setEnabled(true);
        } catch (Exception e) {
            Log.d(MainActivity.DEBUG_TAG, e.getMessage());
        }
    }

    private void uploadGambar() {
        if (!validateNullText(editTextJudul) || !validateNullText(editTextHarga)) return;
        String judul = editTextJudul.getText().toString();
        int harga = Integer.parseInt(editTextHarga.getText().toString());
        Date d = new Date();
        CharSequence timeNow  = DateFormat.format("dd-MM-yyyy_hh-mm-ss", d.getTime());
        String filename = UserItem.MY_ID + "_" + timeNow.toString() + ".jpg";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String photofile = Base64.encodeToString(byteArray, Base64.DEFAULT);

        Call<PostPutDelItem> postItemCall = mApiInterface.storeItem("Bearer " + UserItem.MY_TOKEN, judul, harga, filename, photofile);
        postItemCall.enqueue(new Callback<PostPutDelItem>() {
            @Override
            public void onResponse(Call<PostPutDelItem> call, Response<PostPutDelItem> response) {
                if (response.isSuccessful()) {
                    PostPutDelItem storeResult = response.body();
                    Toast.makeText(AddItemActivity.this, storeResult.getMessage(), Toast.LENGTH_SHORT).show();

                    buttonUpload.setEnabled(false);
                    iv.setImageBitmap(null);
                    editTextJudul.setText("");
                    editTextHarga.setText("");
                } else {
                    try {
                        String responseBodyString = response.errorBody().string();
                        Log.d(MainActivity.DEBUG_TAG, responseBodyString);
                        JSONObject jsonObject = new JSONObject(responseBodyString);

                        Toast.makeText(AddItemActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.d(MainActivity.DEBUG_TAG, "Error Body JSON: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<PostPutDelItem> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, "Terjadi kesalahan", Toast.LENGTH_LONG).show();
                Log.d(MainActivity.DEBUG_TAG, "Error Retrofit Store: " + t.getMessage());
            }
        });
    }
}