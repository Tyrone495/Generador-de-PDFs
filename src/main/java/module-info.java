module org.example.tarea_pdf {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.jfree.jfreechart;
    requires org.apache.pdfbox;
    requires javafx.swing;
    requires javafx.graphics;

    opens org.example.tarea_pdf to javafx.fxml;
    exports org.example.tarea_pdf;
}