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

//    public void setFirstTimeLaunch(boolean isFirstTime) {
//        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("IsFirstTimeLaunch", isFirstTime);
//        editor.apply();
//    }

//    public boolean isFirstTimeLaunch() {
//        return sharedPreferences.getBoolean("IsFirstTimeLaunch", true);
//    }

//    public void setToken(String token) {
//        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("token", token);
//        editor.apply();
//    }

//    public String getToken() {
//        return sharedPreferences.getString("token", "");
//    }

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