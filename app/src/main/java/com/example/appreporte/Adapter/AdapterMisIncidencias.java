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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMisIncidencias  extends RecyclerView.Adapter<AdapterMisIncidencias.ViewHolderDatos>  implements View.OnClickListener{

    ArrayList<Incidencia> listaIncidencia;

    public AdapterMisIncidencias(ArrayList<Incidencia> listaIncidencia) {
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
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mis_incidencias,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos items =(ViewHolderDatos)holder;

            items.tvtitleincidencia.setText(listaIncidencia.get(position).getDescription());
            items.tvlocation.setText(listaIncidencia.get(position).getLocation());
            Picasso.get().load(listaIncidencia.get(position).getPhoto()).into(items.imgincidencia);


        }
    }

    @Override
    public int getItemCount() {
        return listaIncidencia.size();

    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvtitleincidencia,tvlocation;
        ImageView imgincidencia;


        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            tvtitleincidencia=(TextView)itemView.findViewById(R.id.tvtitleincidencia);
            tvlocation=(TextView)itemView.findViewById(R.id.tvlocation);
            imgincidencia=(ImageView)itemView.findViewById(R.id.imgincidencia);
        }
    }
}
