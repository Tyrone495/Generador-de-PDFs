package org.example.tarea_pdf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ClienteDAO {

    private static String RUTA_CSV = "src/main/resources/clientes.csv";

    public void SetRuta(String NuevaRuta) {
        RUTA_CSV = NuevaRuta;
    }


    /**
     * Simula: SELECT * FROM clientes
     */
    public List<Cliente> obtenerTodos() {
        return leerCSV();
    }

    /**
     * ============================
     * FASE 3 – FILTROS Y PARÁMETROS
     * ============================
     * Simula: SELECT * FROM clientes WHERE ciudad = ?
     */
    public List<Cliente> obtenerPorCiudad(String ciudadFiltro) {

        List<Cliente> resultado = new ArrayList<>();

        for (Cliente c : leerCSV()) {
            if (c.getCiudad().equalsIgnoreCase(ciudadFiltro)) {
                resultado.add(c);
            }
        }

        return resultado;
    }

    /**
     * Simula: SELECT * FROM clientes WHERE nombre LIKE '%texto%'
     */
    public List<Cliente> obtenerPorNombreContiene(String texto) {

        List<Cliente> resultado = new ArrayList<>();

        for (Cliente c : leerCSV()) {
            if (c.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                resultado.add(c);
            }
        }

        return resultado;
    }

    /**
     * ============================
     * FASE 4 – CÁLCULOS Y TOTALES
     * ============================
     * Simula: SELECT COUNT(*) FROM clientes
     */
    public int contarClientes() {
        return leerCSV().size();
    }

    /**
     * Simula: SELECT ciudad, COUNT(*) FROM clientes GROUP BY ciudad
     */
    public Map<String, Integer> contarClientesPorCiudad() {

        Map<String, Integer> totales = new HashMap<>();

        for (Cliente c : leerCSV()) {
            String ciudad = c.getCiudad();
            totales.put(ciudad, totales.getOrDefault(ciudad, 0) + 1);
        }

        return totales;
    }

    /**
     * ============================
     * MÉTODO INTERNO DE LECTURA
     * ============================
     */
    private List<Cliente> leerCSV() {

        List<Cliente> clientes = new ArrayList<>();

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

        } catch (IOException e) {
            System.err.println("Error leyendo el CSV");
            e.printStackTrace();
        }

        return clientes;
    }

}
