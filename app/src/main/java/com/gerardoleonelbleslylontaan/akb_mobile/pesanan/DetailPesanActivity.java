package com.gerardoleonelbleslylontaan.akb_mobile.pesanan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gerardoleonelbleslylontaan.akb_mobile.R;
import com.gerardoleonelbleslylontaan.akb_mobile.entity.Menu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

public class DetailPesanActivity extends AppCompatActivity {

    TextView kuantitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesan);

        Menu menu = (Menu)getIntent().getSerializableExtra("Menu");

        TextView namaMenu, deskripsi, harga;
        ImageView urlPhoto;
        FloatingActionButton btnBack;
        ImageButton btnMinus, btnPlus;

        namaMenu = findViewById(R.id.tvNamaMenu);
        deskripsi = findViewById(R.id.tvDeskripsi);
        harga = findViewById(R.id.tvHarga);
        urlPhoto = findViewById(R.id.urlPhoto);
        btnBack = findViewById(R.id.btnBack);

        kuantitas = findViewById(R.id.tvKuantitas);
        kuantitas.setText(String.valueOf(menu.getKuantitas()));
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer intKuantitas = Integer.parseInt(kuantitas.getText().toString());
                if(intKuantitas!=0)
                {
                    intKuantitas--;
                    kuantitas.setText(intKuantitas.toString());
                }
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer intKuantitas = Integer.parseInt(kuantitas.getText().toString());
                intKuantitas++;
                kuantitas.setText(intKuantitas.toString());
            }
        });

        namaMenu.setText(menu.getNama_menu());
        deskripsi.setText(menu.getDeskripsi());
        harga.setText(convertToCurrency(menu.getHarga()));
        Glide.with(this).load("http://192.168.18.6:8000/urlPhoto/"+ menu.getUrlPhoto()).into(urlPhoto);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Kuantitas", kuantitas.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public String convertToCurrency(String value) {
        Integer val = Integer.valueOf(value);
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return "Rp " + formatter.format(val);
    }
}