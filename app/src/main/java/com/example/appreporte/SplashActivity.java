package com.example.appreporte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences2 = getSharedPreferences("USER", MODE_PRIVATE);
                        String usuario_id =sharedPreferences2.getString("id", null);
                        if (usuario_id!=null){
                            startActivity(new Intent(SplashActivity.this, PrincipalActivity.class));
                            finish();
                        }else{
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                }, 1500);


    }
}