package com.example.tasker;

public class Usuario {

    String nombre, edad, email;

    public Usuario(String nombre, String edad, String email) {
        this.nombre = nombre;
        this.edad = edad;
        this.email = email;
    }

    public String getNombre() { return nombre; }
    public String getEdad() { return edad; }
    public String getEmail() { return email; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
