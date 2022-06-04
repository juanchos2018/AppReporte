package com.example.appreporte;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appreporte.Activity.UbicacionActivity;
import com.example.appreporte.Interface.InterfaceRegistro;
import com.example.appreporte.Model.User;
import com.example.appreporte.Presenter.LoginPresenter;
import com.example.appreporte.Presenter.RegistroPresenter;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener, InterfaceRegistro {


    private static final String TAG ="RegistroActivity" ;
    Button btnubicacion;
    ImageView imgSaveUser;
    EditText etNombre,etApellido,etDni,etPhone,etEmail,etPassword,etLocation;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 1;

    ProgressDialog progressDialog;
    private RegistroPresenter registroPresenter;

    android.app.AlertDialog.Builder builder;
    AlertDialog alert;
    String lati,longi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        initInputs();
        registroPresenter= new RegistroPresenter(this);
        imgSaveUser.setOnClickListener(this);
        btnubicacion.setOnClickListener(this);

    }

    private void initInputs() {

        etNombre=(EditText)findViewById(R.id.etNombre);
        etApellido=(EditText)findViewById(R.id.etApellido);
        etDni=(EditText)findViewById(R.id.etDni);
        etPhone=(EditText)findViewById(R.id.etPhone);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etLocation=(EditText)findViewById(R.id.etLocation);
        btnubicacion=(Button)findViewById(R.id.btnubicacion);
        imgSaveUser=(ImageView) findViewById(R.id.imgSaveUser);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnubicacion:

                Intent intent = new Intent(this, UbicacionActivity.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
                break;

            case   R.id.imgSaveUser:

                String name=etNombre.getText().toString();
                String las_name=etApellido.getText().toString();
                String number_document =etDni.getText().toString();
                String phone=etPhone.getText().toString();
                String email=etEmail.getText().toString();
                String password=etPassword.getText().toString();
                String location=etLocation.getText().toString();
                storeUser(name,las_name,number_document,phone,email,password,location);

                break;

        }
    }

    private void storeUser(String name, String las_name, String number_document, String phone, String email, String password, String location) {

        if (TextUtils.isEmpty(name)) {
            etNombre.setError("Campo necesario");
        }
        else if (TextUtils.isEmpty(las_name)){
            etApellido.setError("Campo necesario");
        }
        else if (TextUtils.isEmpty(number_document)){
            etDni.setError("campo necsario");
        }
        else if (TextUtils.isEmpty(phone)){
            etPhone.setError("Campo necesario");
        }
        else if (TextUtils.isEmpty(email)){
            etEmail.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(password)){
            etPassword.setError("campo necesario");
        }else{
            location="Plaza Vigil, Tacna";
            User user = new User(name,las_name,email,phone,location,lati,longi,"default",1,number_document,password);
            registroPresenter.storeUer(user);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                lati = data.getStringExtra("lat");
                longi = data.getStringExtra("lon");
                Toast.makeText(this, "lati:" +lati, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void message(String mensaje){
        builder = new AlertDialog.Builder(getContext());
        Button btcerrrar;
        TextView tvestado;
        View v = LayoutInflater.from(getContext()).inflate(R.layout.alert_message, null);
        btcerrrar=(Button)v.findViewById(R.id.idbtncerrardialogo);
        tvestado=(TextView)v.findViewById(R.id.idestado);
        tvestado.setText(mensaje);
        builder.setView(v);
        btcerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                clearAndRedirect();
            }
        });
        alert  = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }

    private  void  clearAndRedirect(){
        finish();
    }
    @Override
    public void showLoading(boolean state) {
        //progressDialog= new ProgressDialog(RegistroActivity.this);
//        if (state==true){
//            progressDialog.setTitle("Registro");
//            progressDialog.setMessage("Cargando...");
//            progressDialog.show();
//
//        }else{
//            progressDialog.dismiss();
//        }
        Log.e(TAG, "state "+state);
    }

    @Override
    public void onRequestSuccess(User object) {
     //   progressDialog.dismiss();
        message("Registrado");
    }

    @Override
    public void onRequestError(Object object) {
        Toast.makeText(this, "Error ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }
}