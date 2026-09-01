package ni.edu.uam.pae_eventos_javafx_reynoldsydonald.reto3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Reto3Application extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Reto3Application.class.getResource("reto3-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 780, 600);
        stage.setTitle("Reto 3 - Tienda de Artesanías Nicaragüenses");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
