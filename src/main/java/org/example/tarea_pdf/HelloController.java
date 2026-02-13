package org.example.tarea_pdf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

public class HelloController {

    @FXML
    public Button csv_button;
    @FXML
    public Button pdf_button;
    @FXML
    public Button changes_button;
    @FXML
    public TextField txt_name;
    @FXML
    public Label total = new Label();
    @FXML
    public TableView<Cliente> tabla;
    @FXML
    public TableColumn<Cliente, String> colNombre;
    @FXML
    public TableColumn<Cliente, String> colEmail;
    @FXML
    public TableColumn<Cliente, String> colCiudad;
    @FXML
    public ComboBox<String> cbCriterio;
    @FXML
    public BarChart<String, Number> graficoBarras;
    @FXML
    public PieChart graficoPastel = new PieChart();
    @FXML
    public RadioButton rbBarras = new RadioButton();
    @FXML
    public RadioButton rbPastel = new RadioButton();
    @FXML
    public Button help_button;



    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ObservableList<Cliente> masterData = FXCollections.observableArrayList();

    private FilteredList<Cliente> filteredData;



    /**
     * Este es el método initialize, al ser el primero que en teoría se debe efectuar al iniciar el controlador,
     * facilita la inicialización de las columnas de la TableView:
     * colNombre
     * colEmail
     * colCiuddad.
     *
     * Los botones encargados de actualizar el gráfico, para despues agruparse en un ToggleGroup:
     * rBarras
     * rPastel.
     * Finalmente los valores del ComboBox, asi como seleccionarlos por defecto al seleccionar el CSV:
     * cbCriterio.
     * Posteriormente los eventos apuntano a los métodos de los botones al seleccionarse:
     * csv_button
     * changes_button
     * pdf_button.
     */

    public void initialize() {
        SelectController select = new SelectController();
        Helpcontroller help = new Helpcontroller();
        ToggleGroup group = new ToggleGroup();

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        filteredData = new FilteredList<>(masterData, p -> true);

        tabla.setItems(filteredData);


        rbBarras.setToggleGroup(group);
        rbPastel.setToggleGroup(group);
        rbBarras.setSelected(true);

        cbCriterio.getItems().setAll("Nombre", "Email", "Ciudad", "Ver Todos");
        cbCriterio.getSelectionModel().selectFirst();
        cbCriterio.valueProperty().addListener((obs, oldV, newV) -> actualizarInterfazCompleta());

        group.selectedToggleProperty().addListener((obs, oldV, newV) -> actualizarGrafico());



        changes_button.setOnAction(actionEvent -> aplicarfiltros());
        csv_button.setOnAction(actionEvent -> SeleccionarCSV());
        pdf_button.setOnAction(actionEvent -> select.Selectventana());

        help_button.setOnAction(actionEvent -> help.ayudaVentana());


    }

    /**
     * El método SeleccionarCSV utiliza la clase FileChooser para permitir al usuario seleccionar el archivo CSV
     * que se desee utilizar para leer por el programa, asegurandose de que no sea nulo
     * y utilizando su ruta absoluta para leerlo y actualizar la ruta del nuevo CSV.
     * Creando una nueva lista Cliente con todos los datos leídos con ayuda del objeto tipo clienteDAO, y su método:
     * obtenerTodos, para pasarlos al objeto ObservableList que le servirá a la tabla y al gráfico al leer sus Datos.
     * Finalmente actualizarGrafico para generarlo junto a la tabla.
     */


