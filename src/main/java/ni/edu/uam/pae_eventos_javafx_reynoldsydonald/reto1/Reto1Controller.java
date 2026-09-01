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

        mostrarAlerta("ℹ Ingrese un código y presione ENTER para buscar o complete los datos y guarde.", "INFO");
    }

    @FXML
    private void guardarProducto(ActionEvent event) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();

        // Verificación de campos no vacíos usando isEmpty()
        if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
            mostrarAlerta("⚠ Por favor, complete todos los campos del formulario.", "ADVERTENCIA");
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            int cantidad = Integer.parseInt(cantidadStr);

            if (precio <= 0 || cantidad < 0) {
                mostrarAlerta("❌ El precio debe ser mayor a 0 y la cantidad no puede ser negativa.", "ERROR");
                return;
            }

            // Guardar en el inventario simulado
            Producto prod = new Producto(codigo, nombre, precio, cantidad);
            inventarioSimulado.put(codigo, prod);

            mostrarAlerta(String.format("✔ ¡Producto '%s' guardado exitosamente!", nombre), "EXITO");

        } catch (NumberFormatException e) {
            mostrarAlerta("❌ Error: Ingrese datos numéricos válidos en precio y cantidad.", "ERROR");
        }
    }

    @FXML
    private void buscarProducto(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String codigo = txtCodigo.getText().trim();

            if (codigo.isEmpty()) {
                mostrarAlerta("⚠ Ingrese un código en el campo para realizar la búsqueda.", "ADVERTENCIA");
                return;
            }

            if (inventarioSimulado.containsKey(codigo)) {
                Producto prod = inventarioSimulado.get(codigo);
                txtNombre.setText(prod.getNombre());
                txtPrecio.setText(String.valueOf(prod.getPrecio()));
                txtCantidad.setText(String.valueOf(prod.getCantidad()));

                mostrarAlerta(String.format("🔎 Producto encontrado: %s (C$ %.2f - Cantidad: %d)", prod.getNombre(), prod.getPrecio(), prod.getCantidad()), "EXITO");
            } else {
                mostrarAlerta(String.format("ℹ Código '%s' no encontrado. Puede ingresar los datos para registrarlo.", codigo), "INFO");
                txtNombre.clear();
                txtPrecio.clear();
                txtCantidad.clear();
            }
        }
    }

    private void mostrarAlerta(String mensaje, String tipo) {
        lblMensaje.setText(mensaje);
        switch (tipo) {
            case "EXITO":
                lblMensaje.setStyle("-fx-background-color: #d1fae5; -fx-text-fill: #065f46; -fx-border-color: #6ee7b7; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10 14; -fx-font-weight: bold; -fx-alignment: center;");
                break;
            case "ERROR":
                lblMensaje.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #991b1b; -fx-border-color: #fca5a5; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10 14; -fx-font-weight: bold; -fx-alignment: center;");
                break;
            case "ADVERTENCIA":
                lblMensaje.setStyle("-fx-background-color: #fef3c7; -fx-text-fill: #92400e; -fx-border-color: #fcd34d; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10 14; -fx-font-weight: bold; -fx-alignment: center;");
                break;
            default: // INFO
                lblMensaje.setStyle("-fx-background-color: #e0f2fe; -fx-text-fill: #075985; -fx-border-color: #7dd3fc; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10 14; -fx-font-weight: bold; -fx-alignment: center;");
                break;
        }
    }

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
