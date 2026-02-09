package org.example.tarea_pdf;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelpcontrollerTest {
    Helpcontroller helpcontroller = new Helpcontroller();

    @Test
    void ayudaVentana() {
        Platform.startup(() -> helpcontroller.ayudaVentana());
    }
}