package ni.edu.uam.pae_eventos_javafx_reynoldsydonald.reto1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

public class Reto1Controller {

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCantidad;

    @FXML
    private Label lblMensaje;

    // Inventario simulado (Código -> Datos del producto)
    private final Map<String, Producto> inventarioSimulado = new HashMap<>();

    @FXML
    public void initialize() {
        // Cargar algunos datos iniciales de prueba
        inventarioSimulado.put("001", new Producto("001", "Arroz (lb)", 22.50, 50));
        inventarioSimulado.put("002", new Producto("002", "Frijoles (lb)", 30.00, 40));
        inventarioSimulado.put("003", new Producto("003", "Aceite 1L", 65.00, 15));
        inventarioSimulado.put("004", new Producto("004", "Azúcar (lb)", 18.00, 60));
    }

    @FXML
    private void guardarProducto(ActionEvent event) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();

        // Verificación de campos no vacíos usando isEmpty()
        if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText("Por favor, complete todos los campos.");
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            int cantidad = Integer.parseInt(cantidadStr);

            if (precio <= 0 || cantidad < 0) {
                lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
                lblMensaje.setText("El precio debe ser positivo y la cantidad no negativa.");
                return;
            }

            // Guardar en el inventario simulado
            Producto prod = new Producto(codigo, nombre, precio, cantidad);
            inventarioSimulado.put(codigo, prod);

            lblMensaje.setStyle("-fx-text-fill: #27ae60;");
            lblMensaje.setText(String.format("¡Producto '%s' guardado exitosamente!", nombre));

        } catch (NumberFormatException e) {
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText("Ingrese datos numéricos válidos.");
        }
    }

    @FXML
    private void buscarProducto(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String codigo = txtCodigo.getText().trim();

            if (codigo.isEmpty()) {
                lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
                lblMensaje.setText("Ingrese un código para buscar.");
                return;
            }

            if (inventarioSimulado.containsKey(codigo)) {
                Producto prod = inventarioSimulado.get(codigo);
                txtNombre.setText(prod.getNombre());
                txtPrecio.setText(String.valueOf(prod.getPrecio()));
                txtCantidad.setText(String.valueOf(prod.getCantidad()));

                lblMensaje.setStyle("-fx-text-fill: #2980b9;");
                lblMensaje.setText(String.format("Producto encontrado: %s", prod.getNombre()));
            } else {
                lblMensaje.setStyle("-fx-text-fill: #e67e22;");
                lblMensaje.setText(String.format("Código '%s' no encontrado. Ingrese los datos para registrarlo.", codigo));
                txtNombre.clear();
                txtPrecio.clear();
                txtCantidad.clear();
            }
        }
    }

    // Clase auxiliar interna para representar un Producto
    public static class Producto {
        private final String codigo;
        private final String nombre;
        private final double precio;
        private final int cantidad;

        public Producto(String codigo, String nombre, double precio, int cantidad) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.precio = precio;
            this.cantidad = cantidad;
        }

        public String getCodigo() { return codigo; }
        public String getNombre() { return nombre; }
        public double getPrecio() { return precio; }
        public int getCantidad() { return cantidad; }
    }
}
