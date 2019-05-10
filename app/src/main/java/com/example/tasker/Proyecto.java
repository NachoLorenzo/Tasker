package com.example.tasker;

import java.util.List;

public class Proyecto {

    //List<Usuario> listaUsuarios;
    String nombreProyecto;
    String id;

    public Proyecto(/*List<Usuario> listaUsuarios,*/String nombreProyecto, String id) {
        //this.listaUsuarios = listaUsuarios;
        this.nombreProyecto = nombreProyecto;
        this.id = id;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*public List<Usuario> getListaUsuarios() { return listaUsuarios; }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }*/
}
