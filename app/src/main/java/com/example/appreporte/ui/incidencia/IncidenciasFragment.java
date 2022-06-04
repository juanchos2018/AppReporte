package com.example.appreporte.ui.incidencia;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appreporte.Adapter.AdapterIncidencia;
import com.example.appreporte.Interface.InterfaceIncidencia;
import com.example.appreporte.Model.Incidencia;
import com.example.appreporte.Presenter.IncidenciaPresenter;
import com.example.appreporte.R;

import java.util.ArrayList;


public class IncidenciasFragment extends Fragment implements InterfaceIncidencia.View {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    AdapterIncidencia adapterIncidencia;
   // ArrayList<Incidencia> listaIncidencia;
    RecyclerView recyclerView;
    private Context context;

    private InterfaceIncidencia.Presenter presenter;

    public IncidenciasFragment() {
        // Required empty public constructor
    }

    public static IncidenciasFragment newInstance(String param1, String param2) {
        IncidenciasFragment fragment = new IncidenciasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }
    public static IncidenciasFragment newInstance(int i) {
        return new IncidenciasFragment();
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
        View vista=inflater.inflate(R.layout.fragment_incidencias, container, false);

       // listaIncidencia= new ArrayList<>();
      //  init(vista);
        initViews(vista);
       // incidenciaPresenter = new IncidenciaPresenter(context,view);
        //incidenciaPresenter.loadData();
      //  view = new       InterfaceIncidencia.View();


        presenter.loadData();
        return  vista;
    }

    private void listaIncideicias() {

//        Incidencia o1= new Incidencia("1","fecha","trobo","Titulo de incidencia","Descrcipciion Incidencia","Avenida leguia","",1,"activo",20.5,15.2);
//        Incidencia o2= new Incidencia("1","fecha","trobo","Titulo de incidencia","Descrcipciion Incidencia","Avenida leguia","",1,"activo",20.5,15.2);
//        Incidencia o3= new Incidencia("1","fecha","trobo","Titulo de incidencia","Descrcipciion Incidencia","Avenida leguia","",1,"activo",20.5,15.2);
//        Incidencia o4= new Incidencia("1","fecha","trobo","Titulo de incidencia","Descrcipciion Incidencia","Avenida leguia","",1,"activo",20.5,15.2);

//        listaIncidencia.add(o1);
//        listaIncidencia.add(o2);
//        listaIncidencia.add(o3);
//        listaIncidencia.add(o4);
//
//        adapterIncidencia = new AdapterIncidencia(listaIncidencia);
//        recyclerView.setAdapter(adapterIncidencia);
    }

    @Override
    public void setPresenter(InterfaceIncidencia.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        recyclerView =(RecyclerView)view.findViewById(R.id.recyclerindicencias);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void showResult(ArrayList<Incidencia> arrayList) {
        if (adapterIncidencia == null) {
            adapterIncidencia = new AdapterIncidencia(arrayList);
            recyclerView.setAdapter(adapterIncidencia);
           // Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
        } else {
            adapterIncidencia.notifyDataSetChanged();
          //  Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void startLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showLoadError() {

    }
    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }
}