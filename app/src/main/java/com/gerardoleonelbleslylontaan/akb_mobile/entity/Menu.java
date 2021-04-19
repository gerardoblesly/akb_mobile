package com.gerardoleonelbleslylontaan.akb_mobile.entity;

import java.io.Serializable;

public class Menu implements Serializable {

    private int id;
    private String nama_menu;
    private String deskripsi;
    private String takaran_saji;
    private String harga;
    private String kategori;
    private String unit;
    private String id_bahan;

    public Menu(int id, String nama_menu, String deskripsi, String takaran_saji, String harga, String kategori, String unit, String id_bahan) {
        this.id = id;
        this.nama_menu = nama_menu;
        this.deskripsi = deskripsi;
        this.takaran_saji = takaran_saji;
        this.harga = harga;
        this.kategori = kategori;
        this.unit = unit;
        this.id_bahan = id_bahan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTakaran_saji() {
        return takaran_saji;
    }

    public void setTakaran_saji(String takaran_saji) {
        this.takaran_saji = takaran_saji;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getId_bahan() {
        return id_bahan;
    }

    public void setId_bahan(String id_bahan) {
        this.id_bahan = id_bahan;
    }
}
