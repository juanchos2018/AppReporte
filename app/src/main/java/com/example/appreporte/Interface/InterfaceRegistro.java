package com.example.appreporte.Interface;

import android.content.Context;

import com.example.appreporte.Model.User;

public interface InterfaceRegistro {


    void showLoading(boolean state);
    void onRequestSuccess(User object);
    void onRequestError(Object object);
    void onFail(String message);
    Context getContext();

}
