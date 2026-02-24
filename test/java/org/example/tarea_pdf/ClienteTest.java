package org.example.tarea_pdf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ClienteTest {
    int id = 23;
    String nombre = "Pepe";
    String email = "pepe@gmail.com";
    String ciudad = "Nueva York";
    Cliente cliente = new Cliente(id, nombre, email ,ciudad);

    @Test
    void getId() {
        assertEquals(23,cliente.getId());
    }

    @Test
    void getNombre() {
        Cliente cliente = new Cliente(id, nombre, email ,ciudad);
        assertEquals("Pepe",cliente.getNombre());
    }

    @Test
    void getEmail() {
        Cliente cliente = new Cliente(id, nombre, email ,ciudad);
        assertEquals("pepe@gmail.com",cliente.getEmail());
    }

    @Test
    void getCiudad() {
        Cliente cliente = new Cliente(id, nombre, email ,ciudad);
        assertEquals("Nueva York",cliente.getCiudad());
    }
}
