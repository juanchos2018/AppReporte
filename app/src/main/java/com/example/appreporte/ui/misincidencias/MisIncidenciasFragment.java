package com.example.appreporte.ui.misincidencias;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appreporte.Adapter.AdapterMisIncidencias;
import com.example.appreporte.Interface.InterfaceIncidencia2;
import com.example.appreporte.Model.Incidencia;
import com.example.appreporte.Presenter.LoginPresenter;
import com.example.appreporte.Presenter.MisIncidenciasPresenter;
import com.example.appreporte.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MisIncidenciasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MisIncidenciasFragment extends Fragment implements InterfaceIncidencia2 {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    private MisIncidenciasPresenter misIncidenciasPresenter;
    AdapterMisIncidencias adapter;

    RecyclerView recycler;

    public MisIncidenciasFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MisIncidenciasFragment newInstance(String param1, String param2) {
        MisIncidenciasFragment fragment = new MisIncidenciasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public static MisIncidenciasFragment newInstance(int i) {
        return new MisIncidenciasFragment();
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
        View view =inflater.inflate(R.layout.fragment_mis_incidencias, container, false);

        misIncidenciasPresenter= new MisIncidenciasPresenter(this);
        recycler=view.findViewById(R.id.recyclermisindicencias);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        SharedPreferences sharedPreferences2 = getContext().getSharedPreferences("USER", getContext().MODE_PRIVATE);
        String usuario_id =sharedPreferences2.getString("id", "");
        misIncidenciasPresenter.ListarMisIncidencias(usuario_id);
        return view;
    }

    @Override
    public void showLoading(boolean state) {

    }

    @Override
    public void onRequestSuccess(ArrayList<Incidencia> object) {
        adapter = new AdapterMisIncidencias(object);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onRequestError(Object object) {

    }

    @Override
    public void onFail(String message) {

    }
}