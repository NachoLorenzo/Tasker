package com.example.tasker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorRecyclerUsuaris extends RecyclerView.Adapter<AdaptadorRecyclerUsuaris.UsuariHolder>{

    private ArrayList<Perfil> llistatUsuarisAMostrar;

    public static class UsuariHolder extends RecyclerView.ViewHolder{
        public TextView nom,email;

        public UsuariHolder(View v) {

            super(v);

            nom=v.findViewById(R.id.nom);
            email=v.findViewById(R.id.e_mail);
        }
    }

    public AdaptadorRecyclerUsuaris(ArrayList<Perfil> llistat){
        llistatUsuarisAMostrar=llistat;

    }

    @Override
    public AdaptadorRecyclerUsuaris.UsuariHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_llistat_usuaris_del_projecte,viewGroup,false);

        return new UsuariHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorRecyclerUsuaris.UsuariHolder usuariHolder, int i) {
        usuariHolder.nom.setText(llistatUsuarisAMostrar.get(i).getNombre());
        usuariHolder.email.setText(llistatUsuarisAMostrar.get(i).getEmail());
    }

    @Override
    public int getItemCount() {
        return this.llistatUsuarisAMostrar.size();
    }
}
