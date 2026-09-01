package ni.edu.uam.pae_eventos_javafx_reynoldsydonald;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/ni/edu/uam/pae_eventos_javafx_reynoldsydonald/reto2/LoteView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Reto 2 - Recepción de Café");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}