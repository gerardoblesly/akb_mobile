package com.gerardoleonelbleslylontaan.akb_mobile.pesanan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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
import com.gerardoleonelbleslylontaan.akb_mobile.entity.Cart;
import com.gerardoleonelbleslylontaan.akb_mobile.entity.Menu;
import com.gerardoleonelbleslylontaan.akb_mobile.menu.MenuActivity;
import com.gerardoleonelbleslylontaan.akb_mobile.menu.SharedPref;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.GET;

public class FinalCartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterFinalCart adapter;
    MaterialButton btnBayar, btnBack;
    Context context;
    SharedPref sharedPref;
    private List<Cart> listCart;
    TextView displayTotalHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_cart);
        sharedPref = new SharedPref(this);
        context = this.getApplicationContext();
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnBayar = findViewById(R.id.btnBayar);
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bayarSekarang();
            }
        });

        displayTotalHarga = findViewById(R.id.tvTotalHargaSemuanya);

        setAdapter();
        getAllList();
    }

    public void setAdapter(){
        listCart = new ArrayList<Cart>();
        recyclerView = findViewById(R.id.rvFinalCart);
        adapter = new AdapterFinalCart(listCart, this.getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void getAllList()
    {
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, MenuAPI.URL_GET_ORDER + sharedPref.getIdReservasi(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int tempTotal=0;
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    System.out.println("TEST DISINI ARKIN QUEUE");
                    System.out.println(jsonArray.length());
                    tempTotal = response.getInt("total_semuanya");

                    if (!listCart.isEmpty())
                        listCart.clear();

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        String nama_menu = jsonObject.optString("nama_menu");
                        String total_harga = jsonObject.optString("total_harga");
                        String jumlah = jsonObject.optString("jumlah");

                        Cart cart = new Cart(nama_menu, jumlah, total_harga);

                        listCart.add(cart);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                displayTotalHarga.setText(convertToCurrency(String.valueOf(tempTotal)));
                Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + BuildConfig.token);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void bayarSekarang()
    {
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, MenuAPI.URL_END_ORDER + sharedPref.getIdReservasi(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println("MASUK TRY");
                sharedPref.setIsLogin(false);
                startActivity(new Intent(FinalCartActivity.this, MenuActivity.class));
                finish();

                Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + BuildConfig.token);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public String convertToCurrency(String value) {
        Integer val = Integer.valueOf(value);
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return "Rp " + formatter.format(val);
    }
}