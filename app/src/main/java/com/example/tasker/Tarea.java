package com.example.tasker;

public class Tarea {

    String nombre, descripcion, id;

    public Tarea(String nombre, String descripcion, String id) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = id;
    }
    public Tarea(){};

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
