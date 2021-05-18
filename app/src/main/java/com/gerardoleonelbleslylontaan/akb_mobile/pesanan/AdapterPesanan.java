package com.gerardoleonelbleslylontaan.akb_mobile.pesanan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gerardoleonelbleslylontaan.akb_mobile.R;
import com.gerardoleonelbleslylontaan.akb_mobile.entity.Menu;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterPesanan extends RecyclerView.Adapter<AdapterPesanan.PesananViewHolder>  {

    private List<Menu> listMenu;
    private Context context;
    private IClickable iClickable;

    public AdapterPesanan(List<Menu> listMenu, Context context, IClickable iClickable) {
        this.listMenu = listMenu;
        this.context = context;
        this.iClickable = iClickable;
    }

    @NonNull
    @Override
    public PesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_pesanan, parent, false);
        return new PesananViewHolder(view);
    }

    public void filtering(List<Menu> listMenu)
    {
        this.listMenu = listMenu;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PesananViewHolder holder, int position) {
        final Menu menu = listMenu.get(position);
        holder.nama_menu.setText(menu.getNama_menu());
//        holder.deskripsi.setText(menu.getDeskripsi());
        holder.harga.setText(convertToCurrency(menu.getHarga()));
        Glide.with(holder.itemView.getContext()).load("http://192.168.18.6:8000/urlPhoto/"+ menu.getUrlPhoto()).into(holder.urlPhoto);
//        holder.kategori.setText(menu.getKategori());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickable.onMenuClick(menu);
            }
        });
    }

    public String convertToCurrency(String value) {
        Integer val = Integer.valueOf(value);
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return "Rp " + formatter.format(val);
    }

    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    public interface IClickable{
        void onMenuClick(Menu menu);
    }

    public class PesananViewHolder extends RecyclerView.ViewHolder{

        public PesananViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_menu = itemView.findViewById(R.id.tvNamaMenu);
//            deskripsi = itemView.findViewById(R.id.tvDeskripsi);
            harga = itemView.findViewById(R.id.tvHarga);
//            kategori = itemView.findViewById(R.id.tvKategori);
            urlPhoto = itemView.findViewById(R.id.urlPhoto);
        }
        TextView nama_menu, deskripsi, harga, kategori;
        ImageView urlPhoto;
    }

}