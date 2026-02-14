package org.example.tarea_pdf;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SelectController {
    static Stage stage2 = new Stage();
    @FXML
    Button button_cancelar = new Button();
    @FXML
    Button button_aceptar = new Button();


    public void initialize() {
        HelloController helloController = new HelloController();


        button_cancelar.setOnAction(actionEvent ->
                stage2.close());
        button_aceptar.setOnAction(actionEvent -> helloController.exportarAPDF());
    }

    @FXML
    public void Selectventana() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accept.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 120);
            stage2.setTitle("Ventana de Guardado");
            stage2.setScene(scene);
            stage2.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