    @FXML
    public void SeleccionarCSV() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Archivos CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(filter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            clienteDAO.SetRuta(selectedFile.getAbsolutePath());

            List<Cliente> nuevosDatos = clienteDAO.obtenerTodos();

            masterData.setAll(nuevosDatos);

            total.setText(String.valueOf(masterData.size()));

            actualizarGrafico();

        }
    }

    /**
     * El método actualizarGrafico mantiene limpia la disposición del contenido del diagráma al limpiar los datos de ambos gráficos
     * con el mapeo del contenido del ObservableList y su valor en String y su cantidad se permite leer la lista.
     * Utilizando la ciudad y el total de clientes, se permite que el Chart grafique sección por ciudad.
     * Con ayuda del evento del botón perteneciente al ToggleGroup se muestra y se mapea cada uno
     * para su conteo y graficación.
     *
     */

    private void actualizarGrafico() {

        graficoBarras.getData().clear();
        graficoPastel.getData().clear();

        Map<String, Long> conteo = masterData.stream()
                .collect(Collectors.groupingBy(Cliente::getCiudad, Collectors.counting()));

        if (rbBarras.isSelected()) {
            graficoBarras.setVisible(true);
            graficoPastel.setVisible(false);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Clientes");
            conteo.forEach((ciudad, total) -> series.getData().add(new XYChart.Data<>(ciudad, total)));
            graficoBarras.getData().setAll(series);
        } else {
            graficoBarras.setVisible(false);
            graficoPastel.setVisible(true);

            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
            conteo.forEach((ciudad, total) -> pieData.add(new PieChart.Data(ciudad, total)));
            graficoPastel.setData(pieData);
        }
    }

    /**
     * El método actualizarInterfaz utiliza el contenido o valor actual del ComboBox, asegurandose que al principio de el inicio
     * no se muestren por defecto. Y utilizando un switch - case, dependiendo del valor del ComboBox se mostrara
     * la columna que contenga el valor designado. Y en el caso de que se elija "Ver Todos", se volverá a los datos por defecto.
     */




    private void actualizarInterfazCompleta() {
        String criterio = cbCriterio.getValue();

        colNombre.setVisible(false);
        colEmail.setVisible(false);
        colCiudad.setVisible(false);

        switch (criterio) {
            case "Ver Todos" -> {
                masterData.clear();
                List<Cliente> clientes = clienteDAO.obtenerTodos();
                masterData.addAll(clientes);
                total.setText(String.valueOf(masterData.size()));
                colNombre.setVisible(true);
                colEmail.setVisible(true);
                colCiudad.setVisible(true);
            }
            case "Nombre" -> colNombre.setVisible(true);
            case "Email"  -> colEmail.setVisible(true);
            case "Ciudad" -> colCiudad.setVisible(true);
        }

        total.setText(String.valueOf(masterData.size()));

        actualizarGrafico();
    }




    /**
     * El método exportaraPDF utiliza la biblioteca PDFBox para crear un nuevo documento tipo PDDocument,
     * una nueva página añadida al doumento para crear el contenido del PDF.
     * Utilizando PDFType1Font, se elige la forma en la que el PDF se presentará, como el tipo de letra y su tamaño.
     * Con un for-each se lee por Strings el contenido tipo Cliente del CSV para mostrárlo en el PDF, configurando
     * la disposición del PDF a la vista y a la distribución.
     * Se anuda el grafico con el evento del botón dentro del ToggleGroup con las posibles 2 formas de graficar el diagráma seleccionado.
     * Para después incrustrar el gráfico como una foto con ayuda de la clase WritableImage para poder leerlo PDFBox byte a byte.
     * Para finalmente definir el tipo de archivo que será, y guardar el documento con el nombre "reporte.pdf".
     */



    @FXML
    public void exportarAPDF() {

        File archivoDestino = elegirDestinoPDF();

        if (archivoDestino == null) {
            return;
        }
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);

            content.beginText();
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.newLineAtOffset(40, 750);

            content.showText("--- DATOS DEL CSV ---");
            content.newLineAtOffset(0, -20);

            for (Cliente c : masterData) {
                String info = c.getId() + " | " + c.getNombre() + " | " + c.getEmail() + " | " + c.getCiudad();
                content.showText(info);
                content.newLineAtOffset(0, -12);
            }
            content.endText();


            Node grafico = rbBarras.isSelected() ? graficoBarras : graficoPastel;

            WritableImage foto = grafico.snapshot(new SnapshotParameters(), null);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(foto, null), "png", out);

            PDImageXObject img = PDImageXObject.createFromByteArray(doc, out.toByteArray(), "grafico");

            content.drawImage(img, 40, 50, 500, 600);

            content.close();
            doc.save(archivoDestino);


        } catch (Exception e) {
            System.err.println("Error detallado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void aplicarfiltros() {
        masterData.clear();
        String name_criteria = txt_name.getText();
        List<Cliente> clientes = clienteDAO.obtenerPorNombreContiene(name_criteria);
        List <Cliente> resultado = new ArrayList<>();

        for (Cliente c : clientes) {
            if (c.getNombre().toLowerCase().contains(name_criteria.toLowerCase())) {
                resultado.add(c);
            }

        }
        masterData.addAll(resultado);
        total.setText(String.valueOf(masterData.size()));

        actualizarGrafico();
    }

    private File elegirDestinoPDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Documento");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf")
        );

        fileChooser.setInitialFileName("reporte_clientes.pdf");

        return fileChooser.showSaveDialog(null);
    }






}