package com.example.appreporte.Presenter;

import com.android.volley.RequestQueue;

import com.example.appreporte.Interface.InterfaceIncidencia2;
import com.example.appreporte.Interface.InterfaceNumeros;
import com.example.appreporte.Model.Incidencia;
import com.example.appreporte.Model.Numeros;

import java.util.ArrayList;

public class NumerosPresenter {

    private InterfaceNumeros interfaceNumeros;
    private RequestQueue queue;

    private static final String TAG ="Incidencia2Presenter" ;
    ArrayList<Numeros> listaItems;

    public NumerosPresenter(InterfaceNumeros interfaceNumeros) {
        this.interfaceNumeros = interfaceNumeros;
    }






}
