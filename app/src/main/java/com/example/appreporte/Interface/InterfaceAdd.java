package com.example.appreporte.Interface;

import android.content.Context;

import com.example.appreporte.Model.Incidencia;

import java.util.ArrayList;

public interface InterfaceAdd {

    void showLoading(boolean state);
    void onRequestSuccess(Incidencia object);
    void onRequestError(Object object);
    void onFail(String message);
    Context getContext();

}
