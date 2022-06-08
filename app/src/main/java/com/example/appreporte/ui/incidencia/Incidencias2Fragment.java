package com.example.appreporte.ui.incidencia;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appreporte.Adapter.AdapterIncidencia;
import com.example.appreporte.Interface.InterfaceIncidencia2;
import com.example.appreporte.Model.Incidencia;
import com.example.appreporte.Presenter.Incidencia2Presenter;
import com.example.appreporte.Presenter.LoginPresenter;
import com.example.appreporte.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Incidencias2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Incidencias2Fragment extends Fragment implements InterfaceIncidencia2 {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private Incidencia2Presenter incidencia2Presenter;
    RecyclerView recyclerView;
    private Context context;
    AdapterIncidencia adapterIncidencia;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Incidencias2Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Incidencias2Fragment newInstance(String param1, String param2) {
        Incidencias2Fragment fragment = new Incidencias2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View vista=inflater.inflate(R.layout.fragment_incidencias2, container, false);

        recyclerView =(RecyclerView)vista.findViewById(R.id.recyclerindicencias2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        incidencia2Presenter= new Incidencia2Presenter(this);
        incidencia2Presenter.Listar();

        return vista;
    }

    @Override
    public void showLoading(boolean state) {

    }

    @Override
    public void onRequestSuccess(ArrayList<Incidencia> object) {
        adapterIncidencia = new AdapterIncidencia(object);
        recyclerView.setAdapter(adapterIncidencia);
    }

    @Override
    public void onRequestError(Object object) {

    }

    @Override
    public void onFail(String message) {

    }
}