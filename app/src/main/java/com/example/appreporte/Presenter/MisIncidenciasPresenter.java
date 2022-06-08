package com.example.appreporte.Presenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appreporte.Interface.InterfaceIncidencia2;
import com.example.appreporte.Model.Incidencia;
import com.example.appreporte.Model.User;
import com.example.appreporte.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MisIncidenciasPresenter {


    private static final String TAG = "mis incidencias";
    private InterfaceIncidencia2 interfaceIncidencia2;
    private RequestQueue queue;
    ArrayList<Incidencia> listaIncidencia;

    public MisIncidenciasPresenter(InterfaceIncidencia2 interfaceIncidencia2) {
        this.interfaceIncidencia2 = interfaceIncidencia2;
    }

    public  void  ListarMisIncidencias(String usuario_id){

       String url=  interfaceIncidencia2.getContext().getString(R.string.url_base)+"incidence/"+usuario_id;
       queue = Volley.newRequestQueue(interfaceIncidencia2.getContext());
       JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Gson gson = new Gson();
                        listaIncidencia = new ArrayList<>();
                        try
                        {
                            JSONObject object = (JSONObject) new JSONTokener(response.toString()).nextValue();
                            Integer status = object.getInt("status");
                            String result = object.getString("result");

                            Type userListType = new TypeToken<ArrayList<Incidencia>>(){}.getType();
                            ArrayList<Incidencia>    list1 = gson.fromJson(result, userListType);
                            for (Incidencia item : list1) {
                                   listaIncidencia.add(item);
                             }
                            interfaceIncidencia2.onRequestSuccess(listaIncidencia);

                        }catch (JSONException e)
                        {
                            Log.e(TAG, "error 2"+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e(TAG, "Error: " + error);
            }
        });
        queue.add(jsonObjReq);
    }
}
