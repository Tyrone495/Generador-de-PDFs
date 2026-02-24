package org.example.tarea_pdf;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Helpcontroller {

    @FXML
    public void ayudaVentana() {
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("help.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 120);
            stage.setTitle("Ventana de Información");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
