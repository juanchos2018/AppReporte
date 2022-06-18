package com.example.appreporte.Interface;

import android.content.Context;

import com.example.appreporte.Model.Incidencia;
import com.example.appreporte.Model.Numeros;

import java.util.ArrayList;

public interface InterfaceNumeros {


    void showLoading(boolean state);
    void onRequestSuccess(ArrayList<Numeros> object);
    void onRequestError(Object object);
    void onFail(String message);
    Context getContext();

}
