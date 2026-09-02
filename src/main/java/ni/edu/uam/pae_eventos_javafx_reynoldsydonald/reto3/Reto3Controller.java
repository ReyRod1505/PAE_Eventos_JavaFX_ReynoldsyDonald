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
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        
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

        listaArtesanias.add(new Artesania("ART-01", "Jarrón de Barro Masaya", "Cerámica", 350.00, "https://via.placeholder.com/40/8d6e63/ffffff?text=Jarron"));
        listaArtesanias.add(new Artesania("ART-02", "Hamaca Matagalpina", "Textiles", 1200.00, "https://via.placeholder.com/40/e67e22/ffffff?text=Hamaca"));
        listaArtesanias.add(new Artesania("ART-03", "Máscara El Güegüense", "Madera", 450.00, "https://via.placeholder.com/40/27ae60/ffffff?text=Mascara"));
        
        mostrarAlerta("ℹ Bienvenido a la Tienda de Artesanías. Seleccione o ingrese un producto.", "INFO");
    }

    @FXML
    private void onNuevo(ActionEvent event) {
        limpiarFormulario();
        mostrarAlerta("📄 Formulario listo para registrar un nuevo producto.", "INFO");
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String urlImagen = txtUrlImagen.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || categoria.isEmpty() || precioStr.isEmpty()) {
            mostrarAlerta("⚠ Complete todos los campos requeridos (Código, Nombre, Categoría y Precio).", "ADVERTENCIA");
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            if (urlImagen.isEmpty()) {
                urlImagen = "https://via.placeholder.com/40/7f8c8d/ffffff?text=Arte";
            }

            Artesania nueva = new Artesania(codigo, nombre, categoria, precio, urlImagen);
            listaArtesanias.add(nueva);

            mostrarAlerta(String.format("✔ Artesanía '%s' (%s) guardada exitosamente en la tabla.", nombre, codigo), "EXITO");
            limpiarFormulario();
        } catch (NumberFormatException e) {
            mostrarAlerta("El precio ingresado debe ser un número válido.", "ERROR");
        }
    }

    @FXML
    private void onBuscar(ActionEvent event) {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            mostrarAlerta("Ingrese un código en el campo de texto para buscar.", "ADVERTENCIA");
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
                
                mostrarAlerta(String.format("Artesanía encontrada: %s - %s", a.getCodigo(), a.getNombre()), "EXITO");
                break;
            }
        }

        if (!encontrado) {
            mostrarAlerta(String.format("No se encontró ninguna artesanía con el código '%s'.", codigo), "ERROR");
        }
    }

    @FXML
    private void onProcesarVenta(ActionEvent event) {
        Artesania seleccionada = tblArtesanias.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            mostrarAlerta(String.format("🛒 Venta procesada para '%s' por C$ %.2f", seleccionada.getNombre(), seleccionada.getPrecio()), "EXITO");
        } else {
            mostrarAlerta("⚠ Seleccione una artesanía de la tabla para procesar la venta.", "ADVERTENCIA");
        }
    }

    @FXML
    private void onVerHistorial(ActionEvent event) {
        mostrarAlerta(String.format("📊 Historial de ventas (Total de productos registrados: %d)", listaArtesanias.size()), "INFO");
    }

    @FXML
    private void onAcercaDe(ActionEvent event) {
        mostrarAlerta("ℹ Tienda de Artesanías v1.0 - Práctica JavaFX Reto 3", "INFO");
    }

    @FXML
    private void onSalir(ActionEvent event) {
        mostrarAlerta("Cerrando aplicación...", "ERROR");
    }

    private void mostrarAlerta(String mensaje, String tipo) {
        lblMensaje.setText(mensaje);
        System.out.println(mensaje);
        switch (tipo) {
            case "EXITO":
                lblMensaje.setStyle("-fx-background-color: #d1fae5; -fx-text-fill: #065f46; -fx-border-color: #6ee7b7; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8 12; -fx-font-weight: bold;");
                break;
            case "ERROR":
                lblMensaje.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #991b1b; -fx-border-color: #fca5a5; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8 12; -fx-font-weight: bold;");
                break;
            case "ADVERTENCIA":
                lblMensaje.setStyle("-fx-background-color: #fef3c7; -fx-text-fill: #92400e; -fx-border-color: #fcd34d; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8 12; -fx-font-weight: bold;");
                break;
            default: // INFO
                lblMensaje.setStyle("-fx-background-color: #e0f2fe; -fx-text-fill: #075985; -fx-border-color: #7dd3fc; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8 12; -fx-font-weight: bold;");
                break;
        }
    }

    private void limpiarFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        txtCategoria.clear();
        txtPrecio.clear();
        txtUrlImagen.clear();
    }
}
