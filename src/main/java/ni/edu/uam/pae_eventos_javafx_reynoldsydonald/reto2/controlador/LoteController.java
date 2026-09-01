package ni.edu.uam.pae_eventos_javafx_reynoldsydonald.reto2.controlador;
import ni.edu.uam.pae_eventos_javafx_reynoldsydonald.reto2.modelo.LoteCafe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.util.Optional;

public class LoteController {

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

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductor.setCellValueFactory(new PropertyValueFactory<>("productor"));
        colQuintales.setCellValueFactory(new PropertyValueFactory<>("quintales"));
        colVariedad.setCellValueFactory(new PropertyValueFactory<>("variedad"));

        listaLotes.addAll(
                new LoteCafe(1, "Don Pedro", 45.5, "Bourbon"),
                new LoteCafe(2, "Doña María", 30.0, "Caturra")
        );
        tblLotes.setItems(listaLotes);

        tblLotes.setOnMouseClicked(this::mostrarDetalles);
        configurarContextMenu();
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

    private void limpiarVistaDetalle() {
        lblDetalleId.setText("ID: -");
        lblDetalleProductor.setText("Productor: -");
        lblDetalleQuintales.setText("Quintales: -");
        lblDetalleVariedad.setText("Variedad: -");
    }
}