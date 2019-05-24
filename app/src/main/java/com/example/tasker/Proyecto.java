package com.example.tasker;

import java.util.ArrayList;
import java.util.List;

public class Proyecto {

    //List<Usuario> listaUsuarios;
    String nombreProyecto;
    String id;
    ArrayList<String> id_usu;
    ArrayList<String> id_tareas;



    public Proyecto(){

    }


    public Proyecto(/*List<Usuario> listaUsuarios,*/String nombreProyecto, String id,ArrayList<String> id_usuaris,ArrayList<String> id_tasques) {
        //this.listaUsuarios = listaUsuarios;
        this.nombreProyecto = nombreProyecto;
        this.id = id;
        this.id_usu=id_usuaris; //ArrayList d'usuaris
        this.id_tareas=id_tasques; //ArrayList de Tareas
    }

    public ArrayList<String> getId_tareas() {
        return id_tareas;
    }

    public void setId_tareas(ArrayList<String> id_tareas) {
        this.id_tareas = id_tareas;
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

    public void setId_usu(ArrayList<String> id_usu) {
        this.id_usu = id_usu;
    }

    public ArrayList<String> getId_usu() {
        return id_usu;
    }

    /*public List<Usuario> getListaUsuarios() { return listaUsuarios; }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }*/
}
