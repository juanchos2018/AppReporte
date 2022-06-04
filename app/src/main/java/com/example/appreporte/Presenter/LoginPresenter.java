package com.example.appreporte.Presenter;

import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appreporte.Interface.ILoginPresenter;
import com.example.appreporte.Interface.ILoginView;
import com.example.appreporte.Model.LoginResponse;
import com.example.appreporte.Model.User;
import com.example.appreporte.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class LoginPresenter{

    private static final String TAG ="LoginPresenter" ;
    private ILoginView loginView;
    private RequestQueue queue;
    private User userResponse;
    ProgressDialog progressDialog;


    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }


    public void login(String email, String password)
    {
        queue = Volley.newRequestQueue(loginView.getContext());
        String url=  loginView.getContext().getString(R.string.url_base)+"login";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loginView.showLoading(true);
        showLoanding();
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
                            if (status==200){
                                JSONObject object2 = (JSONObject) new JSONTokener(result).nextValue();
                                String userstring = object2.getString("user");
                                userResponse = gson.fromJson(userstring, User.class);
                                if(userResponse!=null)
                                {   loginView.showLoading(false);
                                    dismiss();
                                    loginView.onRequestSuccess(userResponse);
                                }
                            }else{
                                loginView.showLoading(false);
                                dismiss();
                                String message="Datos incorectos";
                                loginView.onFail(message);
                            }
                        }catch (JSONException e)
                        {   loginView.showLoading(false);
                            dismiss();
                            loginView.onRequestError(e);
                            Log.i(TAG, "error 2"+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {   loginView.showLoading(false);
                dismiss();
                Log.i(TAG, "Error: " + error);
                loginView.onRequestError(error);
            }
        });
        queue.add(jsonObjReq);
    }

    private  void showLoanding(){
        progressDialog= new ProgressDialog(loginView.getContext());
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Cargando");
        progressDialog.show();
    }
    private  void  dismiss(){
        progressDialog.dismiss();
    }


}
