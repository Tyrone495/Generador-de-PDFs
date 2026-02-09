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
        Assertions.assertAll(() -> assertEquals(23,cliente.getId()),
                () -> assertEquals(23,12));
    }

    @Test
    void getNombre() {
        Cliente cliente = new Cliente(id, nombre, email ,ciudad);
        Assertions.assertAll(() -> assertEquals("Pepe",cliente.getNombre()),
                () -> assertEquals("Pepe", "Juan"));
    }

    @Test
    void getEmail() {
        Cliente cliente = new Cliente(id, nombre, email ,ciudad);
        Assertions.assertAll(() -> assertEquals("pepe@gmail.com",cliente.getEmail()),
                () -> assertEquals("lucho@gmail.com",cliente.getEmail()));
    }

    @Test
    void getCiudad() {
        Cliente cliente = new Cliente(id, nombre, email ,ciudad);
        Assertions.assertAll(() -> assertEquals("Nueva York",cliente.getCiudad()),
                () -> assertEquals("lucho@gmail.com",cliente.getCiudad()));
    }
}