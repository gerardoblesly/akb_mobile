package com.gerardoleonelbleslylontaan.akb_mobile.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gerardoleonelbleslylontaan.akb_mobile.R;
import com.gerardoleonelbleslylontaan.akb_mobile.entity.Menu;
import java.util.List;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.MenuViewHolder>  {

    private List<Menu> listMenu;
    private Context context;
    public int eventIdTemp;

    public AdapterMenu(List<Menu> listMenu, Context context) {
        this.listMenu = listMenu;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_menu, parent, false);
        return new MenuViewHolder(view);
    }

    public void filtering(List<Menu> listMenu)
    {
        this.listMenu = listMenu;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        final Menu menu = listMenu.get(position);
        holder.nama_menu.setText(menu.getNama_menu());
        holder.deskripsi.setText(menu.getDeskripsi());
        holder.harga.setText(menu.getHarga());
        holder.kategori.setText(menu.getKategori());
    }

    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_menu = itemView.findViewById(R.id.tvNamaMenu);
            deskripsi = itemView.findViewById(R.id.tvDeskripsi);
            harga = itemView.findViewById(R.id.tvHarga);
            kategori = itemView.findViewById(R.id.tvKategori);

        }
        TextView nama_menu, deskripsi, harga, kategori;
    }

//    public void deleteEvent(){
//        RequestQueue queue = Volley.newRequestQueue(context);
//
//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("loading....");
//        progressDialog.setTitle("Menghapus data event");
//        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();
//
//        //Memulai membuat permintaan request menghapus data ke jaringan
//        StringRequest stringRequest = new StringRequest(DELETE, EventAPI.URL_DELETE_EVENT + eventIdTemp, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
//                progressDialog.dismiss();
//                try {
//                    //Mengubah response string menjadi object
//                    JSONObject obj = new JSONObject(response);
//                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
//                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
//                    notifyDataSetChanged();
//                    mListener.deleteItem(true);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //Disini bagian jika response jaringan terdapat ganguan/error
//                progressDialog.dismiss();
//                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
//        queue.add(stringRequest);
//    }

}