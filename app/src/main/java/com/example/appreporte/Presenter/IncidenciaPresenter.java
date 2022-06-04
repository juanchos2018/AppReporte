package com.example.appreporte.Presenter;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.appreporte.Adapter.AdapterIncidencia;
import com.example.appreporte.Interface.InterfaceIncidencia;
import com.example.appreporte.Interface.OnStringListener;
import com.example.appreporte.Model.Incidencia;
import com.example.appreporte.Model.StringModelImpl;
import com.google.gson.Gson;

import java.util.ArrayList;

public class IncidenciaPresenter implements InterfaceIncidencia.Presenter, OnStringListener {



    private InterfaceIncidencia.View view;
    private Context context;
    private StringModelImpl model;
    ArrayList<Incidencia> listaIncidencia = new ArrayList<>();



    public IncidenciaPresenter(Context context,InterfaceIncidencia.View view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);

    }

    @Override
    public void loadData() {
        view.startLoading();
      //  listaIncideicias();
        String url="http://192.168.1.209:8000/incidence/1";
        model.load(url, this);
    }

    @Override
    public void start() {

    }
    private void listaIncideicias() {

//        Incidencia o1= new Incidencia("1","fecha","trobo","Titulo de incidencia","Descrcipciion Incidencia","Avenida leguia","",1,"activo",20.5,15.2);
//        Incidencia o2= new Incidencia("1","fecha","trobo","Titulo de incidencia","Descrcipciion Incidencia","Avenida leguia","",1,"activo",20.5,15.2);
//        Incidencia o3= new Incidencia("1","fecha","trobo","Titulo de incidencia","Descrcipciion Incidencia","Avenida leguia","",1,"activo",20.5,15.2);
//        Incidencia o4= new Incidencia("1","fecha","trobo","Titulo de incidencia","Descrcipciion Incidencia","Avenida leguia","",1,"activo",20.5,15.2);
//
//        listaIncidencia.add(o1);
//        listaIncidencia.add(o2);
//        listaIncidencia.add(o3);
//        listaIncidencia.add(o4);
//
//        view.showResult(listaIncidencia);
//        view.stopLoading();

    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
      //  Incidencia q = gson.fromJson(result, Incidencia.class);
//        for (Incidencia item : q.getItems()) {
//            list.add(item);
//        }
//        Incidencia o1= new Incidencia("1","fecha","trobo","Titulo de incidencia","Descrcipciion Incidencia","Avenida leguia","",1,"activo",20.5,15.2);
//        Incidencia o2= new Incidencia("1","fecha","trobo","Titulo de incidencia","Descrcipciion Incidencia","Avenida leguia","",1,"activo",20.5,15.2);
//        Incidencia o3= new Incidencia("1","fecha","trobo","Titulo de incidencia","Descrcipciion Incidencia","Avenida leguia","",1,"activo",20.5,15.2);
//        Incidencia o4= new Incidencia("1","fecha","trobo","Titulo de incidencia","Descrcipciion Incidencia","Avenida leguia","",1,"activo",20.5,15.2);
//
//        listaIncidencia.add(o1);
//        listaIncidencia.add(o2);
//        listaIncidencia.add(o3);
//        listaIncidencia.add(o4);
//
//        view.showResult(listaIncidencia);
//        view.stopLoading();
//        Toast.makeText(context, "onSuccess", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VolleyError error) {
        view.showLoadError();
        view.stopLoading();
    }
}
