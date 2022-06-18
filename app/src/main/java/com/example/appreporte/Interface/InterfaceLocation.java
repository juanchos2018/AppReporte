package com.example.appreporte.Interface;

import android.content.Context;

import com.example.appreporte.Model.User;

public interface InterfaceLocation {

    void showLoading(boolean state);
    void onRequestSuccess(String object);
    void onRequestError(Object object);
    void onFail(String message);
    Context getContext();

}
