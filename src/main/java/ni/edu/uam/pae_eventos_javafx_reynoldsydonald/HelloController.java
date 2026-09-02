package ni.edu.uam.pae_eventos_javafx_reynoldsydonald;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private void abrirReto1(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ni/edu/uam/pae_eventos_javafx_reynoldsydonald/reto1/reto1-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Reto 1 - Inventario de Pulpería");
            stage.setScene(new Scene(root, 480, 560));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirReto3(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ni/edu/uam/pae_eventos_javafx_reynoldsydonald/reto3/reto3-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Reto 3 - Tienda de Artesanías");
            stage.setScene(new Scene(root, 780, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
