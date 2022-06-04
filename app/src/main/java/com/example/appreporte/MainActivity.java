package com.example.appreporte;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    Button btndialogo;
    android.app.AlertDialog.Builder builder1;
    AlertDialog alert1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btndialogo =(Button) findViewById(R.id.btndialogo);
        btndialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });
    }

    private void dialogo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("This is Alert Dialog");
        builder.setMessage("Bottom Alert dialog");

        // set the neutral button to do some actions
        builder.setNeutralButton("DISMISS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Alert Dialog Dismissed", Toast.LENGTH_SHORT).show();
            }
        });

        // set the positive button to do some actions
        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "OKAY", Toast.LENGTH_SHORT).show();
            }
        });

        // set the negative button to do some actions
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "CANCEL", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getWindow().setGravity(Gravity.BOTTOM);

    }
    private  void  dialog(){
        builder1 = new AlertDialog.Builder(MainActivity.this);
        Button btcerrrar;
        TextView tvestado;
        builder1.setTitle("detalle");
        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogo_panico, null);
        builder1.setView(v);
        alert1  = builder1.create();
        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert1.getWindow().setGravity(Gravity.BOTTOM);
        alert1.show();
    }
}