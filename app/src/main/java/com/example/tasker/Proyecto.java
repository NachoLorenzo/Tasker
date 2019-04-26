package com.example.tasker;

import java.util.List;

public class Proyecto {

    List<Usuario> listaUsuarios;

    public Proyecto(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Usuario> getListaUsuarios() { return listaUsuarios; }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
}
