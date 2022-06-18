package com.example.appreporte.Presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appreporte.Interface.InterfaceLocation;
import com.example.appreporte.Interface.InterfaceRegistro;
import com.example.appreporte.Model.Incidencia;
import com.example.appreporte.Model.Locations;
import com.example.appreporte.Model.User;
import com.example.appreporte.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LocationPresenter {


    private static final String TAG ="RegistroPresenter" ;
    private InterfaceLocation interfaceLocation;
    private RequestQueue queue;
    private User userResponse;



    public LocationPresenter(InterfaceLocation interfaceLocation) {
        this.interfaceLocation = interfaceLocation;
    }

    public  void InfoLocation(Double latitud,Double logitud){

        queue = Volley.newRequestQueue(interfaceLocation.getContext());
        String url=  interfaceLocation.getContext().getString(R.string.url_maps)+"json?address="+latitud+","+logitud+"&key="+interfaceLocation.getContext().getString(R.string.key_map);
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
                            String status = object.getString("status");
                            String result = object.getString("results");

                            Type userListType = new TypeToken<ArrayList<Locations>>(){}.getType();

                            ArrayList<Locations>    list1 = gson.fromJson(result, userListType);

                            Log.e(TAG, "result "+result);
                            String addrees =list1.get(0).getFormatted_address();
                            interfaceLocation.onRequestSuccess(addrees);
                           // Toast.makeText(interfaceLocation.getContext(), addrees, Toast.LENGTH_SHORT).show();


                        }catch (JSONException e)
                        {   interfaceLocation.showLoading(false);
                            interfaceLocation.onRequestError(e);

                            Log.e(TAG, "error 2"+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {   interfaceLocation.showLoading(false);
                Log.e(TAG, "Error onErrorResponse: " + error.getMessage());
                interfaceLocation.onRequestError(error);
            }
        });
        queue.add(jsonObjReq);
    }


}
