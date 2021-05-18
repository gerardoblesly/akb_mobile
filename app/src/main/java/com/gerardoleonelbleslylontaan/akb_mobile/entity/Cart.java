package com.gerardoleonelbleslylontaan.akb_mobile.entity;

import java.io.Serializable;

public class Cart implements Serializable {

    private String nama_menu;
    private String jumlah;
    private String total_harga;

    public Cart(String nama_menu, String jumlah, String total_harga) {
        this.nama_menu = nama_menu;
        this.jumlah = jumlah;
        this.total_harga = total_harga;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }
}
