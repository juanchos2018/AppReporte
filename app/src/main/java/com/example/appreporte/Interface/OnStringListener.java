package com.example.appreporte.Interface;

import com.android.volley.VolleyError;

public interface OnStringListener{

    void onSuccess(String result);

    void onError(VolleyError error);

}
