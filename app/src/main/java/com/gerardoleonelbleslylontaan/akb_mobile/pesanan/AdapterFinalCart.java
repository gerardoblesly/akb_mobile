package com.gerardoleonelbleslylontaan.akb_mobile.pesanan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gerardoleonelbleslylontaan.akb_mobile.R;
import com.gerardoleonelbleslylontaan.akb_mobile.entity.Cart;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterFinalCart extends RecyclerView.Adapter<AdapterFinalCart.FinalCartViewHolder>  {

    private List<Cart> listCart;
    private Context context;

    public AdapterFinalCart(List<Cart> listCart, Context context) {
        this.listCart = listCart;
        this.context = context;
    }

    @NonNull
    @Override
    public FinalCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_final_cart, parent, false);
        return new FinalCartViewHolder(view);
    }

    public void filtering(List<Cart> listCart)
    {
        this.listCart = listCart;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull FinalCartViewHolder holder, int position) {
        final Cart cart = listCart.get(position);
        holder.nama_menu.setText(cart.getNama_menu());
        holder.total_harga.setText(convertToCurrency(cart.getTotal_harga()));
        holder.jumlah.setText(cart.getJumlah());
    }

    public String convertToCurrency(String value) {
        Integer val = Integer.valueOf(value);
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return "Rp " + formatter.format(val);
    }


    @Override
    public int getItemCount() {
        return listCart.size();
    }


    public class FinalCartViewHolder extends RecyclerView.ViewHolder{

        public FinalCartViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_menu = itemView.findViewById(R.id.tvNamaMenu);
            jumlah = itemView.findViewById(R.id.tvJumlah);
            total_harga = itemView.findViewById(R.id.tvTotalHarga);

        }
        TextView nama_menu, jumlah, total_harga;
    }

}