package com.example.appreporte.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appreporte.Interface.InterfaceRegistro;
import com.example.appreporte.Model.User;
import com.example.appreporte.Presenter.RegistroPresenter;
import com.example.appreporte.R;

public class EditPerfilActivity extends AppCompatActivity  implements View.OnClickListener, InterfaceRegistro {


    ProgressDialog progressDialog;
    private RegistroPresenter registroPresenter;

    android.app.AlertDialog.Builder builder;
    AlertDialog alert;


    EditText tvnonbres,tvapellidos,tvphone,tvocrreo;
    Button btnactualizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil);

        registroPresenter= new RegistroPresenter(this);
        initInputs();
        callInfo();
    }

    private void callInfo() {
        SharedPreferences sharedPreferences2 = getSharedPreferences("USER", MODE_PRIVATE);
        String usuario_id =sharedPreferences2.getString("id", "");
        registroPresenter.InfoUser(usuario_id);
    }

    private void initInputs() {

        tvnonbres=(EditText)findViewById(R.id.tvnonbres);
        tvapellidos=(EditText)findViewById(R.id.tvapellidos);
        tvphone=(EditText)findViewById(R.id.tvphone);
        tvocrreo=(EditText)findViewById(R.id.tvocrreo);
        btnactualizar=(Button) findViewById(R.id.btnactualizar);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void showLoading(boolean state) {

    }

    @Override
    public void onRequestSuccess(User object) {
        if (object!=null){
            tvnonbres.setText(object.getName());
            tvapellidos.setText(object.getLast_name());
            tvphone.setText(object.getPhone());
            tvocrreo.setText(object.getEmail());
        }
    }

    @Override
    public void onRequestError(Object object) {

    }

    @Override
    public void onFail(String message) {

    }

    @Override
    public Context getContext() {
        return this;
    }
}