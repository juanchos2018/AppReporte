package com.example.appreporte.Presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.appreporte.Interface.InterfaceRegistro;
import com.example.appreporte.Model.User;
import com.example.appreporte.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RegistroPresenter {


    private static final String TAG ="RegistroPresenter" ;
    private InterfaceRegistro interfaceRegistro;
    private RequestQueue queue;
    private User userResponse;
    private Context mContext;


    public RegistroPresenter(InterfaceRegistro interfaceRegistro) {
        this.interfaceRegistro = interfaceRegistro;
    }

    public  void InfoUser(String usuario_id){

        queue = Volley.newRequestQueue(interfaceRegistro.getContext());
        String url=  interfaceRegistro.getContext().getString(R.string.url_base)+"user-view/"+usuario_id;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Gson gson = new Gson();
                        userResponse=null;
                        try
                        {
                            JSONObject object = (JSONObject) new JSONTokener(response.toString()).nextValue();
                            Integer status = object.getInt("status");
                            String result = object.getString("result");
                            Log.e(TAG, "result "+result);
                            JSONObject object2 = (JSONObject) new JSONTokener(result).nextValue();
                            String userstring = object2.getString("user");
                            if (status==200){
                                userResponse = gson.fromJson(userstring, User.class);

                                if(userResponse!=null)
                                {   interfaceRegistro.showLoading(false);
                                    interfaceRegistro.onRequestSuccess(userResponse);
                                }
                            }else{
                                interfaceRegistro.showLoading(false);

                                String message="Error al Registrar";
                                interfaceRegistro.onFail(message);
                            }
                        }catch (JSONException e)
                        {   interfaceRegistro.showLoading(false);
                            interfaceRegistro.onRequestError(e);

                            Log.e(TAG, "error 2"+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {   interfaceRegistro.showLoading(false);
                Log.e(TAG, "Error onErrorResponse: " + error.getMessage());
                interfaceRegistro.onRequestError(error);
            }
        });
        queue.add(jsonObjReq);
    }

    public  void editUser(User user){

    }
    public  void storeUer(User user)
    {
        if (user==null){
            String message="Error al Registrar";
            interfaceRegistro.onFail(message);
        }
        else {

            queue = Volley.newRequestQueue(interfaceRegistro.getContext());
            String url=  interfaceRegistro.getContext().getString(R.string.url_base)+"user";
            JSONObject jsonBody = new JSONObject();
            try {
                //String tipo =Type
                jsonBody.put("name", user.getName());
                jsonBody.put("number_document", String.valueOf(user.getNumber_document()));
                jsonBody.put("last_name", user.getLast_name());
                jsonBody.put("user", user.getName());
                jsonBody.put("email", user.getEmail());
                jsonBody.put("password", user.getPassword());
                jsonBody.put("phone", user.getPhone());
                jsonBody.put("location", user.getLocation());
                jsonBody.put("lat", user.getLat());
                jsonBody.put("lon", user.getLon());
                jsonBody.put("photo", user.getPhoto());
            } catch (JSONException e) {
                e.printStackTrace();
            }
          //  interfaceRegistro.showLoading(true);
            ProgressDialog progressDialog= new ProgressDialog(interfaceRegistro.getContext());
            progressDialog.setMessage("Cargando..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, jsonBody,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            Gson gson = new Gson();
                            userResponse=null;
                            try
                            {
                                JSONObject object = (JSONObject) new JSONTokener(response.toString()).nextValue();
                                Integer status = object.getInt("status");
                                String result = object.getString("result");
                                Log.e(TAG, "result "+result);
                                JSONObject object2 = (JSONObject) new JSONTokener(result).nextValue();
                                String userstring = object2.getString("user");
                                if (status==200){
                                    userResponse = gson.fromJson(userstring, User.class);
                                    progressDialog.dismiss();
                                    if(userResponse!=null)
                                    {   interfaceRegistro.showLoading(false);
                                        interfaceRegistro.onRequestSuccess(userResponse);
                                    }
                                }else{
                                    interfaceRegistro.showLoading(false);
                                    progressDialog.dismiss();
                                    String message="Error al Registrar";
                                    interfaceRegistro.onFail(message);
                                }
                            }catch (JSONException e)
                            {   interfaceRegistro.showLoading(false);
                                interfaceRegistro.onRequestError(e);
                                progressDialog.dismiss();
                                Log.e(TAG, "error 2"+e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error)
                {   interfaceRegistro.showLoading(false);
                    Log.e(TAG, "Error onErrorResponse: " + error.getMessage());
                    progressDialog.dismiss();
                    interfaceRegistro.onRequestError(error);
                }
            });
            queue.add(jsonObjReq);
        }

    }

}
