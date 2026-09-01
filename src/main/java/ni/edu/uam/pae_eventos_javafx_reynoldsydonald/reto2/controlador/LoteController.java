package ni.edu.uam.pae_eventos_javafx_reynoldsydonald.reto2.controlador;

import ni.edu.uam.pae_eventos_javafx_reynoldsydonald.reto2.modelo.LoteCafe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.util.Optional;

public class LoteController {

    @FXML private TextField txtProductor;
    @FXML private TextField txtQuintales;
    @FXML private TextField txtVariedad;

    @FXML private TableView<LoteCafe> tblLotes;
    @FXML private TableColumn<LoteCafe, Integer> colId;
    @FXML private TableColumn<LoteCafe, String> colProductor;
    @FXML private TableColumn<LoteCafe, Double> colQuintales;
    @FXML private TableColumn<LoteCafe, String> colVariedad;

    @FXML private Label lblDetalleId;
    @FXML private Label lblDetalleProductor;
    @FXML private Label lblDetalleQuintales;
    @FXML private Label lblDetalleVariedad;

    private final ObservableList<LoteCafe> listaLotes = FXCollections.observableArrayList();
    private int contadorId = 1;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductor.setCellValueFactory(new PropertyValueFactory<>("productor"));
        colQuintales.setCellValueFactory(new PropertyValueFactory<>("quintales"));
        colVariedad.setCellValueFactory(new PropertyValueFactory<>("variedad"));

        tblLotes.setItems(listaLotes);
        tblLotes.setOnMouseClicked(this::mostrarDetalles);
        configurarContextMenu();
    }

    @FXML
    private void registrarLote(ActionEvent event) {
        String productor = txtProductor.getText().trim();
        String quintalesStr = txtQuintales.getText().trim();
        String variedad = txtVariedad.getText().trim();

        if (productor.isEmpty() || quintalesStr.isEmpty() || variedad.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor, complete todos los campos.");
            return;
        }

        try {
            double quintales = Double.parseDouble(quintalesStr);
            if (quintales <= 0) {
                mostrarAlerta(Alert.AlertType.WARNING, "Dato Inválido", "Los quintales deben ser mayor a 0.");
                return;
            }

            LoteCafe nuevoLote = new LoteCafe(contadorId++, productor, quintales, variedad);
            listaLotes.add(nuevoLote);
            limpiarFormulario();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Formato", "Ingrese un valor numérico válido para los quintales.");
        }
    }

    private void mostrarDetalles(MouseEvent event) {
        LoteCafe seleccionado = tblLotes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            lblDetalleId.setText("ID: " + seleccionado.getId());
            lblDetalleProductor.setText("Productor: " + seleccionado.getProductor());
            lblDetalleQuintales.setText("Quintales: " + seleccionado.getQuintales());
            lblDetalleVariedad.setText("Variedad: " + seleccionado.getVariedad());
        }
    }

    private void configurarContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem itemEditar = new MenuItem("Editar");
        MenuItem itemEliminar = new MenuItem("Eliminar");

        itemEditar.setOnAction(e -> editarLote());
        itemEliminar.setOnAction(e -> eliminarLote());

        contextMenu.getItems().addAll(itemEditar, itemEliminar);
        tblLotes.setContextMenu(contextMenu);
    }

    private void eliminarLote() {
        LoteCafe seleccionado = tblLotes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText("¿Eliminar lote de " + seleccionado.getProductor() + "?");
        alerta.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            listaLotes.remove(seleccionado);
            limpiarVistaDetalle();
        }
    }

    private void editarLote() {
        LoteCafe seleccionado = tblLotes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setProductor(seleccionado.getProductor() + " (Modificado)");
            tblLotes.refresh();
        }
    }

    private void limpiarFormulario() {
        txtProductor.clear();
        txtQuintales.clear();
        txtVariedad.clear();
        txtProductor.requestFocus();
    }

    private void limpiarVistaDetalle() {
        lblDetalleId.setText("ID: -");
        lblDetalleProductor.setText("Productor: -");
        lblDetalleQuintales.setText("Quintales: -");
        lblDetalleVariedad.setText("Variedad: -");
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}