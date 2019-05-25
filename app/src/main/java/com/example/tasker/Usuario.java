package com.example.tasker;

public class Usuario {

    String id, nombre, edad, email, id_proyecto;

    public Usuario(String id, String nombre, String edad, String email, String id_proyecto){
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.email = email;
        this.id_proyecto = id_proyecto;
    }


    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEdad() { return edad; }
    public String getEmail() { return email; }
    public String getId_proyecto() { return id_proyecto; }




    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setEdad(String edad) {
        this.edad = edad;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setId_proyecto(String id_proyecto) { this.id_proyecto = id_proyecto; }
}
