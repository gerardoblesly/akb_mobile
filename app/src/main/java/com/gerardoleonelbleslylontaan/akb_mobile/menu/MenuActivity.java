package com.gerardoleonelbleslylontaan.akb_mobile.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Menu> listMenu;
    private AdapterMenu adapter;
    SearchView searchView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_menu);
        setAdapter();
        getMenu();

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
                return true;
            }
        });
    }

    public void setAdapter(){
        listMenu = new ArrayList<Menu>();
        recyclerView = findViewById(R.id.rvMenu);
        adapter = new AdapterMenu(listMenu, this.getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void getMenu()
    {
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this.getApplicationContext());
        progressDialog.setMessage("loading...");
        progressDialog.setTitle("Mengambil semua data menu....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, MenuAPI.URL_SELECT_EVENT,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
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

                            Menu menu = new Menu(id, nama_menu, deskripsi, takaran_saji, harga,kategori, unit, id_bahan);

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
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}