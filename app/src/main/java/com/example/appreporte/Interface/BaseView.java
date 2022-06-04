package com.example.appreporte.Interface;

import android.view.View;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void initViews(View view);

}
