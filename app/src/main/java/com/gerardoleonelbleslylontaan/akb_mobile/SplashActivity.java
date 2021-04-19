package com.gerardoleonelbleslylontaan.akb_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gerardoleonelbleslylontaan.akb_mobile.databinding.ActivitySplashBinding;
import com.gerardoleonelbleslylontaan.akb_mobile.menu.MenuActivity;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    startActivity(new Intent(SplashActivity.this, MenuActivity.class));
                finish();
            }
        },5000);
    }

    private void navigate(Class destination) {

    }
}