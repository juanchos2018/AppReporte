package com.example.appreporte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appreporte.fragment.MainFragment;

public class ContenedorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor);


        MainFragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();

    }
}