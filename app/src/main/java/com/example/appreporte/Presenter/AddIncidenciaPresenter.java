package com.example.appreporte.Presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appreporte.Interface.InterfaceAdd;
import com.example.appreporte.Interface.InterfaceRegistro;
import com.example.appreporte.Model.Incidencia;
import com.example.appreporte.Model.User;
import com.example.appreporte.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;

public class AddIncidenciaPresenter {

    private static final String TAG ="AddIncidenciaPresenter" ;
    private InterfaceAdd interfaceAdd;
    private RequestQueue queue;
    private Incidencia objResponse;
    private Context mContext;

    private StorageReference storageReference;
    ProgressDialog progressDialog;

    PusherOptions options = new PusherOptions();

    public AddIncidenciaPresenter(InterfaceAdd interfaceAdd,StorageReference storageReference) {
        this.interfaceAdd = interfaceAdd;
        this.storageReference=storageReference;
        options.setCluster("APP_CLUSTER");

//        Pusher pusher = new Pusher("82e50670bca8ae634294", options);
//        pusher.connect();
    }


    public void uploadPhoto(Incidencia incidence, Uri uriphoto){

        if (uriphoto!=null){
            progressDialog= new ProgressDialog(interfaceAdd.getContext());
            progressDialog.setMessage("Cargando..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StorageReference fotoref=storageReference.child("FotosIncidente").child(uriphoto.getLastPathSegment());
            fotoref.putFile(uriphoto).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  new Exception();
                    }
                    return fotoref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri uridownload =task.getResult();
                        incidence.setPhoto(uridownload.toString());
                        StoreIndicence(incidence);
//                        Map<String,Object> fami= new HashMap<>();
//                        fami.put("urlfoto",uridownload.toString());
                    }
                }
            });
        }else{
            Toast.makeText(interfaceAdd.getContext(), "No selecciono Imagen ", Toast.LENGTH_SHORT).show();
        }
    }

    private  void  StoreIndicence(Incidencia incidencia){
        queue = Volley.newRequestQueue(interfaceAdd.getContext());
        String url=  interfaceAdd.getContext().getString(R.string.url_base)+"incidence";
        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("description", incidencia.getDescription());
            jsonBody.put("location", incidencia.getLocation());
            jsonBody.put("lat", incidencia.getLat());
            jsonBody.put("lon", incidencia.getLon());
            jsonBody.put("photo", incidencia.getPhoto());
            jsonBody.put("date", incidencia.getFecha());
            jsonBody.put("hour", "10:40");
            jsonBody.put("type_incidence_id", incidencia.getType_incidence_id());
            jsonBody.put("organization_id", incidencia.getOrganization_id());
            jsonBody.put("user_id", incidencia.getUsuarioId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  interfaceRegistro.showLoading(true);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonBody,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Gson gson = new Gson();
                        objResponse=null;
                        try
                        {
                            JSONObject object = (JSONObject) new JSONTokener(response.toString()).nextValue();
                            Integer status = object.getInt("status");
                            String result = object.getString("result");
                            if (status==200){
                                JSONObject object2 = (JSONObject) new JSONTokener(result).nextValue();
                                String userstring = object2.getString("obj");
                                objResponse = gson.fromJson(userstring, Incidencia.class);
                                progressDialog.dismiss();
                                if(objResponse!=null)
                                {   interfaceAdd.showLoading(false);
                                    sendPusher();
                                    interfaceAdd.onRequestSuccess(objResponse);
                                }
                            }else{
                                interfaceAdd.showLoading(false);
                                progressDialog.dismiss();
                                String message="Error al Registrar";
                                interfaceAdd.onFail(message);
                            }
                        }catch (JSONException e)
                        {   interfaceAdd.showLoading(false);
                            interfaceAdd.onRequestError(e);
                            progressDialog.dismiss();
                            Log.e(TAG, "error 2"+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {   interfaceAdd.showLoading(false);
                Log.e(TAG, "Error onErrorResponse: " + error.getMessage());
                progressDialog.dismiss();
                interfaceAdd.onRequestError(error);
            }
        });
        queue.add(jsonObjReq);
    }
    public   void  sendPusher(){
        queue = Volley.newRequestQueue(interfaceAdd.getContext());
        String url=  interfaceAdd.getContext().getString(R.string.url_base)+"fire";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Gson gson = new Gson();
//                        objResponse=null;
//                        try
//                        {
//                            JSONObject object = (JSONObject) new JSONTokener(response.toString()).nextValue();
//
//                        }catch (JSONException e)
//                        {
//                            Log.e(TAG, "error 2"+e.getMessage());
//                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {   interfaceAdd.showLoading(false);
                Log.e(TAG, "Error onErrorResponse: " + error.getMessage());

                interfaceAdd.onRequestError(error);
            }
        });
        queue.add(jsonObjReq);
    }


}
