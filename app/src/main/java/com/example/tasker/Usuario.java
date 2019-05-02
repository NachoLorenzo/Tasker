package com.example.tasker;

public class Usuario {

    String id, nombre, edad, email, fecha, tarea;

    public Usuario(String id, String nombre, String edad, String email/*, String fecha, String tarea*/) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.email = email;
        //this.fecha = fecha;
        //this.tarea = tarea;
    }


    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEdad() { return edad; }
    public String getEmail() { return email; }
    //public String getFecha() { return fecha; }
    //public String getTarea() { return tarea; }

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
    //public void setFecha(String fecha) { this.fecha = fecha; }
    //public void setTarea(String tarea) { this.tarea = tarea; }
}
