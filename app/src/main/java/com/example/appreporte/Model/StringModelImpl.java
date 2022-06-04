package com.example.appreporte.Model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appreporte.Interface.OnStringListener;
import com.example.appreporte.LoginActivity;

import org.json.JSONObject;

public class StringModelImpl {
    private Context context;
    JsonObjectRequest jsonObjectRequest;
    public StringModelImpl(Context context) {
        this.context = context;
    }
    private  void  dd(){
//        String url="http://192.168.1.209:8000/elemento";
//        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//
//        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                Log.e("respone :" ,response.toString());
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//             //   Toast.makeText(LoginActivity.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
//                Log.e("Error :" ,error.toString());
//            }
//        });
//        queue.add(jsonObjectRequest);
    }
    public void load(String url, final OnStringListener listener) {
//        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                listener.onSuccess(s);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                listener.onError(volleyError);
//                Toast.makeText(context, volleyError.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });


        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onSuccess("response");
                Log.e("respone :" ,response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(LoginActivity.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error :" ,error.toString());
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        VolleyConf.getVolleySingleton(context).addToRequestQueue(jsonObjectRequest);
    }
}
