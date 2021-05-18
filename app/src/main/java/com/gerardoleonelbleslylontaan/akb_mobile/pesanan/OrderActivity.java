package com.gerardoleonelbleslylontaan.akb_mobile.pesanan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gerardoleonelbleslylontaan.akb_mobile.BuildConfig;
import com.gerardoleonelbleslylontaan.akb_mobile.R;
import com.gerardoleonelbleslylontaan.akb_mobile.api.MenuAPI;
import com.gerardoleonelbleslylontaan.akb_mobile.entity.Menu;
import com.gerardoleonelbleslylontaan.akb_mobile.menu.SharedPref;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterOrder adapter;
    MaterialButton btnOrder, btnBack;
    Context context;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //dari string
        String menu = getIntent().getStringExtra("Order");

//        dibalikin ke list lagi
        List<Menu> listMenu = Arrays.asList(new Gson().fromJson(menu, Menu[].class));
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnOrder = findViewById(R.id.btnOrder);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrder(listMenu);
            }
        });

        recyclerView = findViewById(R.id.rvOrder);
        adapter = new AdapterOrder(listMenu, this.getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public JSONObject addOrderBody(List<Menu> listMenu)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_transaksi", sharedPref.getIdReservasi());

            JSONArray jsonArray = new JSONArray();
            for(Menu menu: listMenu)
            {
                JSONObject jsonObjectBody = new JSONObject();

                    jsonObjectBody.put("id_menu", String.valueOf(menu.getId()));
                    jsonObjectBody.put("jumlah", String.valueOf(menu.getKuantitas()));
                    jsonArray.put(jsonObjectBody);
            }
            jsonObject.put("data",jsonArray);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void addOrder(List<Menu> listMenu)
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(POST, MenuAPI.URL_ADD_ORDER, addOrderBody(listMenu) , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setResult(Activity.RESULT_OK, new Intent());
                finish();
                    Toast.makeText(OrderActivity.this, "Berhasil menambah order!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + BuildConfig.token);
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }

}