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

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.OrderViewHolder>  {

    private List<Menu> listMenu;
    private Context context;

    public AdapterOrder(List<Menu> listMenu, Context context) {
        this.listMenu = listMenu;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_order, parent, false);
        return new OrderViewHolder(view);
    }

    public void filtering(List<Menu> listMenu)
    {
        this.listMenu = listMenu;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        final Menu menu = listMenu.get(position);
        holder.nama_menu.setText(menu.getNama_menu());
        String tempText = "x" + String.valueOf(menu.getKuantitas());
        holder.kuantitas.setText(tempText);
        Glide.with(holder.itemView.getContext()).load("http://192.168.18.6:8000/urlPhoto/"+ menu.getUrlPhoto()).into(holder.urlPhoto);
    }


    @Override
    public int getItemCount() {
        return listMenu.size();
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder{

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_menu = itemView.findViewById(R.id.tvNamaMenu);
            kuantitas = itemView.findViewById(R.id.tvKuantitas);
            urlPhoto = itemView.findViewById(R.id.urlPhoto);
        }
        TextView nama_menu, kuantitas;
        ImageView urlPhoto;
    }

    public String convertPrefix(String value) {
        return "x" + value;
    }

}