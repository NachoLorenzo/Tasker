package com.example.tasker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorRecyclerTareas extends RecyclerView.Adapter<AdaptadorRecyclerTareas.TareaHolder>{

    private ArrayList<Tarea> listaTareasMostrar;

    public static class TareaHolder extends RecyclerView.ViewHolder{
        public TextView nombre, descripcion;

        public TareaHolder(View v) {

            super(v);

            nombre = v.findViewById(R.id.nombreTarea);
            descripcion=v.findViewById(R.id.descripcionTarea);
        }
    }

    public AdaptadorRecyclerTareas(ArrayList<Tarea> listado){
        listaTareasMostrar = listado;

    }

    @Override
    public AdaptadorRecyclerTareas.TareaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.elemento_lista_tareas_proyecto,viewGroup,false);

        return new TareaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorRecyclerTareas.TareaHolder tareaHolder, int i) {
        tareaHolder.nombre.setText(listaTareasMostrar.get(i).getNombre());
        tareaHolder.descripcion.setText(listaTareasMostrar.get(i).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return this.listaTareasMostrar.size();
    }

}
