package org.example.tarea_pdf;

public class Cliente {

    private int id;
    private String nombre;
    private String email;
    private String ciudad;

    public Cliente(int id, String nombre, String email, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.ciudad = ciudad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getCiudad() {
        return ciudad;
    }
}