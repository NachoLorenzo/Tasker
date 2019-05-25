package com.example.tasker;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorRecyclerUsuarios extends RecyclerView.Adapter<AdaptadorRecyclerUsuarios.UsuariHolder>{

    private ArrayList<Perfil> listaUsuariosAMostrar;
    private LinearLayout layoutUsuarios;
    private Fragment fUsuario;
    private FragmentManager fm;
    private FragmentTransaction ft;

    public static class UsuariHolder extends RecyclerView.ViewHolder{
        public TextView nombre, email;

        public UsuariHolder(View v) {

            super(v);

            nombre = v.findViewById(R.id.nombreElementoUsuario);
            email = v.findViewById(R.id.emailElementoUsuario);
        }
    }

    public AdaptadorRecyclerUsuarios(ArrayList<Perfil> listado){
        listaUsuariosAMostrar = listado;

    }

    @Override
    public AdaptadorRecyclerUsuarios.UsuariHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.elemento_lista_usuarios_proyecto,viewGroup,false);

//        layoutUsuarios.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        return new UsuariHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorRecyclerUsuarios.UsuariHolder usuariHolder, int i) {
        usuariHolder.nombre.setText(listaUsuariosAMostrar.get(i).getNombre());
        usuariHolder.email.setText(listaUsuariosAMostrar.get(i).getEmail());
    }

    @Override
    public int getItemCount() {
        return this.listaUsuariosAMostrar.size();
    }
}
