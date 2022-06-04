package com.example.appreporte;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appreporte.Interface.ILoginView;
import com.example.appreporte.Model.User;
import com.example.appreporte.Presenter.LoginPresenter;

import org.json.JSONObject;

//import com.


public class LoginActivity extends AppCompatActivity  implements View.OnClickListener, ILoginView {


    private static final String TAG ="LoginActivity";
    Button btniniciar,btnregistrar;
    JsonObjectRequest jsonObjectRequest;

    ProgressDialog progressDialog;
    private LoginPresenter loginPresenter;
    EditText etcorreo,etpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initinput();
        btniniciar =(Button) findViewById(R.id.btniniciar);
        btnregistrar=(Button)findViewById(R.id.btnregistrarse);
        loginPresenter= new LoginPresenter(this);
        btnregistrar.setOnClickListener(this);
        btniniciar.setOnClickListener(this);


    }

    private void initinput() {
        etcorreo=(EditText)findViewById(R.id.etcorreo);
        etpassword=(EditText)findViewById(R.id.inputpassword);
    }

    private void login(String email, String password)
    {
        if (TextUtils.isEmpty(email)){
            etcorreo.setError("campo necesario");
        }
        else if (TextUtils.isEmpty(password)){
            etpassword.setError("campo necesario");
        }else{
            showLoading(true);
            loginPresenter.login(email,password);
        }
    }

    private void nouso() {
        String url="http://192.168.1.209:8000/elemento";
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("respone :" ,response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error :" ,error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btniniciar:
                String email =etcorreo.getText().toString();
                String password=etpassword.getText().toString();
                login(email,password);
                break;
            case  R.id.btnregistrarse:
                startActivity(new Intent(this,RegistroActivity.class));
                break;
        }
    }

    @Override
    public void showLoading(boolean state) {

//        progressDialog= new ProgressDialog(this);
//        if (state){
//            progressDialog.setTitle("Login");
//            progressDialog.setMessage("Cargando");
//            progressDialog.show();
//        }else{
//            progressDialog.dismiss();
//        }
    }

    @Override
    public void onRequestSuccess(User object) {
        saveStorage(object);
        gotoHome();
    }

    @Override
    public void onRequestError(Object object) {

    }

    @Override
    public void onFail(String message) {
        Toast.makeText(this , message, Toast.LENGTH_SHORT).show();
//        Alerter.create(this@MisCitasActivity)
//            .setTitle("Mensaje")
//                .setText(message)
//                .setIcon(R.drawable.ic_check)
//                .setBackgroundColorRes(R.color.verde)
//                .setIconColorFilter(0) // Optional - Removes white tint
//                .show()
    }

    @Override
    public Context getContext() {
        return this;
    }
    private void gotoHome() {
        startActivity(new Intent(this,PrincipalActivity.class));
        finish();
    }

    private  void  saveStorage(User user){
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putString("id", user.getId().toString());
        editor.putString("name", user.getName());
        editor.putString("last_name", user.getLast_name());
        editor.putString("lat", user.getLat());
        editor.putString("lon", user.getLon());
        editor.putString("photo", user.getPhoto());
        editor.apply();
    }
}