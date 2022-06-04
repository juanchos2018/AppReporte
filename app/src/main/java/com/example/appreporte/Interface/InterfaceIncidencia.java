package com.example.appreporte.Interface;

import com.example.appreporte.Model.Incidencia;

import java.util.ArrayList;

public interface InterfaceIncidencia {
                //ArrayView
    interface View extends BaseView<Presenter> {

        void showResult(ArrayList<Incidencia> arrayList);

        void startLoading();

        void stopLoading();

        void showLoadError();
    }


    interface Presenter extends BasePresenter {

        void loadData();
    }
}
