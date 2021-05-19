package com.gerardoleonelbleslylontaan.akb_mobile.menu;

import android.content.Context;

public class SharedPref {
    android.content.SharedPreferences sharedPreferences;

    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }

    public void setIsLogin(boolean isLogin) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", isLogin);
        editor.apply();
    }

    public boolean loadIsLogin() {
        return sharedPreferences.getBoolean("isLogin", false);
    }


    public void setIdReservasi(String idReservasi) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idReservasi", idReservasi);
        editor.apply();
    }

    public void setNama(String nama) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nama", nama);
        editor.apply();
    }

    public void setNoMeja(String noMeja) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("noMeja", noMeja);
        editor.apply();
    }

    public String getIdReservasi() {
        return sharedPreferences.getString("idReservasi", "");
    }
}