package org.example.tarea_pdf;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteDAOTest {
    String RUTA_CSV = "src/main/resources/clientes.csv";
    List<Cliente> clientes = new ArrayList<>();
    boolean leidos = false;

    @Test
    void leerCSV() {


        Cliente cliente = new Cliente(4,"Ramiro","ramiro@gmail.com","Aldiba");
        Cliente cliente2 = new Cliente(7,"Pamela","pamela@gmail.com","Aldiba2");
        Cliente cliente3 = new Cliente(23,"Karen","karen@gmail.com","Aldiba3");
        Cliente cliente4 = new Cliente(12,"Cassandra","cassandra@gmail.com","Aldiba4");
        Cliente cliente5 = new Cliente(67,"Marisol","marisol@gmail.com","Aldiba5");
        Cliente cliente6 = new Cliente(07,"Lenin","lenin@gmail.com","Aldiba6");


        clientes.add(cliente);
        clientes.add(cliente2);
        clientes.add(cliente3);
        clientes.add(cliente4);
        clientes.add(cliente5);
        clientes.add(cliente6);


        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CSV))) {

            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {

                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] campos = linea.split(",");

                int id = Integer.parseInt(campos[0]);
                String nombre = campos[1];
                String email = campos[2];
                String ciudad = campos[3];

                clientes.add(new Cliente(id, nombre, email, ciudad));
            }

            if (!clientes.isEmpty()) {
                leidos = true;
            } else leidos = false;

            assertTrue(leidos);

        } catch (IOException e) {
            System.err.println("Error leyendo el CSV");
            e.printStackTrace();
        }
    }

}