package com.example.appreporte.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appreporte.Model.Incidencia;
import com.example.appreporte.R;

import java.util.ArrayList;

public class AdapterIncidencia extends RecyclerView.Adapter<AdapterIncidencia.ViewHolderDatos>  implements View.OnClickListener{



    ArrayList<Incidencia> listaIncidencia;

    public AdapterIncidencia(ArrayList<Incidencia> listaIncidencia) {
        this.listaIncidencia = listaIncidencia;
    }


    private View.OnClickListener listener;
    public  void setOnClickListener(View.OnClickListener listener)
    {
        this.listener=listener;
    }



    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incidencia,parent,false);
        vista.setOnClickListener(this);
        return new  ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos items =(ViewHolderDatos)holder;

            items.tvtitle.setText(listaIncidencia.get(position).getTitle());
            items.tvlocation.setText(listaIncidencia.get(position).getLocation());
        }
    }

    @Override
    public int getItemCount() {
        return listaIncidencia.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvtitle,tvlocation;
        ImageView imgphoto;


        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            tvtitle=(TextView)itemView.findViewById(R.id.titile);
            tvlocation=(TextView)itemView.findViewById(R.id.location);
            imgphoto=(ImageView)itemView.findViewById(R.id.photo);

        }
    }
}
