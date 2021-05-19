package com.gerardoleonelbleslylontaan.akb_mobile.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gerardoleonelbleslylontaan.akb_mobile.BuildConfig;
import com.gerardoleonelbleslylontaan.akb_mobile.MainActivity;
import com.gerardoleonelbleslylontaan.akb_mobile.api.MenuAPI;
import com.gerardoleonelbleslylontaan.akb_mobile.pesanan.PesananActivity;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.android.volley.Request.Method.GET;

public class QrCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private static final int PERMISSION_CODE = 1000;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private SharedPref sharedPref;
    String id, name, table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        sharedPref = new SharedPref(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        askCameraPermission(Manifest.permission.CAMERA,
                CAMERA_PERMISSION_CODE);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        mScannerView.stopCamera();
//    }

    public void askCameraPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(QrCodeActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(QrCodeActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(QrCodeActivity.this,
                    "Permission granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(QrCodeActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(QrCodeActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void handleResult(Result rawResult)
    {
        Log.v("TAG", rawResult.getText());
        Log.v("TAG", rawResult.getBarcodeFormat().toString());
        String[] temp = rawResult.getText().split(";");
        System.out.println(temp);
        id = temp[0];
        name = temp[1];
        table = temp[2];
        System.out.println(id);
        System.out.println(name);
        System.out.println(table);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(GET, MenuAPI.URL_QR + id
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Toast.makeText(QrCodeActivity.this, obj.getString("status"), Toast.LENGTH_SHORT).show();
                    if(obj.getString("status").equalsIgnoreCase("1"))
                    {
                        startActivity(new Intent(QrCodeActivity.this, PesananActivity.class));
                        Toast.makeText(QrCodeActivity.this, "Halo, " + name, Toast.LENGTH_SHORT).show();
                        sharedPref.setIdReservasi(id);
                        sharedPref.setNama(name);
                        sharedPref.setNoMeja(table);
                        sharedPref.setIsLogin(true);
                    }
                    else if(obj.getString("status").equalsIgnoreCase("2"))
                    {
                        Toast.makeText(QrCodeActivity.this, "Transaksi Anda Sudah Selesai!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(QrCodeActivity.this, "QR Code tidak teregistrasi!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(QrCodeActivity.this, "KEKW", Toast.LENGTH_SHORT).show();
                }
                finish();
                mScannerView.stopCamera();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QrCodeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(QrCodeActivity.this, "COCOTEH", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + BuildConfig.token);
                return params;
            }
        };;
        queue.add(stringRequest);
    }
}