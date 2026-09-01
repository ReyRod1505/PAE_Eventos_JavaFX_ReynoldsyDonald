package ni.edu.uam.pae_eventos_javafx_reynoldsydonald.reto1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Reto1Application extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Reto1Application.class.getResource("/ni/edu/uam/pae_eventos_javafx_reynoldsydonald/reto1/reto1-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 560);
        stage.setTitle("Reto 1 - Inventario de Pulpería");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
