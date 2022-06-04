package com.example.appreporte.Presenter;

import com.android.volley.RequestQueue;
import com.example.appreporte.Interface.ILoginView;
import com.example.appreporte.Interface.InterfaceIncidencia2;
import com.example.appreporte.Model.Incidencia;
import com.example.appreporte.Model.User;

import java.util.ArrayList;

public class Incidencia2Presenter {

    private InterfaceIncidencia2 interfaceIncidencia2;
    private RequestQueue queue;
    private ILoginView loginView;
    private static final String TAG ="Incidencia2Presenter" ;
    ArrayList<Incidencia> listaIncidencia = new ArrayList<>();


    public Incidencia2Presenter(InterfaceIncidencia2 interfaceIncidencia2) {
        this.interfaceIncidencia2 = interfaceIncidencia2;
    }


    public  void  Listar(){
       // listaIncideicias();
    }

    private void listaIncideicias() {
//
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
//        interfaceIncidencia2.onRequestSuccess(listaIncidencia);


    }




}
