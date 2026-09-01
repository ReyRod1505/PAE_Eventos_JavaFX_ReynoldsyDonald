package ni.edu.uam.pae_eventos_javafx_reynoldsydonald.reto3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Reto3Controller {

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtUrlImagen;

    @FXML
    private TableView<Artesania> tblArtesanias;

    @FXML
    private TableColumn<Artesania, String> colCodigo;

    @FXML
    private TableColumn<Artesania, String> colNombre;

    @FXML
    private TableColumn<Artesania, String> colCategoria;

    @FXML
    private TableColumn<Artesania, Double> colPrecio;

    @FXML
    private TableColumn<Artesania, String> colImagen;

    @FXML
    private Label lblMensaje;

    private final ObservableList<Artesania> listaArtesanias = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configuración de celdas estándar
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        
        // Formato para el precio
        colPrecio.setCellFactory(col -> new TableCell<Artesania, Double>() {
            @Override
            protected void updateItem(Double precio, boolean empty) {
                super.updateItem(precio, empty);
                if (empty || precio == null) {
                    setText(null);
                } else {
                    setText(String.format("C$ %.2f", precio));
                }
            }
        });

        // Configuración de CellFactory para renderizar ImageView desde la URL/Ruta de la imagen
        colImagen.setCellValueFactory(new PropertyValueFactory<>("urlImagen"));
        colImagen.setCellFactory(param -> new TableCell<Artesania, String>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(String urlImagen, boolean empty) {
                super.updateItem(urlImagen, empty);
                if (empty || urlImagen == null || urlImagen.trim().isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        Image img = new Image(urlImagen, 40, 40, true, true, true);
                        imageView.setImage(img);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        setGraphic(null);
                    }
                }
            }
        });

        tblArtesanias.setItems(listaArtesanias);

        // Cargar algunos datos iniciales de artesanías con imágenes de muestra
        listaArtesanias.add(new Artesania("ART-01", "Jarrón de Barro Masaya", "Cerámica", 350.00, "https://via.placeholder.com/40/8d6e63/ffffff?text=Jarron"));
        listaArtesanias.add(new Artesania("ART-02", "Hamaca Matagalpina", "Textiles", 1200.00, "https://via.placeholder.com/40/e67e22/ffffff?text=Hamaca"));
        listaArtesanias.add(new Artesania("ART-03", "Máscara El Güegüense", "Madera", 450.00, "https://via.placeholder.com/40/27ae60/ffffff?text=Mascara"));
        
        System.out.println("[INFO] Módulo Reto 3 - Tienda de Artesanías inicializado correctamente.");
    }

    // --- MÉTODOS ACTION EVENT PARA MENUBAR Y TOOLBAR ---

    @FXML
    private void onNuevo(ActionEvent event) {
        limpiarFormulario();
        String msg = "[EVENTO] Acción 'Nuevo' ejecutada: Formulario limpiado para un nuevo registro.";
        System.out.println(msg);
        lblMensaje.setStyle("-fx-text-fill: #2980b9;");
        lblMensaje.setText(msg);
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String urlImagen = txtUrlImagen.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || categoria.isEmpty() || precioStr.isEmpty()) {
            String warnMsg = "[ADVERTENCIA] Debe completar los campos requeridos (Código, Nombre, Categoría y Precio).";
            System.out.println(warnMsg);
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText(warnMsg);
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            if (urlImagen.isEmpty()) {
                urlImagen = "https://via.placeholder.com/40/7f8c8d/ffffff?text=Arte";
            }

            Artesania nueva = new Artesania(codigo, nombre, categoria, precio, urlImagen);
            listaArtesanias.add(nueva);

            String successMsg = String.format("[ÉXITO] Artesanía '%s' (%s) guardada y agregada a la tabla.", nombre, codigo);
            System.out.println(successMsg);
            lblMensaje.setStyle("-fx-text-fill: #27ae60;");
            lblMensaje.setText(successMsg);

            limpiarFormulario();
        } catch (NumberFormatException e) {
            String errorMsg = "[ERROR] El precio ingresado no es un número válido.";
            System.err.println(errorMsg);
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText(errorMsg);
        }
    }

    @FXML
    private void onBuscar(ActionEvent event) {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            String msg = "[EVENTO] Acción 'Buscar': Ingrese un código en el campo de texto para buscar.";
            System.out.println(msg);
            lblMensaje.setStyle("-fx-text-fill: #e67e22;");
            lblMensaje.setText(msg);
            return;
        }

        boolean encontrado = false;
        for (Artesania a : listaArtesanias) {
            if (a.getCodigo().equalsIgnoreCase(codigo)) {
                tblArtesanias.getSelectionModel().select(a);
                txtNombre.setText(a.getNombre());
                txtCategoria.setText(a.getCategoria());
                txtPrecio.setText(String.valueOf(a.getPrecio()));
                txtUrlImagen.setText(a.getUrlImagen());
                encontrado = true;
                
                String msg = String.format("[BÚSQUEDA] Artesanía encontrada: %s - %s", a.getCodigo(), a.getNombre());
                System.out.println(msg);
                lblMensaje.setStyle("-fx-text-fill: #27ae60;");
                lblMensaje.setText(msg);
                break;
            }
        }

        if (!encontrado) {
            String msg = String.format("[BÚSQUEDA] No se encontró ninguna artesanía con el código '%s'.", codigo);
            System.out.println(msg);
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText(msg);
        }
    }

    @FXML
    private void onProcesarVenta(ActionEvent event) {
        Artesania seleccionada = tblArtesanias.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            String msg = String.format("[VENTA] Venta procesada para '%s' por C$ %.2f", seleccionada.getNombre(), seleccionada.getPrecio());
            System.out.println(msg);
            lblMensaje.setStyle("-fx-text-fill: #8e44ad;");
            lblMensaje.setText(msg);
        } else {
            String msg = "[VENTA] Seleccione una artesanía de la tabla para procesar la venta.";
            System.out.println(msg);
            lblMensaje.setStyle("-fx-text-fill: #e67e22;");
            lblMensaje.setText(msg);
        }
    }

    @FXML
    private void onVerHistorial(ActionEvent event) {
        String msg = "[HISTORIAL] Visualizando historial de ventas (Total de registros en tabla: " + listaArtesanias.size() + ").";
        System.out.println(msg);
        lblMensaje.setStyle("-fx-text-fill: #34495e;");
        lblMensaje.setText(msg);
    }

    @FXML
    private void onAcercaDe(ActionEvent event) {
        String msg = "[AYUDA] Tienda de Artesanías v1.0 - Desarrollado para Práctica JavaFX (Reto 3).";
        System.out.println(msg);
        lblMensaje.setStyle("-fx-text-fill: #2980b9;");
        lblMensaje.setText(msg);
    }

    @FXML
    private void onSalir(ActionEvent event) {
        System.out.println("[SISTEMA] Saliendo de la aplicación Tienda de Artesanías...");
        lblMensaje.setText("[SISTEMA] Cerrando aplicación...");
    }

    private void limpiarFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        txtCategoria.clear();
        txtPrecio.clear();
        txtUrlImagen.clear();
    }
}
