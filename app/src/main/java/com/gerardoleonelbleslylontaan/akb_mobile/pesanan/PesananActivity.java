package com.gerardoleonelbleslylontaan.akb_mobile.pesanan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gerardoleonelbleslylontaan.akb_mobile.R;
import com.gerardoleonelbleslylontaan.akb_mobile.api.MenuAPI;
import com.gerardoleonelbleslylontaan.akb_mobile.entity.Menu;
import com.gerardoleonelbleslylontaan.akb_mobile.pesanan.AdapterPesanan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class PesananActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Menu> listMenu;
    private AdapterPesanan adapter;
    SearchView searchView;
    Context context;
    private Menu lastSelectedMenu;
    FloatingActionButton btnOrder;
    Button btnSelesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_pesanan);
        setAdapter();
        getMenu();

        btnSelesai = findViewById(R.id.btnSelesai);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PesananActivity.this, FinalCartActivity.class));
            }
        });


        btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Menu> tempList = new ArrayList<>();
                for(Menu menu: listMenu)
                {
                    if(menu.getKuantitas()!=0)
                    {
                        tempList.add(menu);
                    }
                }
                if(tempList.isEmpty())
                {
                    Toast.makeText(PesananActivity.this, "Pesanan masih kosong!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(PesananActivity.this, OrderActivity.class);
                    intent.putExtra("Order",new Gson().toJson(tempList));
                    startActivityForResult(intent,200);
                }
            }
        });

        searchView = findViewById(R.id.input_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Menu> tempList = new ArrayList<>();
                for(Menu menu : listMenu) {
                    if(menu.getNama_menu().toLowerCase().contains(s.toLowerCase()))
                    {
                        tempList.add(menu);
                    }
                }
                adapter.filtering(tempList);
                adapter.filtering(tempList);
                return true;
            }
        });
    }


    public void setAdapter(){
        listMenu = new ArrayList<Menu>();
        recyclerView = findViewById(R.id.rvMenu);
        adapter = new AdapterPesanan(listMenu, this.getApplicationContext(), new AdapterPesanan.IClickable() {
            @Override
            public void onMenuClick(Menu menu) {
                Intent intent = new Intent(PesananActivity.this, DetailPesanActivity.class);
                intent.putExtra("Menu",menu);
                lastSelectedMenu = menu;
                startActivityForResult(intent, 100);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this.getApplicationContext(),2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==Activity.RESULT_OK)
        {
            if(data!=null)
            {
                String kuantitas = data.getStringExtra("Kuantitas");
                lastSelectedMenu.setKuantitas(Integer.parseInt(kuantitas));
            }
        }

        if(requestCode==200 && resultCode==Activity.RESULT_OK)
        {
            for(Menu menu : listMenu) {
                menu.setKuantitas(0);
            }
        }
    }

    public void getMenu()
    {
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, MenuAPI.URL_SELECT_EVENT,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    if (!listMenu.isEmpty())
                        listMenu.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        int id = jsonObject.optInt("id");
                        String nama_menu = jsonObject.optString("nama_menu");
                        String deskripsi = jsonObject.optString("deskripsi");
                        String takaran_saji = jsonObject.optString("takaran_saji");
                        String harga = jsonObject.optString("harga");
                        String kategori = jsonObject.optString("kategori");
                        String unit = jsonObject.optString("unit");
                        String id_bahan = jsonObject.optString("id_bahan");
                        String urlPhoto = jsonObject.optString("urlPhoto");

                        Menu menu = new Menu(id, nama_menu, deskripsi, takaran_saji, harga,kategori, unit, id_bahan, urlPhoto);

                        listMenu.add(menu);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}