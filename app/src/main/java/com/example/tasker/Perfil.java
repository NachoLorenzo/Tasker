package com.example.tasker;

public class Perfil {

    String id;
    String email;
    String nombre;
    String edad;

    public Perfil() {
    }

    public Perfil(String id, String email, String nombre, String edad) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
}
